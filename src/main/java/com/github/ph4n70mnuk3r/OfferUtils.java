package com.github.ph4n70mnuk3r;

import lombok.NonNull;

import java.security.InvalidParameterException;
import java.util.Comparator;
import java.util.List;

public class OfferUtils {

/* OfferCheckers */

    /**
     * Helper function to create offer checker for single category offers.
     * @param category The category to check for.
     * @return the number of offers eligible
     */
    static OfferChecker threeForTwoOfferChecker(@NonNull Category category) {
        return threeForTwoOfferCheckerImpl(category);
    }

    /**
     * Helper function to create offer checker for multi-category offers.
     * @param categories The categories to check for.
     * @return the number of offers eligible
     */
    static OfferChecker threeForTwoOfferChecker(@NonNull Category ... categories) {
        return threeForTwoOfferCheckerImpl(categories);
    }

    private static OfferChecker threeForTwoOfferCheckerImpl(@NonNull Category ... categories) {
        if (categories.length == 0) {
            throw new InvalidParameterException("THREE FOR TWO OFFER CHECKER REQUIRES AT LEAST 1 CATEGORY!");
        }
        return basket -> {
            long totalProducts = basket.products.stream()
                    .filter(product -> product.isInCategory(categories))
                    .count();
            if (totalProducts > 3) {
                return Math.abs(totalProducts / 3);
            }
            return 0L;
        };
    }

    /**
     * Helper function to create offer checker for single category offers.
     * @param category The category to check for.
     * @return the number of offers eligible
     */
    static OfferChecker categoryOfferChecker(@NonNull Category category) {
        return categoryOfferCheckerImpl(category);
    }

    /**
     * Helper function to create offer checker for multi-category offers.
     * @param categories The categories to check for.
     * @return the number of offers eligible
     */
    static OfferChecker categoryOfferChecker(@NonNull Category ... categories) {
        return categoryOfferCheckerImpl(categories);
    }

    private static OfferChecker categoryOfferCheckerImpl(@NonNull Category ... categories) {
        if (categories.length == 0) {
            throw new InvalidParameterException("CATEGORY OFFER CHECKER REQUIRES AT LEAST 1 CATEGORY!");
        }
        return basket -> basket.products.stream()
                .filter(product -> product.isInCategory(categories))
                .count();
    }

    /**
     * Helper function to create offer checker for single category BOGOF offers.
     * @param category The category to check for.
     * @return the number of offers eligible
     */
    static OfferChecker BOGOFOfferChecker(@NonNull Category category) {
        return bogofOfferCheckerImpl(category);
    }

    /**
     * Helper function to create offer checker for multi-category BOGOF offers.
     * @param categories The categories to check for.
     * @return the number of offers eligible
     */
    static OfferChecker BOGOFOfferChecker(@NonNull Category ... categories) {
        return bogofOfferCheckerImpl(categories);
    }

    private static OfferChecker bogofOfferCheckerImpl(@NonNull Category ... categories) {
        if (categories.length == 0) {
            throw new InvalidParameterException("BOGOF OFFER CHECKER REQUIRES AT LEAST 1 CATEGORY!");
        }
        return basket -> {
            long totalProducts = basket.products.stream()
                    .filter(product -> product.isInCategory(categories))
                    .count();
            if (totalProducts > 1) {
                return Math.abs(totalProducts / 2);
            }
            return 0L;
        };
    }

/* OfferAppliers */

    /**
     * Helper function to return the best savings for BOGOF.
     * @param products the products to consider.
     * @return the best possible savings.
     */
    static Double bestBOGOFOffer(@NonNull List<Product> products) {
        if (products.size() < 2) {
            throw new InvalidParameterException("BOGOF OFFER REQUIRES AT LEAST 2 PRODUCTS!");
        }
        // If there are only 2 eligible products then return the cheapest.
        if (products.size() == 2) {
            return Math.min(products.get(0).price, products.get(1).price);
        }
        // ToDo: implement bestBogofOffer case where there are more than 2 products.
        throw new UnsupportedOperationException("BOGOF OFFER NOT IMPLEMENTED FOR MORE THAN 2 PRODUCTS!");
    }

    /**
     * Helper function to calculate the best savings for Meal Deals.
     * @param products the products to consider.
     * @return the best possible savings.
     */
    static Double bestMealDealOffer(@NonNull List<Product> products) {
        // ToDo: refactor to generic multi-category method.
        if (products.size() < 3) {
            throw new InvalidParameterException("MEAL DEAL OFFER REQUIRES AT LEAST 3 PRODUCTS!");
        }
        // If there are only 3 eligible products then ensure the price is no more than £3.
        if (products.size() == 3) {
            var maxPrice = 3D;
            var subTotal = products.stream().mapToDouble(product -> product.price).sum();
            return subTotal <= maxPrice ? 0D : subTotal - maxPrice;
        }
        // ToDo: implement bestMealDealOffer case where there are more than 3 products (1 main, 1 snack, 1 drink).
        throw new UnsupportedOperationException("MEAL DEAL OFFER NOT IMPLEMENTED FOR MORE THAN 3 PRODUCTS!");
    }

    /**
     * Helper function to return the best savings for Fixed Price Discounts.
     * @param products the products to consider.
     * @return the best possible savings.
     */
    static Double bestFixedPriceDiscountOffer(@NonNull List<Product> products) {
        if (products.isEmpty()) {
            throw new InvalidParameterException("FIXED PRICE DISCOUNT OFFER REQUIRES AT LEAST 1 PRODUCT!");
        }
        // ToDo: fix this method, it makes no sense, should be returning the discount amount not the product price...
        //       Should there ever be >1 product for this offer???
        // If there are only 1 eligible products then return the product's price.
        if (products.size() == 1) {
            return products.get(0).price;
        }
        // Return cheapest product.
        return products.stream().max(Comparator.comparing(o -> o.price)).get().price;
    }

    public static Double bestCereal50pOffOffer(@NonNull List<Product> products) {
        return bestFixedPriceDiscountOffer(products);
    }

    /**
     * Helper function to return the best savings for 3 For 2 offers.
     * @param products the products to consider.
     * @return the best possible savings.
     */
    static Double bestThreeForTwoDiscountOffer(@NonNull List<Product> products) {
        if (products.size() < 3) {
            throw new InvalidParameterException("THREE FOR TWO DISCOUNT OFFER REQUIRES AT LEAST 3 PRODUCT!");
        }
        // If there are only 3 eligible products then return the cheapest product.
        if (products.size() == 3) {
            return products.stream().min(Comparator.comparing(o -> o.price)).get().price;
        }
        // ToDo: implement bestThreeForTwoDiscountOffer case where there are more than three products.
        throw new UnsupportedOperationException("THREE FOR TWO DISCOUNT OFFER IS NOT IMPLEMENTED FOR MORE THAN THREE PRODUCTS!");
    }

    public static Double bestFruit3For2Offer(@NonNull List<Product> products) {
        return bestThreeForTwoDiscountOffer(products);
    }

    public static Double bestSnack50pOffOffer(@NonNull List<Product> products) {
        return bestFixedPriceDiscountOffer(products);
    }

    public static Double bestSmoothie70pOffOffer(@NonNull List<Product> products) {
        return bestFixedPriceDiscountOffer(products);
    }

    // ToDo: refactor fixed price discount offers into single method.
    // ToDo: refactor fruit 3 for 2 offer into generic 3 for 2 offer.
}
