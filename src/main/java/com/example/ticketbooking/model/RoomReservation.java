package com.example.ticketbooking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "room_reservations")
@Getter
@Setter
@RequiredArgsConstructor
public class RoomReservation {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @PositiveOrZero(message = "Значение не может быть ниже нуля")
    private Long childrenAmount;

    @PositiveOrZero(message = "Значение не может быть ниже нуля")
    private Long adultAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;
}