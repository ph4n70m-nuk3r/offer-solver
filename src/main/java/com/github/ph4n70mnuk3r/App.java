package com.github.ph4n70mnuk3r;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.ph4n70mnuk3r.Constants.*;

/**
 * Class to tinker around with.
 */
public class App {
    // Category map for convenience.
    private static final Map<String, Category> categoryMap = createCategories();
    // Product map for convenience.
    private static final Map<String, Product> productMap = createProducts();
    // Offer map for convenience.
    private static final Map<String, Offer> offerMap = createOffers();
    // Basket for convenience.
    private static final Basket basket = createBasket();

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
    private static Map<String, Category> createCategories() {
        var categoryNames = Arrays.asList(
                CEREALS,
                CEREALS__KELLOGS,
                CEREALS__NESTLE,
                COSMETICS,
                COSMETICS__ELF,
                COSMETICS__MAYBELLINE,
                MEAL_DEAL,
                MEAL_DEAL__MAIN,
                MEAL_DEAL__SNACK,
                MEAL_DEAL__DRINK,
                PRODUCE,
                PRODUCE__FRUIT,
                PRODUCE__SALAD,
                PRODUCE__VEGETABLES,
                SNACKS
        );
        Map<String, Category> categoryMap = new HashMap<>(categoryNames.size());
        for (var categoryName : categoryNames) {
            categoryMap.put(categoryName, new Category(categoryName));
        }
        return categoryMap;
    }

