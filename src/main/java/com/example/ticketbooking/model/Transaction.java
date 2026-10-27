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
@Table(name = "transactions", indexes = {
        @Index(name = "idx_transactions_amount", columnList = "amount"),
        @Index(name = "idx_transactions_amount", columnList = "currency"),
        @Index(name = "idx_transactions_amount", columnList = "status")
})
@Getter
@Setter
@RequiredArgsConstructor
public class Transaction {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "amount")
    @PositiveOrZero
    private BigDecimal amount;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(name = "payment")
    @Enumerated(EnumType.STRING)
    private PaymentMethod payment;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(name = "processed_at")
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime processedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;
}
