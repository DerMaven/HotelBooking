package com.example.ticketbooking.entity;

import com.example.ticketbooking.entity.enums.Currency;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "hotels")
@Getter
@Setter
@RequiredArgsConstructor
public class Hotel {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotBlank(message = "Вы обязаны название отеля")
    private String name;

    @NotBlank(message = "Вы обязаны описание отеля")
    private String description;

    @NotBlank(message = "Вы обязаны описание отеля")
    private String country;

    @NotBlank(message = "Вы обязаны описание отеля")
    private String city;

    @NotBlank(message = "Вы обязаны описание отеля")
    private String address;

    @Enumerated(EnumType.STRING)
    private Currency currency;
}
