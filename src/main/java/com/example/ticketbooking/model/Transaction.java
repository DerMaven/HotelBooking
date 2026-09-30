package com.example.ticketbooking.model;

import com.example.ticketbooking.model.enums.Currency;
import com.example.ticketbooking.model.enums.PaymentMethod;
import com.example.ticketbooking.model.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@RequiredArgsConstructor
public class Transaction {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @PositiveOrZero
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    private PaymentMethod payment;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime processedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;
}
