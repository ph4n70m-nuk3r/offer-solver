package com.github.ph4n70mnuk3r;

import com.github.ph4n70mnuk3r.enums.CATEGORY_NAMES;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.ph4n70mnuk3r.OfferUtils.*;
import static com.github.ph4n70mnuk3r.enums.CATEGORY_NAMES.*;
import static com.github.ph4n70mnuk3r.enums.OFFER_NAMES.*;
import static com.github.ph4n70mnuk3r.enums.PRODUCT_NAMES.*;

public class TestUtils {
    // Category map for convenience.
    final Map<String, Category> categoryMap = createCategories();
    // Product map for convenience.
    final Map<String, Product> productMap = createProducts();
    // Offer map for convenience.
    final Map<String, Offer> offerMap = createOffers();
    // Basket for convenience.
    final Basket basket = createSampleBasket();

    /**
     * Creates all categories.
     * @return all categories.
     */
    Map<String, Category> createCategories() {
        Map<String, Category> categoryMap = new HashMap<>(CATEGORY_NAMES.values().length);
        for (var categoryName : CATEGORY_NAMES.values()) {
            categoryMap.put(categoryName.label, new Category(categoryName.label));
        }
        return categoryMap;
    }

    /**
     * Creates all products.
     * @return all products.
     */
    Map<String, Product> createProducts() {
        var id = 0;
        List<Product> products = Arrays.asList(
                new Product(++id, MAYBELLINE_MASCARA.label, 1.50D, Arrays.asList(categoryMap.get(COSMETICS.label), categoryMap.get(COSMETICS__MAYBELLINE.label))),
                new Product(++id, MAYBELLINE_LIPSTICK.label, 2.00D, Arrays.asList(categoryMap.get(COSMETICS.label), categoryMap.get(COSMETICS__MAYBELLINE.label))),
                new Product(++id, HAM_SANDWICH.label, 1.25D, Arrays.asList(categoryMap.get(MEAL_DEAL.label), categoryMap.get(MEAL_DEAL__MAIN.label))),
                new Product(++id, BLT_SANDWICH.label, 2.35D, Arrays.asList(categoryMap.get(MEAL_DEAL.label), categoryMap.get(MEAL_DEAL__MAIN.label))),
                new Product(++id, PASTA_SALAD.label, 2.15D, Arrays.asList(categoryMap.get(MEAL_DEAL.label), categoryMap.get(MEAL_DEAL__MAIN.label))),
                new Product(++id, FRIDGE_RAIDERS_PLAIN.label, 1.15D, Arrays.asList(categoryMap.get(MEAL_DEAL.label), categoryMap.get(MEAL_DEAL__SNACK.label), categoryMap.get(SNACKS.label))),
                new Product(++id, PORK_PIES_2PK.label, 1.00D, Arrays.asList(categoryMap.get(MEAL_DEAL.label), categoryMap.get(MEAL_DEAL__SNACK.label), categoryMap.get(SNACKS.label))),
                new Product(++id, COCA_COLA_500ML.label, 1.25D, Arrays.asList(categoryMap.get(MEAL_DEAL.label), categoryMap.get(MEAL_DEAL__DRINK.label))),
                new Product(++id, FRUIT_SMOOTHIE.label, 1.75D, Arrays.asList(categoryMap.get(MEAL_DEAL.label), categoryMap.get(MEAL_DEAL__DRINK.label))),
                new Product(++id, WATER_250ML.label, 0.60D, Arrays.asList(categoryMap.get(MEAL_DEAL.label), categoryMap.get(MEAL_DEAL__DRINK.label))),
                new Product(++id, RED_GRAPES_250G.label, 1.75D, Arrays.asList(categoryMap.get(PRODUCE.label), categoryMap.get(PRODUCE__FRUIT.label))),
                new Product(++id, GREEN_GRAPES_250G.label, 1.50D, Arrays.asList(categoryMap.get(PRODUCE.label), categoryMap.get(PRODUCE__FRUIT.label))),
                new Product(++id, PHYSALIS_150G.label, 1.50D, Arrays.asList(categoryMap.get(PRODUCE.label), categoryMap.get(PRODUCE__FRUIT.label))),
                new Product(++id, AVOCADOS_2PK.label, 2.00D, Arrays.asList(categoryMap.get(PRODUCE.label),categoryMap.get(PRODUCE__SALAD.label))),
                new Product(++id, CAULIFLOWER.label, 1.00D, Arrays.asList(categoryMap.get(PRODUCE.label),categoryMap.get(PRODUCE__VEGETABLES.label))),
                new Product(++id, KELLOGS_CORNFLAKES.label, 2.75, Arrays.asList(categoryMap.get(CEREALS.label), categoryMap.get(CEREALS__KELLOGS.label))),
                new Product(++id, NESTLE_NESQUIK.label, 2.75, Arrays.asList(categoryMap.get(CEREALS.label), categoryMap.get(CEREALS__NESTLE.label)))
        );
        Map<String, Product> productMap = new HashMap<>(products.size());
        // Add products to map.
        for (var product : products) {
            productMap.put(product.name, product);
        }
        return productMap;
    }

