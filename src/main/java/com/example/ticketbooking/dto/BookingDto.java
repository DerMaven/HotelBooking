package com.example.ticketbooking.dto;

import java.time.LocalDateTime;

public record BookingDto(
        Long id,
        Long hotelId,
        Long roomId,
        Long guestId,
        Long bookingId,
        LocalDateTime checkIn,
        LocalDateTime checkOut
) {
}
