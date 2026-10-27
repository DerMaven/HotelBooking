package com.example.ticketbooking.exception;

public class NightsLimitReachedException extends RuntimeException {
    public NightsLimitReachedException(String message) {
        super(message);
    }
}