    /**
     * Method to construct offers.
     * @return a map containing all available offers.
     */
    Map<String, Offer> createOffers() {
        Map<String, Offer> offersMap = new HashMap<>();
        offersMap.put(MAYBELLINE_BOGOF_OFFER.label, createMaybellineBOGOFOffer());
        offersMap.put(MEAL_DEAL_OFFER.label, createMealDealOffer());
        offersMap.put(NESTLE_CEREAL_BOGOF_OFFER.label, createNestleCerealBOGOFOffer());
        offersMap.put(CEREAL_50P_OFF_OFFER.label, createCereal50pOffOffer());
        offersMap.put(FRUIT_3_FOR_2_OFFER.label, createFruit3For2Offer());
        offersMap.put(SNACK_50P_OFF_OFFER.label, createSnack50pOffOffer());
        offersMap.put(SMOOTHIE_70P_OFF_OFFER.label, createFruitSmoothie70pOffOffer());
        return offersMap;
    }

    /**
     * Creates Maybelline BOGOF offer.
     * @return Maybelline BOGOF offer.
     */
    Offer createMaybellineBOGOFOffer() {
        OfferChecker MaybellineBOGOFChecker = BOGOFOfferChecker(categoryMap.get(COSMETICS__MAYBELLINE.label));
        OfferApplier MaybellineBOGOFApplier = OfferUtils::bestBOGOFOffer;
        return new Offer(MAYBELLINE_BOGOF_OFFER.label, MaybellineBOGOFChecker, MaybellineBOGOFApplier);
    }

    /**
     * Creates Meal Deal offer.
     * @return Meal Deal offer.
     */
    Offer createMealDealOffer() {
        // ToDo: refactor MealDealChecker into generic single-product-in-multi-categories method in OfferUtils class.
        OfferChecker MealDealChecker = basket -> {
            long totalMealDealMains = basket.products.stream()
                    .filter(product -> product.isInCategory(categoryMap.get(MEAL_DEAL__MAIN.label)))
                    .count();
            long totalMealDealSnacks = basket.products.stream()
                    .filter(product -> product.isInCategory(categoryMap.get(MEAL_DEAL__SNACK.label)))
                    .count();
            long totalMealDealDrinks = basket.products.stream()
                    .filter(product -> product.isInCategory(categoryMap.get(MEAL_DEAL__SNACK.label)))
                    .count();
            // Number of eligible offers is bound by min number of products in any category.
            return Math.min(Math.min(totalMealDealMains, totalMealDealSnacks), totalMealDealDrinks);
        };
        OfferApplier MealDealApplier = OfferUtils::bestMealDealOffer;
        return new Offer(MEAL_DEAL_OFFER.label, MealDealChecker, MealDealApplier);
    }

