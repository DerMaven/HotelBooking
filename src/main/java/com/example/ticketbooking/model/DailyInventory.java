package com.example.ticketbooking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "daily_inventories")
@Getter
@Setter
@RequiredArgsConstructor
public class DailyInventory {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @PositiveOrZero(message = "Value can't be negative.")
    private Long roomsAmount;

    @PositiveOrZero(message = "Value can't be negative.")
    private Long availableRooms;

    @DateTimeFormat
    private LocalDateTime inventoryDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
}
