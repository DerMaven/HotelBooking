package com.example.ticketbooking.service;

import com.example.ticketbooking.dto.BookingDto;
import com.example.ticketbooking.exception.*;
import com.example.ticketbooking.model.*;
import com.example.ticketbooking.model.enums.*;
import com.example.ticketbooking.repository.BookingRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final HotelService hotelService;
    private final RoomService roomService;
    private final RedisLockService redisLockService;
    private final RedisCacheService redisCacheService;

    @Qualifier("paymentService")
    private final MoneyService paymentService;

    @Qualifier("refundService")
    private final MoneyService refundService;
    private final InventoryService inventoryService;

    public BookingDto createBooking(Long hotelId, Long roomId, Long ratePlanId, Long guestId, PaymentMethod paymentMethod, LocalDate checkIn, LocalDate checkOut) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("createBooking");
        EntityManager em = emf.createEntityManager();

        Optional<Booking> foundByDate = bookingRepository.findByHotelIdAndRoomIdAndCheckInAndCheckOut(hotelId, roomId, checkIn, checkOut);
        if (foundByDate.isPresent()) {
            throw new BookingAlreadyExistsException("Бронь на эти даты, в этом отеле, на этот номер, уже существует");
        }
        if (!roomService.belongsToHotel(hotelId, roomId)) {
            throw new RoomNotBelongsToHotelException("Комната не принадлежит к этому отелю");
        }
        if (!roomService.roomsRatePlanBelongsToHotel(hotelId, ratePlanId)) {
            throw new RatePlanNotBelongsToHotelException("Выбранный тариф не принадлежит к этому отелю");
        }

        String tempId = UUID.randomUUID().toString();
        redisLockService.acquireLock(roomId, Long.valueOf(tempId), checkIn, checkOut);

        try {
            em.getTransaction().begin();

            Booking booking = new Booking();
            booking.setStatus(BookingStatus.CREATED);
            booking.setCreatedAt(LocalDateTime.now());

            Guest guest = em.find(Guest.class, guestId);
            booking.setGuest(guest);

            Hotel hotel = em.find(Hotel.class, hotelId);
            booking.setHotel(hotel);

            em.persist(hotel);

            RatePlan ratePlan = em.find(RatePlan.class, ratePlanId);
            booking.setRatePlan(ratePlan);

            em.persist(ratePlan);

            Room room = em.find(Room.class, roomId);
            if (room.getStatus() == RoomStatus.OCCUPIED ||
                    room.getStatus() == RoomStatus.MAINTENANCE ||
                    room.getStatus() == RoomStatus.RESERVED ||
                    room.getIsAvailable() == false) {
                throw new RoomNotAvailableException("Комната недоступна");
            }
            booking.setRoom(room);

            em.persist(room);

            DailyInventory inventory = em.find(DailyInventory.class, inventoryService.getInventoryByHotelId(hotelId));

            em.persist(inventory);

            Payment payment = new Payment();
            payment.setStatus(PaymentStatus.PENDING);
            payment.setPaymentMethod(paymentMethod);

            em.persist(payment);

            if (inventory.getBookingStock() + 1 > inventory.getTotalStock()) {
                em.getTransaction().rollback();
            }
            inventory.setBookingStock(inventory.getBookingStock() + 1);

            Currency hotelCurrency = hotelService.getCurrency(hotelId);
            Currency ratePlanCurrency = Currency.valueOf(hotelService.getRatePlan(ratePlanId).getCurrency());
            if (!paymentService.isHotelAndRatePlanCurrenciesEqual(hotelCurrency, ratePlanCurrency)) {
                em.getTransaction().rollback();
            }

            Long nights = (long) getNights(checkIn, checkOut);
            if (ratePlan.getMinNightsStay() > nights || ratePlan.getMaxNightsStay() < nights) {
                throw new NightsLimitReachedException("Вы превысили границу ночеваний");
            }
            paymentService.setPriceByRatePlan(payment, ratePlanId, nights);

            room.setStatus(RoomStatus.RESERVED);
            room.setIsAvailable(false);

            payment.setProcessedAt(LocalDateTime.now());
            paymentService.paymentLayer(payment);
            payment.setStatus(PaymentStatus.PAID);

            booking.setStatus(BookingStatus.CONFIRMED);

            int hotelHash = Integer.parseInt(String.format("%3.3s", hotelId.hashCode()));
            int planHash = Integer.parseInt(String.format("%3.3s", ratePlanId.hashCode()));
            String bookingReference = "H-" + hotelHash + "P-" + planHash + "-" + UUID.randomUUID();
            booking.setBookingReference(bookingReference);

            booking.setCheckIn(checkIn);
            booking.setCheckOut(checkOut);

            em.persist(booking);

            em.flush();
            em.getTransaction().commit();

            BookingDto dto = new BookingDto(
                    roomId,
                    room.getRoomType(),
                    checkIn,
                    checkOut,
                    nights,
                    ratePlan.getPrice().longValue(),
                    payment.getAmount(),
                    ratePlanCurrency,
                    true,
                    ratePlan.getId()
                    );
            redisCacheService.set("booking", String.valueOf(booking.getId()), Duration.ofMinutes(30));

            return dto;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new BookingCreationException("Возникла ошибка при создании брони");
        } finally {
            em.close();
            redisLockService.releaseLock(roomId, Long.valueOf(tempId), checkIn, checkOut);
        }
    }

    public BookingDto cancelBooking(Long bookingId, Long guestId) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("createBooking");
        EntityManager em = emf.createEntityManager();


        try {
            em.getTransaction().begin();

            // TODO: load booking & guest FOR UPDATE
            Booking booking = em.find(Booking.class, bookingId);
            Long nights = booking.getNights().longValue();
            Currency bookingCurrency = booking.getHotel().getCurrency();
            Guest guest = em.find(Guest.class, guestId);

            // TODO: check if booking was CONFIRMED & belongs to guest
            if (!(booking.getStatus() == BookingStatus.CONFIRMED)) {
                throw new BookingStatusException("Бронь не была подтверждена, чтобы ее отменять");
            }
            if (!(Objects.equals(guest.getId(), booking.getGuest().getId()))) {
                throw new BookingNotBelongsToGuestException("Бронь не принадлежит гостю. чтобы он мог ее отменить");
            }

            // TODO: bookedStock--
            DailyInventory inventory = inventoryService.getInventoryByHotelId(booking.getHotel().getId());
            if ((inventory.getBookingStock() + 1 < 0)) {
                throw new StockDecrementException("Декрементирование инвентаря невозможно");
            }

            inventory.setBookingStock(inventory.getBookingStock() - 1);

            em.persist(inventory);

            Payment paymentEM = refundService.getByBookingId(booking.getId());

            Payment refundPayment = new Payment();
            refundPayment.setAmount(paymentEM.getAmount());
            refundPayment.setCurrency(paymentEM.getCurrency());
            refundPayment.setPaymentMethod(paymentEM.getPaymentMethod());
            refundPayment.setStatus(PaymentStatus.REFUNDING);

            // TODO: Trasaction.processedAt()
            refundPayment.setProcessedAt(LocalDateTime.now());
            em.persist(refundPayment);

            // TODO: Insert new Transaction with status REFUND & bookingId
            refundService.paymentLayer(refundPayment);
            refundPayment.setStatus(PaymentStatus.REFUNDED);
            refundPayment.setBooking(booking);
            em.merge(refundPayment);

            // TODO: Booking status is CANCELLED
            booking.setStatus(BookingStatus.CANCELLED);
            em.merge(booking);

            em.flush();
            em.getTransaction().commit();

            BookingDto dto = new BookingDto(
                    booking.getRoom().getId(),
                    booking.getRoom().getRoomType(),
                    booking.getCheckIn(),
                    booking.getCheckOut(),
                    nights,
                    booking.getRatePlan().getPrice().longValue(),
                    paymentEM.getAmount(),
                    bookingCurrency,
                    false,
                    booking.getRatePlan().getId()
            );

            return dto;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new BookingCancellationException("Возникла ошибка при отмене брони");
        } finally {
            // TODO: после успешного flush + COMMIT кэшировать отмену брони
            em.close();
            redisCacheService.set("booking", String.valueOf(bookingId), Duration.ofMinutes(10));
        }
    }


    private int getNights(LocalDate checkIn, LocalDate checkOut) {
        return checkIn.datesUntil(checkOut).toList().size();
    }
}
