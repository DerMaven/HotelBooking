package com.example.ticketbooking.service;

import com.example.ticketbooking.dto.GuestDto;
import com.example.ticketbooking.exception.GuestNotFoundException;
import com.example.ticketbooking.model.Guest;
import com.example.ticketbooking.repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GuestService {
    private final GuestRepository guestRepository;
    private final RedisCacheService redisCacheService;

    public GuestDto getById(Long guestId) {
        Optional<Guest> guestCache = redisCacheService.get(String.valueOf(guestId), Guest.class);
        if (guestCache.isPresent()) {
            Guest guest = guestCache.orElseThrow(() -> new GuestNotFoundException("Гость не был найден в кэше"));
            redisCacheService.set(String.valueOf(guestId), guest, Duration.ofMinutes(5));
            return new GuestDto(
                    guest.getId(),
                    guest.getFirstName() + " " + guest.getLastName(),
                    guest.getPhoneNumber(),
                    guest.getEmail()
                    );
        }
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new GuestNotFoundException("Гость не был найден в базе данных"));
        redisCacheService.set(String.valueOf(guest.getId()), guest, Duration.ofMinutes(10));
        return new GuestDto(
                guest.getId(),
                guest.getFirstName() + " " + guest.getLastName(),
                guest.getPhoneNumber(),
                guest.getEmail()
        );
    }
}
