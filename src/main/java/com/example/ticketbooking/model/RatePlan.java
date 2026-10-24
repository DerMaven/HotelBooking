package com.example.ticketbooking.model;

import com.example.ticketbooking.model.enums.MealMenuPlan;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Entity
@Table(name = "rate_plans")
public class RatePlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MealMenuPlan menuPlan;

    @PositiveOrZero
    private BigDecimal price;

    @NotBlank
    private String currency;

    @PositiveOrZero
    private Byte minNightsStay;

    @PositiveOrZero
    private Byte maxNightsStay;

    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
}
