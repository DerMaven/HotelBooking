package com.example.ticketbooking.model;

import com.example.ticketbooking.model.enums.Currency;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "hotels", indexes = {
        @Index(name = "idx_hotels_name", columnList = "name"),
        @Index(name = "idx_hotels_city", columnList = "city"),
        @Index(name = "idx_hotels_currency", columnList = "currency")
})
@Getter
@Setter
@RequiredArgsConstructor
public class Hotel {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "You must enter hotel's name")
    private String name;

    @Column(name = "description")
    @NotBlank(message = "You must enter hotel's description")
    private String description;

    @Column(name = "country")
    @NotBlank(message = "You must enter hotel's country")
    private String country;

    @Column(name = "city")
    @NotBlank(message = "You must enter hotel's city")
    private String city;

    @Column(name = "address")
    @NotBlank(message = "You must enter hotel's address")
    private String address;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;
}
