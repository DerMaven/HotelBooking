package com.example.ticketbooking.repository;

import com.example.ticketbooking.model.RatePlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatePlanRepository extends JpaRepository<RatePlan, Long> {
}
