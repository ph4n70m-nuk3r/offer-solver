package com.github.ph4n70mnuk3r.enums;

public enum PRODUCT_NAMES {
    MAYBELLINE_MASCARA("MaybellineMascara"),
    MAYBELLINE_LIPSTICK("MaybellineLipstick"),
    HAM_SANDWICH("HamSandwich"),
    BLT_SANDWICH("BLTSandwich"),
    PASTA_SALAD("PastaSalad"),
    FRIDGE_RAIDERS_PLAIN("FridgeRaidersPlain"),
    PORK_PIES_2PK("PorkPies2Pack"),
    COCA_COLA_500ML("CocaCola"),
    FRUIT_SMOOTHIE("FruitSmoothie"),
    WATER_250ML("Water250ML"),
    RED_GRAPES_250G("RedGrapes250G"),
    GREEN_GRAPES_250G("GreenGrapes250G"),
    PHYSALIS_150G("Physalis150G"),
    AVOCADOS_2PK("Avocados2Pack"),
    CAULIFLOWER("Cauliflower"),
    KELLOGS_CORNFLAKES("KellogsCornflakes"),
    NESTLE_NESQUIK("NestleNesquik");
    public final String label;
    PRODUCT_NAMES(String label) {
        this.label = label;
    }
}
