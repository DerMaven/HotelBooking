package com.example.ticketbooking.service;

import com.example.ticketbooking.model.RatePlan;
import com.example.ticketbooking.model.Transaction;
import com.example.ticketbooking.model.enums.Currency;
import com.example.ticketbooking.repository.RatePlanNotFoundException;
import com.example.ticketbooking.repository.RatePlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final RatePlanRepository ratePlanRepository;

    public String paymentLayer(Transaction transaction) {
        return "Платеж #" + transaction.getId() + " на сумму " + transaction.getAmount() +
                " " + transaction.getCurrency() + " был успешно выполнен.";
    }

    public boolean isHotelAndRatePlanCurrenciesEqual(Currency hotelCurrency, Currency ratePlanCurrency) {
        return Objects.equals(hotelCurrency, ratePlanCurrency);
    }

    public void setPriceByRatePlan(Transaction transaction, Long ratePlanId) {
        RatePlan ratePlan = ratePlanRepository.findById(ratePlanId)
                .orElseThrow(() -> new RatePlanNotFoundException("Тариф не был найден"));
        transaction.setAmount(ratePlan.getPrice());
    }
}
