package com.example.ticketbooking.service;

import com.example.ticketbooking.exception.PaymentNotFoundException;
import com.example.ticketbooking.exception.RatePlanNotFoundException;
import com.example.ticketbooking.model.Payment;
import com.example.ticketbooking.model.RatePlan;
import com.example.ticketbooking.model.enums.Currency;
import com.example.ticketbooking.repository.RatePlanRepository;
import com.example.ticketbooking.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service("paymentService")
public class PaymentService extends MoneyService {
    private final RatePlanRepository ratePlanRepository;
    private final PaymentRepository paymentRepository;


    public PaymentService(RatePlanRepository ratePlanRepository, PaymentRepository paymentRepository) {
        super(ratePlanRepository, paymentRepository);
        this.ratePlanRepository = ratePlanRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public String paymentLayer(Payment payment) {
        return "Платеж #" + payment.getId() + " на сумму " + payment.getAmount() +
                " " + payment.getCurrency() + " был успешно выполнен.";
    }

    @Override
    public boolean isHotelAndRatePlanCurrenciesEqual(Currency hotelCurrency, Currency ratePlanCurrency) {
        return Objects.equals(hotelCurrency, ratePlanCurrency);
    }

    @Override
    public Payment getByBookingId(Long bookingId) {
        return paymentRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new PaymentNotFoundException("Платеж не был найден"));
    }

    @Override
    public void setPriceByRatePlan(Payment payment, Long ratePlanId, Long nights) {
        RatePlan ratePlan = ratePlanRepository.findById(ratePlanId)
                .orElseThrow(() -> new RatePlanNotFoundException("Тариф не был найден"));
        Long amount = ratePlan.getPrice().longValue() * nights;
        payment.setAmount(amount);
    }
}
