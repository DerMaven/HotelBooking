package com.example.ticketbooking.repository;

import com.example.ticketbooking.model.DailyInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DailyInventoryRepository extends JpaRepository<DailyInventory, Long> {

    @Query("select di from DailyInventory di where di.hotel.name = :name")
    Optional<DailyInventory> findByHotelName(String name);
}
