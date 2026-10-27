package com.example.ticketbooking.exception;

public class RoomNotBelongsToHotelException extends RuntimeException {
    public RoomNotBelongsToHotelException(String message) {
        super(message);
    }
}