    /**
     * Creates all products.
     * @return all products.
     */
    private static Map<String, Product> createProducts() {
        List<Product> products = Arrays.asList(
                new Product(1, MAYBELLINE_MASCARA, 1.50D, Arrays.asList(categoryMap.get(COSMETICS), categoryMap.get(COSMETICS__MAYBELLINE))),
                new Product(2, MAYBELLINE_LIPSTICK, 2.00D, Arrays.asList(categoryMap.get(COSMETICS), categoryMap.get(COSMETICS__MAYBELLINE))),
                new Product(3, HAM_SANDWICH, 1.75D, Arrays.asList(categoryMap.get(MEAL_DEAL), categoryMap.get(MEAL_DEAL__MAIN))),
                new Product(4, BLT_SANDWICH, 2.35D, Arrays.asList(categoryMap.get(MEAL_DEAL), categoryMap.get(MEAL_DEAL__MAIN))),
                new Product(5, PASTA_SALAD, 2.15D, Arrays.asList(categoryMap.get(MEAL_DEAL), categoryMap.get(MEAL_DEAL__MAIN))),
                new Product(6, FRIDGE_RAIDERS_PLAIN, 1.15D, Arrays.asList(categoryMap.get(MEAL_DEAL), categoryMap.get(MEAL_DEAL__SNACK), categoryMap.get(SNACKS))),
                new Product(7, PORK_PIES_2PK, 1.00D, Arrays.asList(categoryMap.get(MEAL_DEAL), categoryMap.get(MEAL_DEAL__SNACK), categoryMap.get(SNACKS))),
                new Product(8, COCA_COLA_500ML, 1.25D, Arrays.asList(categoryMap.get(MEAL_DEAL), categoryMap.get(MEAL_DEAL__DRINK))),
                new Product(9, FRUIT_SMOOTHIE, 1.75D, Arrays.asList(categoryMap.get(MEAL_DEAL), categoryMap.get(MEAL_DEAL__DRINK))),
                new Product(10, RED_GRAPES_250G, 1.75D, Arrays.asList(categoryMap.get(PRODUCE), categoryMap.get(PRODUCE__FRUIT))),
                new Product(11, GREEN_GRAPES_250G, 1.50D, Arrays.asList(categoryMap.get(PRODUCE), categoryMap.get(PRODUCE__FRUIT))),
                new Product(12, PHYSALIS_150G, 1.50D, Arrays.asList(categoryMap.get(PRODUCE), categoryMap.get(PRODUCE__FRUIT))),
                new Product(13, AVOCADOS_2PK, 2.00D, Arrays.asList(categoryMap.get(PRODUCE),categoryMap.get(PRODUCE__SALAD))),
                new Product(14, CAULIFLOWER, 1.00D, Arrays.asList(categoryMap.get(PRODUCE),categoryMap.get(PRODUCE__VEGETABLES))),
                new Product(15, KELLOGS_CORNFLAKES, 2.75, Arrays.asList(categoryMap.get(CEREALS), categoryMap.get(CEREALS__KELLOGS))),
                new Product(16, NESTLE_NESQUIK, 2.75, Arrays.asList(categoryMap.get(CEREALS), categoryMap.get(CEREALS__NESTLE)))
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
    private static Map<String, Offer> createOffers() {
        Map<String, Offer> offersMap = new HashMap<>();
        offersMap.put(MAYBELLINE_BOGOF_OFFER, createMaybellineBOGOFOffer());
        offersMap.put(MEAL_DEAL_OFFER, createMealDealOffer());
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
    private static Offer createMaybellineBOGOFOffer() {
        OfferChecker MaybellineBOGOFChecker = basket -> {
            long totalProducts = basket.products.stream()
                    .filter(product -> product.isInCategory(categoryMap.get(COSMETICS__MAYBELLINE)))
                    .count();
            if (totalProducts > 1) {
                return Math.abs(totalProducts / 2);
            }
            return 0L;
        };
        OfferApplier MaybellineBOGOFApplier = App::bestBogofOffer;
        return new Offer(MAYBELLINE_BOGOF_OFFER, MaybellineBOGOFChecker, MaybellineBOGOFApplier);
    }

    /**
     * Creates Meal Deal offer.
     * @return Meal Deal offer.
     */
    private static Offer createMealDealOffer() {
        OfferChecker MealDealChecker = basket -> {
            long totalMealDealMains = basket.products.stream()
                    .filter(product -> product.isInCategory(categoryMap.get(MEAL_DEAL__MAIN)))
                    .count();
            long totalMealDealSnacks = basket.products.stream()
                    .filter(product -> product.isInCategory(categoryMap.get(MEAL_DEAL__SNACK)))
                    .count();
            long totalMealDealDrinks = basket.products.stream()
                    .filter(product -> product.isInCategory(categoryMap.get(MEAL_DEAL__SNACK)))
                    .count();
            long min1 = Math.min(totalMealDealMains, totalMealDealSnacks);
            return Math.min(min1, totalMealDealDrinks);
        };
        OfferApplier MealDealApplier = App::bestMealDealOffer;
        return new Offer(MEAL_DEAL_OFFER, MealDealChecker, MealDealApplier);
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
        // ToDo: implement case where there are more than 3 products (1 main, 1 snack, 1 drink).
        return null;
    }

    /**
     * Helper function to create an example basket that can be used.
     * @return Basket which can be used to work with.
     */
    private static Basket createBasket() {
        return new Basket(Arrays.asList(
                productMap.get(MAYBELLINE_MASCARA),
                productMap.get(MAYBELLINE_MASCARA),
                productMap.get(MAYBELLINE_LIPSTICK),
                productMap.get(HAM_SANDWICH),
                productMap.get(BLT_SANDWICH),
                productMap.get(PASTA_SALAD),
                productMap.get(FRIDGE_RAIDERS_PLAIN),
                productMap.get(PORK_PIES_2PK),
                productMap.get(COCA_COLA_500ML),
                productMap.get(FRUIT_SMOOTHIE),
                productMap.get(RED_GRAPES_250G),
                productMap.get(RED_GRAPES_250G),
                productMap.get(GREEN_GRAPES_250G),
                productMap.get(PHYSALIS_150G),
                productMap.get(AVOCADOS_2PK),
                productMap.get(CAULIFLOWER)
        ));
    }
}