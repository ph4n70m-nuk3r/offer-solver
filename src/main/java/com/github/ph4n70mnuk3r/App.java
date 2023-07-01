package com.github.ph4n70mnuk3r;

import com.github.ph4n70mnuk3r.enums.CATEGORY_NAMES;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.ph4n70mnuk3r.enums.CATEGORY_NAMES.*;
import static com.github.ph4n70mnuk3r.enums.OFFER_NAMES.*;
import static com.github.ph4n70mnuk3r.enums.PRODUCT_NAMES.*;

/**
 * Class to tinker around with.
 */
public class App {
    // Category map for convenience.
    static final Map<String, Category> categoryMap = createCategories();
    // Product map for convenience.
    static final Map<String, Product> productMap = createProducts();
    // Offer map for convenience.
    static final Map<String, Offer> offerMap = createOffers();
    // Basket for convenience.
    static final Basket basket = createSampleBasket();

    public static void main(String[] args) {
        System.out.println(">>> CATEGORIES <<<");
        categoryMap.values().stream().sorted().forEach(System.out::println);

        System.out.println(">>> PRODUCTS <<<");
        productMap.values().stream().sorted().forEach(System.out::println);

        System.out.println(">>> OFFERS <<<");
        offerMap.values().stream().sorted().forEach(System.out::println);

        System.out.println(">>> BASKET <<<");
        basket.products.stream().sorted().forEach(System.out::println);
    }

    /**
     * Creates all categories.
     * @return all categories.
     */
    static Map<String, Category> createCategories() {
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
    static Map<String, Product> createProducts() {
        List<Product> products = Arrays.asList(
                new Product(1, MAYBELLINE_MASCARA.label, 1.50D, Arrays.asList(categoryMap.get(COSMETICS.label), categoryMap.get(COSMETICS__MAYBELLINE.label))),
                new Product(2, MAYBELLINE_LIPSTICK.label, 2.00D, Arrays.asList(categoryMap.get(COSMETICS.label), categoryMap.get(COSMETICS__MAYBELLINE.label))),
                new Product(3, HAM_SANDWICH.label, 1.75D, Arrays.asList(categoryMap.get(MEAL_DEAL.label), categoryMap.get(MEAL_DEAL__MAIN.label))),
                new Product(4, BLT_SANDWICH.label, 2.35D, Arrays.asList(categoryMap.get(MEAL_DEAL.label), categoryMap.get(MEAL_DEAL__MAIN.label))),
                new Product(5, PASTA_SALAD.label, 2.15D, Arrays.asList(categoryMap.get(MEAL_DEAL.label), categoryMap.get(MEAL_DEAL__MAIN.label))),
                new Product(6, FRIDGE_RAIDERS_PLAIN.label, 1.15D, Arrays.asList(categoryMap.get(MEAL_DEAL.label), categoryMap.get(MEAL_DEAL__SNACK.label), categoryMap.get(SNACKS.label))),
                new Product(7, PORK_PIES_2PK.label, 1.00D, Arrays.asList(categoryMap.get(MEAL_DEAL.label), categoryMap.get(MEAL_DEAL__SNACK.label), categoryMap.get(SNACKS.label))),
                new Product(8, COCA_COLA_500ML.label, 1.25D, Arrays.asList(categoryMap.get(MEAL_DEAL.label), categoryMap.get(MEAL_DEAL__DRINK.label))),
                new Product(9, FRUIT_SMOOTHIE.label, 1.75D, Arrays.asList(categoryMap.get(MEAL_DEAL.label), categoryMap.get(MEAL_DEAL__DRINK.label))),
                new Product(10, RED_GRAPES_250G.label, 1.75D, Arrays.asList(categoryMap.get(PRODUCE.label), categoryMap.get(PRODUCE__FRUIT.label))),
                new Product(11, GREEN_GRAPES_250G.label, 1.50D, Arrays.asList(categoryMap.get(PRODUCE.label), categoryMap.get(PRODUCE__FRUIT.label))),
                new Product(12, PHYSALIS_150G.label, 1.50D, Arrays.asList(categoryMap.get(PRODUCE.label), categoryMap.get(PRODUCE__FRUIT.label))),
                new Product(13, AVOCADOS_2PK.label, 2.00D, Arrays.asList(categoryMap.get(PRODUCE.label),categoryMap.get(PRODUCE__SALAD.label))),
                new Product(14, CAULIFLOWER.label, 1.00D, Arrays.asList(categoryMap.get(PRODUCE.label),categoryMap.get(PRODUCE__VEGETABLES.label))),
                new Product(15, KELLOGS_CORNFLAKES.label, 2.75, Arrays.asList(categoryMap.get(CEREALS.label), categoryMap.get(CEREALS__KELLOGS.label))),
                new Product(16, NESTLE_NESQUIK.label, 2.75, Arrays.asList(categoryMap.get(CEREALS.label), categoryMap.get(CEREALS__NESTLE.label)))
        );
        Map<String, Product> productMap = new HashMap<>(products.size());
        for (var product : products) {
            productMap.put(product.name, product);
        }
        return productMap;
    }

    /**
     * Method to construct offers.
     * @return a map containing all available offers.
     */
    static Map<String, Offer> createOffers() {
        Map<String, Offer> offersMap = new HashMap<>();
        offersMap.put(MAYBELLINE_BOGOF_OFFER.label, createMaybellineBOGOFOffer());
        offersMap.put(MEAL_DEAL_OFFER.label, createMealDealOffer());
        /* ToDo:
        *    - nestle cereal offer
        *    - all cereal 50p off offer
        *    - fruit 3-for-2 offer
        *    - snack 50p off offer
        *    - smoothie 70p off offer
        */
        return offersMap;
    }

    /**
     * Creates Maybelline BOGOF offer.
     * @return Maybelline BOGOF offer.
     */
    static Offer createMaybellineBOGOFOffer() {
        OfferChecker MaybellineBOGOFChecker = basket -> {
            long totalProducts = basket.products.stream()
                    .filter(product -> product.isInCategory(categoryMap.get(COSMETICS__MAYBELLINE.label)))
                    .count();
            if (totalProducts > 1) {
                return Math.abs(totalProducts / 2);
            }
            return 0L;
        };
        OfferApplier MaybellineBOGOFApplier = App::bestBogofOffer;
        return new Offer(MAYBELLINE_BOGOF_OFFER.label, MaybellineBOGOFChecker, MaybellineBOGOFApplier);
    }

    /**
     * Creates Meal Deal offer.
     * @return Meal Deal offer.
     */
    static Offer createMealDealOffer() {
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
            long min1 = Math.min(totalMealDealMains, totalMealDealSnacks);
            return Math.min(min1, totalMealDealDrinks);
        };
        OfferApplier MealDealApplier = App::bestMealDealOffer;
        return new Offer(MEAL_DEAL_OFFER.label, MealDealChecker, MealDealApplier);
    }