    /**
     * Creates Nestle Cereal offer.
     * @return Nestle Cereal offer.
     */
    Offer createNestleCerealBOGOFOffer() {
        OfferChecker NestleCerealBOGOFChecker = BOGOFOfferChecker(categoryMap.get(CEREALS__NESTLE.label));
        OfferApplier NestleCerealBOGOFApplier = OfferUtils::bestBOGOFOffer;
        return new Offer(NESTLE_CEREAL_BOGOF_OFFER.label, NestleCerealBOGOFChecker, NestleCerealBOGOFApplier);
    }

    /**
     * Creates Cereal 50p offer.
     * @return Cereal 50p offer.
     */
    Offer createCereal50pOffOffer() {
        OfferChecker Cereal50pOffChecker = categoryOfferChecker(categoryMap.get(CEREALS.label));
        OfferApplier Cereal50pOffApplier = OfferUtils::bestCereal50pOffOffer;
        return new Offer(CEREAL_50P_OFF_OFFER.label, Cereal50pOffChecker, Cereal50pOffApplier);
    }

    /**
     * Creates Fruit 3 For 2 offer.
     * @return Fruit 3 For 2 offer.
     */
    Offer createFruit3For2Offer() {
        OfferChecker Fruit3For2Checker = threeForTwoOfferChecker(categoryMap.get(PRODUCE__FRUIT.label));
        OfferApplier Fruit3For2Applier = OfferUtils::bestFruit3For2Offer;
        return new Offer(FRUIT_3_FOR_2_OFFER.label, Fruit3For2Checker, Fruit3For2Applier);
    }

    /**
     * Creates Snack 50p Off offer.
     * @return Snack 50p Off offer.
     */
    Offer createSnack50pOffOffer() {
        OfferChecker Snack50pOffChecker = categoryOfferChecker(categoryMap.get(SNACKS.label));
        OfferApplier Snack50pOffApplier = OfferUtils::bestSnack50pOffOffer;
        return new Offer(SNACK_50P_OFF_OFFER.label, Snack50pOffChecker, Snack50pOffApplier);
    }

    /**
     * Creates Snack 50p Off offer.
     * @return Snack 50p Off offer.
     */
    Offer createFruitSmoothie70pOffOffer() {
        // ToDo: Refactor to generic method in OfferUtils.
        OfferChecker Smoothie70pOffChecker = basket -> basket.products.stream()
                .filter(product -> product.equals(productMap.get(FRUIT_SMOOTHIE.label))).count();
        OfferApplier Smoothie70pOffApplier = OfferUtils::bestSmoothie70pOffOffer;
        return new Offer(SMOOTHIE_70P_OFF_OFFER.label, Smoothie70pOffChecker, Smoothie70pOffApplier);
    }

    /**
     * Helper function to create an example basket that can be used.
     * @return Basket which can be used to work with.
     */
    Basket createSampleBasket() {
        return new Basket(Arrays.asList(
                productMap.get(MAYBELLINE_MASCARA.label),
                productMap.get(MAYBELLINE_MASCARA.label),
                productMap.get(MAYBELLINE_LIPSTICK.label),
                productMap.get(HAM_SANDWICH.label),
                productMap.get(BLT_SANDWICH.label),
                productMap.get(PASTA_SALAD.label),
                productMap.get(FRIDGE_RAIDERS_PLAIN.label),
                productMap.get(PORK_PIES_2PK.label),
                productMap.get(COCA_COLA_500ML.label),
                productMap.get(WATER_250ML.label),
                productMap.get(FRUIT_SMOOTHIE.label),
                productMap.get(RED_GRAPES_250G.label),
                productMap.get(RED_GRAPES_250G.label),
                productMap.get(GREEN_GRAPES_250G.label),
                productMap.get(PHYSALIS_150G.label),
                productMap.get(AVOCADOS_2PK.label),
                productMap.get(CAULIFLOWER.label),
                productMap.get(KELLOGS_CORNFLAKES.label),
                productMap.get(NESTLE_NESQUIK.label)
        ));
    }
}
