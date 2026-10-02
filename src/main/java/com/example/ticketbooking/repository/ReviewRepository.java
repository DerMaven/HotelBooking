package com.example.ticketbooking.repository;

import com.example.ticketbooking.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