    /**
     * Helper function to return the best savings for BOGOF.
     * @param products the products to consider.
     * @return the best possible savings.
     */
    static Double bestBogofOffer(List<Product> products) {
        if (products.size() < 2) {
            throw new InvalidParameterException("BOGOF OFFER REQUIRES AT LEAST 2 PRODUCTS!");
        }
        // If there are only 2 eligible products then return the cheapest.
        if (products.size() == 2) {
            return Math.min(products.get(0).price, products.get(1).price);
        }
        // ToDo: implement case where there are more than 2 products.
        throw new UnsupportedOperationException("BOGOF OFFER NOT IMPLEMENTED FOR MORE THAN 2 PRODUCTS!");
    }

    /**
     * Helper function to calculate the best savings for Meal Deals.
     * @param products the products to consider.
     * @return the best possible savings.
     */
    static Double bestMealDealOffer(List<Product> products) {
        if (products.size() < 3) {
            throw new InvalidParameterException("MEAL DEAL OFFER REQUIRES AT LEAST 3 PRODUCTS!");
        }
        // If there are only 3 eligible products then ensure the price is no more than £3.
        if (products.size() == 3) {
            return Math.min(3D, products.stream().mapToDouble(product -> product.price).sum());
        }
        // ToDo: implement case where there are more than 3 products (1 main, 1 snack, 1 drink).
        throw new UnsupportedOperationException("MEAL DEAL OFFER NOT IMPLEMENTED FOR MORE THAN 3 PRODUCTS!");
    }

    /**
     * Helper function to create an example basket that can be used.
     * @return Basket which can be used to work with.
     */
    static Basket createSampleBasket() {
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
                productMap.get(FRUIT_SMOOTHIE.label),
                productMap.get(RED_GRAPES_250G.label),
                productMap.get(RED_GRAPES_250G.label),
                productMap.get(GREEN_GRAPES_250G.label),
                productMap.get(PHYSALIS_150G.label),
                productMap.get(AVOCADOS_2PK.label),
                productMap.get(CAULIFLOWER.label)
        ));
    }
}
