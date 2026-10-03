package com.example.ticketbooking.repository;

import com.example.ticketbooking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("select b from Booking b join b.guest g where g.firstName = :firstName and g.lastName = :lastName")
    List<Booking> findByFullName(String firstName, String lastName);

    @Query("select b from Booking b join b.guest g where b.checkIn = :checkIn and g.firstName = :firstName and g.lastName = :lastName order by :checkIn desc")
    Optional<Booking> findByCheckInDateAndFullName(LocalDateTime checkIn, String firstName, String lastName);
}
