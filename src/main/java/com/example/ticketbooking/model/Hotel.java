package com.example.ticketbooking.model;

import com.example.ticketbooking.model.enums.Currency;
import jakarta.persistence.*;
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

    @NotBlank(message = "You must enter hotel's name")
    private String name;

    @NotBlank(message = "You must enter hotel's description")
    private String description;

    @NotBlank(message = "You must enter hotel's country")
    private String country;

    @NotBlank(message = "You must enter hotel's city")
    private String city;

    @NotBlank(message = "You must enter hotel's address")
    private String address;

    @Enumerated(EnumType.STRING)
    private Currency currency;
}
