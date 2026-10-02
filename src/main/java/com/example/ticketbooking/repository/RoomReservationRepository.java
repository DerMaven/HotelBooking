package com.example.ticketbooking.repository;

import com.example.ticketbooking.model.RoomReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomReservationRepository extends JpaRepository<RoomReservation, Long> {
}
