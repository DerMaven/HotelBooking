package com.example.ticketbooking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "guests")
@Getter
@Setter
@RequiredArgsConstructor
public class Guest {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotBlank(message = "You must enter first name")
    private String firstName;

    @NotBlank(message = "You must enter last name")
    private String lastName;

    @NotBlank
    @Email(message = "You must enter a proper email")
    private String email;

    private String passportNumber;

    @NotBlank(message = "Phone number has to be entered")
    private String phoneNumber;
}
