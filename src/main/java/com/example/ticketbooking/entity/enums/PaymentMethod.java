package com.example.ticketbooking.entity.enums;

public enum PaymentMethod {
    CREDIT_CARD("Credit Card", "Online card payment via credit card (Visa, Mastercard, Amex)", true, false),
    DEBIT_CARD("Debit Card", "Direct debit payment from bank account", true, false),
    BANK_TRANSFER("Bank Transfer", "Direct wire/SWIFT/IBAN transfer", false, false),
    CASH("Cash", "On-site cash payment upon arrival or checkout", false, true),
    PAYPAL("PayPal", "Online digital wallet transfer via PayPal", true, false),
    APPLE_PAY("Apple Pay", "Contactless/mobile wallet transaction via Apple Pay", true, false),
    GOOGLE_PAY("Google Pay", "Contactless/mobile wallet transaction via Google Pay", true, false),
    CRYPTO("Cryptocurrency", "Blockchain transaction (BTC, ETH, USDT)", true, false),
    GIFT_CARD("Gift Card / Voucher", "Pre-paid gift voucher or promotional balance", true, false);

    private final String displayName;
    private final String description;
    private final boolean isOnlinePayment;
    private final boolean requiresPayOnArrival;

    PaymentMethod(String displayName, String description, boolean isOnlinePayment, boolean requiresPayOnArrival) {
        this.displayName = displayName;
        this.description = description;
        this.isOnlinePayment = isOnlinePayment;
        this.requiresPayOnArrival = requiresPayOnArrival;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public boolean isOnlinePayment() {
        return isOnlinePayment;
    }

    public boolean isRequiresPayOnArrival() {
        return requiresPayOnArrival;
    }
}