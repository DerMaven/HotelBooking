package com.example.ticketbooking.exception;

public class StockDecrementException extends RuntimeException {
    public StockDecrementException(String message) {
        super(message);
    }
}
