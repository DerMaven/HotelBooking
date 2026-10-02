package com.example.ticketbooking.repository;

import com.example.ticketbooking.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
