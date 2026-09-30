package com.example.ticketbooking.entity.enums;

public enum PaymentStatus {
    UNPAID("Unpaid", "No payment attempt has been made yet.", false, true),
    PENDING("Pending", "Payment is processing (e.g., bank transfer, 3D Secure, webhooks).", false, false),
    AUTHORIZED("Authorized", "Funds are held on the customer card but not yet captured.", false, true),
    PAID("Paid", "Payment completed and funds successfully captured.", true, false),
    PARTIALLY_REFUNDED("Partially Refunded", "A portion of the total amount was refunded.", true, false),
    REFUNDED("Refunded", "Full payment amount was refunded to the customer.", false, false),
    FAILED("Failed", "Payment transaction failed or was declined.", false, true),
    EXPIRED("Expired", "Payment link or session timed out before completion.", false, true);

    private final String displayName;
    private final String description;
    private final boolean settled;
    private final boolean requiresAction;

    PaymentStatus(String displayName, String description, boolean settled, boolean requiresAction) {
        this.displayName = displayName;
        this.description = description;
        this.settled = settled;
        this.requiresAction = requiresAction;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public boolean isSettled() {
        return settled;
    }

    public boolean isRequiresAction() {
        return requiresAction;
    }

    public boolean canTransitionTo(PaymentStatus nextStatus) {
        return switch (this) {
            case UNPAID -> nextStatus == PENDING || nextStatus == AUTHORIZED || nextStatus == PAID || nextStatus == EXPIRED;
            case PENDING -> nextStatus == PAID || nextStatus == FAILED || nextStatus == EXPIRED;
            case AUTHORIZED -> nextStatus == PAID || nextStatus == FAILED || nextStatus == EXPIRED;
            case PAID -> nextStatus == PARTIALLY_REFUNDED || nextStatus == REFUNDED;
            case PARTIALLY_REFUNDED -> nextStatus == REFUNDED;
            case FAILED, EXPIRED -> nextStatus == PENDING || nextStatus == PAID;
            case REFUNDED -> false;
        };
    }
}