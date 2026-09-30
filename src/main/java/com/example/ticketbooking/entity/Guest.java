package com.example.ticketbooking.entity;

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

    @NotBlank(message = "Вы обязаны ввести имя")
    private String firstName;

    @NotBlank(message = "Вы обязаны ввести фамилию")
    private String lastName;

    @NotBlank
    @Email(message = "Вы обязаны ввести верный email")
    private String email;

    private String passportNumber;

    @NotBlank(message = "Номер телефона должен быть введен")
    private String phoneNumber;
}
