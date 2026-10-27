package com.example.ticketbooking.service;

import com.example.ticketbooking.model.DailyInventory;
import com.example.ticketbooking.repository.DailyInventoryRepository;
import com.example.ticketbooking.repository.InventoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final DailyInventoryRepository inventoryRepository;

    public DailyInventory getInventoryByHotelId(Long hotelId) {
        return inventoryRepository.findByHotelId(hotelId)
                .orElseThrow(() -> new InventoryNotFoundException("Инвентарь не был найден"));
    }
}
