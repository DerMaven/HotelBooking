package com.example.ticketbooking.repository;

import com.example.ticketbooking.model.DailyInventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyInventoryRepository extends JpaRepository<DailyInventory, Long> {
}
