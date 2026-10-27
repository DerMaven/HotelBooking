package com.example.ticketbooking.exception;

public class RatePlanNotBelongsToHotelException extends RuntimeException {
    public RatePlanNotBelongsToHotelException(String message) {
        super(message);
    }
}
