package com.example.ticketbooking.model;

import com.example.ticketbooking.model.enums.MealMenuPlan;
import com.example.ticketbooking.model.enums.RoomType;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
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

    @PositiveOrZero
    private Byte minNightsStay;

    @PositiveOrZero
    private Byte maxNightsStay;

    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
}
