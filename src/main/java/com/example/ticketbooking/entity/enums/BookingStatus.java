package com.example.ticketbooking.entity.enums;

public enum BookingStatus {
    PENDING("Pending", "Booking initiated; awaiting payment confirmation or approval.", false, true),
    CONFIRMED("Confirmed", "Reservation fully paid or guaranteed; room is reserved.", true, true),
    CHECKED_IN("Checked In", "Guest has arrived and registered at the property.", true, false),
    CHECKED_OUT("Checked Out", "Guest has completed stay and departed.", true, false),
    CANCELLED("Cancelled", "Booking cancelled by guest or property prior to check-in.", false, false),
    NO_SHOW("No Show", "Guest failed to arrive without cancelling in time.", false, false),
    REFUNDED("Refunded", "Booking cancelled and payment fully or partially refunded.", false, false);

    private final String displayName;
    private final String description;
    private final boolean active;
    private final boolean cancellable;

    BookingStatus(String displayName, String description, boolean active, boolean cancellable) {
        this.displayName = displayName;
        this.description = description;
        this.active = active;
        this.cancellable = cancellable;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isCancellable() {
        return cancellable;
    }

    public boolean canTransitionTo(BookingStatus nextStatus) {
        return switch (this) {
            case PENDING -> nextStatus == CONFIRMED || nextStatus == CANCELLED;
            case CONFIRMED -> nextStatus == CHECKED_IN || nextStatus == CANCELLED || nextStatus == NO_SHOW;
            case CHECKED_IN -> nextStatus == CHECKED_OUT;
            case CANCELLED -> nextStatus == REFUNDED;
            case CHECKED_OUT, NO_SHOW, REFUNDED -> false;
        };
    }
}