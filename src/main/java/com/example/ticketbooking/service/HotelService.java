package com.example.ticketbooking.service;

import com.example.ticketbooking.exception.HotelNotFoundException;
import com.example.ticketbooking.model.RatePlan;
import com.example.ticketbooking.model.enums.Currency;
import com.example.ticketbooking.repository.HotelRepository;
import com.example.ticketbooking.exception.RatePlanNotFoundException;
import com.example.ticketbooking.repository.RatePlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotelService {
    private final RatePlanRepository ratePlanRepository;
    private final HotelRepository hotelRepository;

    public RatePlan getRatePlan(Long ratePlanId) {
        return ratePlanRepository.findById(ratePlanId)
                .orElseThrow(() -> new RatePlanNotFoundException("Тариф не был найден"));
    }

    public Currency getCurrency(Long hotelId) {
        return hotelRepository.findById(hotelId)
                .orElseThrow(() -> new HotelNotFoundException("Отель не был найден"))
                .getCurrency();
    }
}
