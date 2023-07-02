package com.github.ph4n70mnuk3r.enums;

public enum OFFER_NAMES {

    // Offer names for convenience:
    MAYBELLINE_BOGOF_OFFER("MaybellineBOGOFOffer"),
    MEAL_DEAL_OFFER("MealDealOffer"),
    NESTLE_CEREAL_OFFER("NestleCerealOffer"),
    CEREAL_50P_OFF_OFFER("Cereal50pOffOffer"),
    FRUIT_3_FOR_2_OFFER("Fruit3For2Offer"),
    SNACK_50P_OFF_OFFER("Snack50pOffOffer"),
    SMOOTHIE_70P_OFF_OFFER("Smoothie70pOffOffer");
    public final String label;
    OFFER_NAMES(String label) {
        this.label = label;
    }
}
