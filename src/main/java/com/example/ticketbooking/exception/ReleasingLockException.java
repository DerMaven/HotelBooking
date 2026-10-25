package com.example.ticketbooking.exception;

public class ReleasingLockException extends RuntimeException {
    public ReleasingLockException(String message) {
        super(message);
    }
}
