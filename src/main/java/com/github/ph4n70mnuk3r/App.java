package com.github.ph4n70mnuk3r;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.*;
import static com.github.ph4n70mnuk3r.Constants.*;

/**
 * Class to tinker around with.
 */
public class App {
    // Category map for convenience.
    private static final Map<String, Category> categoryMap = new HashMap<>();
    // Product map for convenience.
    private static final Multimap<String, Product> productMap = ArrayListMultimap.create();
    // Offer map for convenience.
    private static final Map<String, Offer> offerMap = new HashMap<>();

    public static void main(String[] args) {
        createCategories();
        createProducts();
        createMaybellineBOGOFOffer();
        createMealDealOffer();

        System.out.println(">>> PRODUCTS <<<");
        productMap.values().stream().sorted().forEach(System.out::println);

        System.out.println(">>> OFFERS <<<");
        offerMap.values().stream().sorted().forEach(System.out::println);
    }

    /**
     * Add all categories to the map of available categories.
     */
    public static void createCategories() {
        // ToDo: add remaining categories.
        var categoryNames = Arrays.asList(
                MAYBELLINE_COSMETICS,
                MEAL_DEAL_MAIN,
                MEAL_DEAL_SNACK,
                MEAL_DEAL_DRINK
        );
        for (var categoryName : categoryNames) {
            categoryMap.put(categoryName, new Category(categoryName));
        }
    }

    /**
     * Add all products to the map of available products.
     */
    public static void createProducts() {
        // ToDo: add remaining products.
        List<Product> products = Arrays.asList(
                new Product(1, MAYBELLINE_MASCARA, 1.50D, Collections.singletonList(categoryMap.get(MAYBELLINE_COSMETICS))),
                new Product(2, MAYBELLINE_LIPSTICK, 2.50D, Collections.singletonList(categoryMap.get(MAYBELLINE_COSMETICS))),
                new Product(3, MAYBELLINE_LIPSTICK, 2.50D, Collections.singletonList(categoryMap.get(MAYBELLINE_COSMETICS))),
                new Product(4, HAM_SANDWICH, 1.75D, Collections.singletonList(categoryMap.get(MEAL_DEAL_MAIN))),
                new Product(5, FRIDGE_RAIDERS, 1.35D, Collections.singletonList(categoryMap.get(MEAL_DEAL_SNACK))),
                new Product(6, COCA_COLA, 1.25D, Collections.singletonList(categoryMap.get(MEAL_DEAL_DRINK)))
        );
        for (var product : products) {
            productMap.put(product.name, product);
        }
    }

    /**
     * Add the Maybelline BOGOF offer to the map of available offers.
     */
    private static void createMaybellineBOGOFOffer() {
        OfferChecker MaybellineBOGOFChecker = basket -> {
            long totalProducts = basket.products.stream()
                    .filter(product -> product.isInCategory(categoryMap.get(MAYBELLINE_COSMETICS)))
                    .count();
            if (totalProducts > 1) {
                return Math.abs(totalProducts / 2);
            }
            return 0L;
        };
        OfferApplier MaybellineBOGOFApplier = App::bestBogofOffer;
        offerMap.put(MAYBELLINE_BOGOF_OFFER, new Offer(MAYBELLINE_BOGOF_OFFER, MaybellineBOGOFChecker, MaybellineBOGOFApplier));
    }

    /**
     * Add the Meal Deal offer to the map of available offers.
     */
    private static void createMealDealOffer() {
        OfferChecker MealDealChecker = basket -> {
            long totalMealDealMains = basket.products.stream()
                    .filter(product -> product.isInCategory(categoryMap.get(MEAL_DEAL_MAIN)))
                    .count();
            long totalMealDealSnacks = basket.products.stream()
                    .filter(product -> product.isInCategory(categoryMap.get(MEAL_DEAL_SNACK)))
                    .count();
            long totalMealDealDrinks = basket.products.stream()
                    .filter(product -> product.isInCategory(categoryMap.get(MEAL_DEAL_SNACK)))
                    .count();
            long min1 = Math.min(totalMealDealMains, totalMealDealSnacks);
            return Math.min(min1, totalMealDealDrinks);
        };
        OfferApplier MealDealApplier = App::bestMealDealOffer;
        offerMap.put(MEAL_DEAL_OFFER, new Offer(MEAL_DEAL_OFFER, MealDealChecker, MealDealApplier));
    }

    /**
     * Helper function to return the best savings for BOGOF.
     * @param products the products to consider.
     * @return the best possible savings.
     */
    private static Double bestBogofOffer(List<Product> products) {
        // If there are only 2 eligible products then return the cheapest.
        if (products.size() == 2) {
            return Math.min(products.get(0).price, products.get(1).price);
        }
        // ToDo: implement case where there are more than 2 products.
        return null;
    }

    /**
     * Helper function to calculate the best savings for Meal Deals.
     * @param products the products to consider.
     * @return the best possible savings.
     */
    private static Double bestMealDealOffer(List<Product> products) {
        // If there are only 3 eligible products then ensure the price is no more than £3.
        if (products.size() == 3) {
            return Math.min(3D, products.stream().mapToDouble(product -> product.price).sum());
        }
        // ToDo: implement case where there are more than 3 products.
        return null;
    }
}