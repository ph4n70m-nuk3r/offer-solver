package com.github.ph4n70mnuk3r.enums;

public enum CATEGORY_NAMES {
    CEREALS("Cereals"),
    CEREALS__KELLOGS(CEREALS.label + '.' + "Kellogs"),
    CEREALS__NESTLE(CEREALS.label + '.' + "Nestle"),
    COSMETICS("Cosmetics"),
    COSMETICS__ELF(COSMETICS.label + '.' + "Elf"),
    COSMETICS__MAYBELLINE(COSMETICS.label + '.' + "Maybelline"),
    MEAL_DEAL("MealDeal"),
    MEAL_DEAL__MAIN(MEAL_DEAL.label + '.' + "Main"),
    MEAL_DEAL__SNACK(MEAL_DEAL.label + '.' + "Snack"),
    MEAL_DEAL__DRINK(MEAL_DEAL.label + '.' + "Drink"),
    PRODUCE("Produce"),
    PRODUCE__FRUIT(PRODUCE.label + '.' + "Fruit"),
    PRODUCE__SALAD(PRODUCE.label + '.' + "Salad"),
    PRODUCE__VEGETABLES(PRODUCE.label + '.' + "Vegetables"),
    SNACKS("Snacks");

    public final String label;
    CATEGORY_NAMES(String label) {
        this.label = label;
    }
}