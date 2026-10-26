package com.example.ticketbooking.repository;

import com.example.ticketbooking.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query(value = "select r from Room r where r.hotel.id = :hotelId order by r.status limit :size")
    List<Room> findByHotel(Long hotelId, Long size);
}
