package com.example.ticketbooking.repository;

import com.example.ticketbooking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<BookingRepository, Long> {

    @Query("select b.* from bookings b join guests g on g.id = b.guest_id where g.first_name = :firstName and g.last_name = :lastName")
    Optional<Booking> findByFullName(String firstName, String lastName);


}
