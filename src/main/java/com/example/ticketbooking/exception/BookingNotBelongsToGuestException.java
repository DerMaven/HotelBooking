package com.example.ticketbooking.exception;

public class BookingNotBelongsToGuestException extends RuntimeException {
    public BookingNotBelongsToGuestException(String message) {
        super(message);
    }
}
