package com.example.ticketbooking.dto;

import com.example.ticketbooking.model.enums.Currency;
import com.example.ticketbooking.model.enums.RoomType;

import java.time.LocalDate;

public record BookingDto(
        Long bookingId,
        Long roomId,
        RoomType roomType,
        LocalDate checkIn,
        LocalDate checkOut,
        Long nights,
        Long pricePerNight,
        Long totalAmount,
        Currency currency,
        Boolean isAvailable,
        Long ratePlanId
) {
}
