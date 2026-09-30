package com.example.ticketbooking.entity.enums;

public enum MealMenuPlan {
    ROOM_ONLY(
            "RO",
            "Room Only",
            "Accommodation only; no meals included.",
            0.00
    ),
    BED_AND_BREAKFAST(
            "BB",
            "Bed & Breakfast",
            "Includes daily breakfast buffer or set menu.",
            20.00
    ),
    HALF_BOARD(
            "HB",
            "Half Board",
            "Includes daily breakfast and one main meal (usually dinner).",
            45.00
    ),
    FULL_BOARD(
            "FB",
            "Full Board",
            "Includes breakfast, lunch, and dinner. Beverages excluded during lunch/dinner.",
            75.00
    ),
    ALL_INCLUSIVE(
            "AI",
            "All Inclusive",
            "Includes all meals, snacks, and selected alcoholic and non-alcoholic drinks.",
            120.00
    ),
    ULTRA_ALL_INCLUSIVE(
            "UAI",
            "Ultra All Inclusive",
            "Includes 24/7 dining, premium foreign spirits, room service, and specialty restaurants.",
            180.00
    );

    private final String code;
    private final String displayName;
    private final String description;
    private final double defaultDailyRate;

    MealPlan(String code, String displayName, String description, double defaultDailyRate) {
        this.code = code;
        this.displayName = displayName;
        this.description = description;
        this.defaultDailyRate = defaultDailyRate;
    }

    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public double getDefaultDailyRate() {
        return defaultDailyRate;
    }

    public double calculateCost(int numberOfGuests, int nights) {
        return this.defaultDailyRate * numberOfGuests * nights;
    }

    public static MealPlan fromCode(String code) {
        if (code == null) return ROOM_ONLY;
        for (MealPlan plan : values()) {
            if (plan.code.equalsIgnoreCase(code)) {
                return plan;
            }
        }
        throw new IllegalArgumentException("Unknown meal plan code: " + code);
    }
}