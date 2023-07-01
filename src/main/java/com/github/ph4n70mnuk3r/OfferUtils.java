package com.github.ph4n70mnuk3r;

import java.security.InvalidParameterException;
import java.util.List;

public class OfferUtils {

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
            var maxPrice = 3D;
            var subTotal = products.stream().mapToDouble(product -> product.price).sum();
            return subTotal <= maxPrice ? 0D : subTotal - maxPrice;
        }
        // ToDo: implement case where there are more than 3 products (1 main, 1 snack, 1 drink).
        throw new UnsupportedOperationException("MEAL DEAL OFFER NOT IMPLEMENTED FOR MORE THAN 3 PRODUCTS!");
    }
}
