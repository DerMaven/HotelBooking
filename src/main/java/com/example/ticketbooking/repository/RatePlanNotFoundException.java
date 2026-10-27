package com.example.ticketbooking.repository;

public class RatePlanNotFoundException extends RuntimeException {
    public RatePlanNotFoundException(String message) {
        super(message);
    }
}
