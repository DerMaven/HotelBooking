package com.example.ticketbooking.service;

import com.example.ticketbooking.dto.BookingDto;
import com.example.ticketbooking.exception.BookingNotFoundException;
import com.example.ticketbooking.exception.GuestNotFoundException;
import com.example.ticketbooking.exception.HotelNotFoundException;
import com.example.ticketbooking.exception.RoomNotFoundException;
import com.example.ticketbooking.model.Booking;
import com.example.ticketbooking.model.Guest;
import com.example.ticketbooking.model.Hotel;
import com.example.ticketbooking.model.Room;
import com.example.ticketbooking.model.enums.BookingStatus;
import com.example.ticketbooking.repository.BookingRepository;
import com.example.ticketbooking.repository.GuestRepository;
import com.example.ticketbooking.repository.HotelRepository;
import com.example.ticketbooking.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;

    public List<Booking> getByFullName(String firstName, String lastName) {
        return bookingRepository.findByFullName(firstName, lastName);
    }

    public Booking getByCheckInAndFullName(LocalDateTime checkIn, String firstName, String lastName) {
        return bookingRepository.findByCheckInDateAndFullName(checkIn, firstName, lastName)
                .orElseThrow(() -> new BookingNotFoundException("Booking was not found"));
    }
}
