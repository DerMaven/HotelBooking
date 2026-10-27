package com.example.ticketbooking.service;

import com.example.ticketbooking.exception.*;
import com.example.ticketbooking.model.*;
import com.example.ticketbooking.model.enums.*;
import com.example.ticketbooking.repository.BookingRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
    private final TransactionService transactionService;
    private final InventoryService inventoryService;

    public Booking createBooking(Long hotelId, Long roomId, Long ratePlanId, Long guestId, PaymentMethod paymentMethod, Currency currency, LocalDate checkIn, LocalDate checkOut) {
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

            // TODO: загрузка пользователя
            Guest guest = em.find(Guest.class, guestId);
            booking.setGuest(guest);

            // TODO: загрузка отеля и тарифа
            Hotel hotel = em.find(Hotel.class, hotelId);
            booking.setHotel(hotel);

            em.persist(hotel);

            RatePlan ratePlan = em.find(RatePlan.class, ratePlanId);
            booking.setRatePlan(ratePlan);

            em.persist(ratePlan);

            // TODO: загрузка комнаты и инвентаря
            Room room = em.find(Room.class, roomId);
            booking.setRoom(room);

            em.persist(room);

            DailyInventory inventory = em.find(DailyInventory.class, inventoryService.getInventoryByHotelId(hotelId));

            em.persist(inventory);
            // TODO: создание платежа со статусом PENDING и методом оплаты
            Transaction transaction = new Transaction();
            transaction.setStatus(PaymentStatus.PENDING);
            transaction.setPayment(paymentMethod);

            em.persist(transaction);

            // TODO: прибавить bookedStock
            if (inventory.getBookingStock() + 1 > inventory.getTotalStock()) {
                em.getTransaction().rollback();
            }
            inventory.setBookingStock(inventory.getBookingStock() + 1);

            // TODO: к сумме пустого платежа назначить сумму из Rate Plan
            // TODO: валюты должны совпадать в отеле и тарифе
            Currency hotelCurrency = hotelService.getCurrency(hotelId);
            Currency ratePlanCurrency = Currency.valueOf(hotelService.getRatePlan(ratePlanId).getCurrency());
            if (!transactionService.isHotelAndRatePlanCurrenciesEqual(hotelCurrency, ratePlanCurrency)) {
                em.getTransaction().rollback();
            }
            transactionService.setPriceByRatePlan(transaction, ratePlanId);

            // TODO: подборка комнаты по параметрам

            // TODO: статус комнаты должен поменяться на RESERVED
            room.setStatus(RoomStatus.RESERVED);

            // TODO: время платежа должно стать processedAt.now(), PAID status, работа шлюза
            transaction.setProcessedAt(LocalDateTime.now());
            transactionService.paymentLayer(transaction);
            transaction.setStatus(PaymentStatus.PAID);

            // TODO: статус брони должен статус CONFIRMED
            booking.setStatus(BookingStatus.CONFIRMED);

            // TODO: поставить Booking Reference и даты
            int hotelHash = Integer.parseInt(String.format("%3.3s", hotelId.hashCode()));
            int planHash = Integer.parseInt(String.format("%3.3s", ratePlanId.hashCode()));
            String bookingReference = "H-" + hotelHash + "P-" + planHash + "-" + UUID.randomUUID();
            booking.setBookingReference(bookingReference);

            booking.setCheckIn(checkIn);
            booking.setCheckOut(checkOut);

            em.persist(booking);

            // TODO: flush + COMMIT
            em.flush();
            em.getTransaction().commit();
        } catch (Exception e) {

        } finally {
            em.close();
            redisLockService.releaseLock(roomId, Long.valueOf(tempId), checkIn, checkOut);
        }
        // TODO: после успешного flush + COMMIT кэшировать бронь + unlock
    }

    public List<Booking> getByFullName(String firstName, String lastName) {
        return bookingRepository.findByFullName(firstName, lastName);
    }

    public Booking getByCheckInAndFullName(LocalDate checkIn, String firstName, String lastName) {
        return bookingRepository.findByCheckInDateAndFullName(checkIn, firstName, lastName)
                .orElseThrow(() -> new BookingNotFoundException("Booking was not found"));
    }
}
