package com.example.ticketbooking.dto;

public record GuestDto(
        Long guestId,
        String fullname,
        String phoneNumber,
        String email
) {
}
