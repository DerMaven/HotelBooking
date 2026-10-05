package com.example.ticketbooking.repository;

import com.example.ticketbooking.model.RatePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RatePlanRepository extends JpaRepository<RatePlan, Long> {

    @Query("select rp from RatePlan rp join Hotel h on rp.hotel.id = h.id where h.name = :hotelName")
    List<RatePlan> findByHotelName(String hotelName);
}
