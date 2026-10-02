package com.example.ticketbooking.repository;

import com.example.ticketbooking.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
