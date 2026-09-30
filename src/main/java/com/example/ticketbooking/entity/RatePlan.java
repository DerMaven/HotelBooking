package com.example.ticketbooking.entity;

import com.example.ticketbooking.entity.enums.MealMenuPlan;
import jakarta.persistence.*;

@Entity
@Table(name = "rate_plans")
public class RatePlan {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private MealMenuPlan menuPlan;

    private Byte minNightsStay;

    private Byte maxNightsStay;

    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
}
