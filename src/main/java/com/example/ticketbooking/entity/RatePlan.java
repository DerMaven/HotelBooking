package com.example.ticketbooking.entity;

import com.example.ticketbooking.entity.enums.MealMenuPlan;
import com.example.ticketbooking.entity.enums.RoomType;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "rate_plans")
public class RatePlan {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private MealMenuPlan menuPlan;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime date;

    private Byte minNightsStay;

    private Byte maxNightsStay;

    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
}
