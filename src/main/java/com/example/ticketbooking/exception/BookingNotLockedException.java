package com.example.ticketbooking.exception;

public class BookingNotLockedException extends RuntimeException {
    public BookingNotLockedException(String message) {
        super(message);
    }
}
