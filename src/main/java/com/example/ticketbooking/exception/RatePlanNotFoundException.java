package com.example.ticketbooking.exception;

public class RatePlanNotFoundException extends RuntimeException {
    public RatePlanNotFoundException(String message) {
        super(message);
    }
}
