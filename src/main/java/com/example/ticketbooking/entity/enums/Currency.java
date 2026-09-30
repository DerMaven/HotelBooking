package com.example.ticketbooking.entity.enums;

public enum Currency {
    USD("USD", "$", "United States Dollar"),
    EUR("EUR", "€", "Euro"),
    GBP("GBP", "£", "British Pound Sterling"),
    JPY("JPY", "¥", "Japanese Yen"),
    CAD("CAD", "CA$", "Canadian Dollar"),
    AUD("AUD", "A$", "Australian Dollar"),
    CHF("CHF", "CHF", "Swiss Franc"),
    CNY("CNY", "¥", "Chinese Yuan"),
    INR("INR", "₹", "Indian Rupee"),
    BRL("BRL", "R$", "Brazilian Real");

    private final String code;
    private final String symbol;
    private final String displayName;

    Currency(String code, String symbol, String displayName) {
        this.code = code;
        this.symbol = symbol;
        this.displayName = displayName;
    }

    public String getCode() {
        return code;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Currency fromCode(String code) {
        if (code == null) {
            return null;
        }
        for (Currency currency : values()) {
            if (currency.code.equalsIgnoreCase(code)) {
                return currency;
            }
        }
        throw new RuntimeException("Неизвестный код валюты: " + code);
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", displayName, symbol);
    }
}