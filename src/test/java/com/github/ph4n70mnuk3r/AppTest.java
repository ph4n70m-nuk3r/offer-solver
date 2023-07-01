package com.github.ph4n70mnuk3r;

import com.github.ph4n70mnuk3r.enums.CATEGORY_NAMES;
import com.github.ph4n70mnuk3r.enums.PRODUCT_NAMES;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import static com.github.ph4n70mnuk3r.enums.OFFER_NAMES.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class.
 */
public class AppTest {
    @Test
    void appMainTest() {
        /* Nothing to test. */
        App.main(new String[0]);
    }

    @Test
    void createCategoriesTest() {
        var categoryMap = App.createCategories();
        // Check expected number of categories exist.
        assertTrue(CATEGORY_NAMES.values().length > 0);
        assertEquals(CATEGORY_NAMES.values().length, categoryMap.size());
        // Check all enum values exist in categoryMap.
        for (var categoryName : CATEGORY_NAMES.values()) {
            assertTrue(categoryMap.containsKey(categoryName.label));
            assertTrue(categoryMap.containsValue(new Category(categoryName.label)));
        }
    }
    @Test
    void createProductsTest() {
        var productMap = App.createProducts();
        // Check expected number of products exist.
        assertTrue(productMap.size() > 0);
        assertEquals(PRODUCT_NAMES.values().length, productMap.size());
        // Check all enum values exist in productMap.
        for (var productName : PRODUCT_NAMES.values()) {
            assertTrue(productMap.containsKey(productName.label));
            assertEquals(productName.label, productMap.get(productName.label).name);
        }
    }
    @Test
    void createMaybellineBOGOFOfferTest() {
        var maybellineBOGOFOffer = App.createMaybellineBOGOFOffer();
        // Check offer name is as expected.
        assertEquals(MAYBELLINE_BOGOF_OFFER.label, maybellineBOGOFOffer.name);

        var productMap = App.createProducts();
        var maybellineLipstick = productMap.get(PRODUCT_NAMES.MAYBELLINE_LIPSTICK.label);
        var maybellineMascara = productMap.get(PRODUCT_NAMES.MAYBELLINE_MASCARA.label);
        var cheapest = Math.min(maybellineLipstick.price, maybellineMascara.price);
        {
            var basket = new Basket(new ArrayList<>());
            // Check offer is applicable 0 times to an empty basket.
            assertEquals(0L, maybellineBOGOFOffer.isApplicable(basket));
            // Check offer throws if 0 items in basket.
            assertThrows(InvalidParameterException.class, () -> maybellineBOGOFOffer.applyOffer(basket.products));
            // Check offer throws if 1 item in basket.
            basket.products.add(maybellineLipstick);
            assertThrows(InvalidParameterException.class, () -> maybellineBOGOFOffer.applyOffer(basket.products));
            // Check offer is applicable 1 time to basket with 2 Maybelline lipsticks.
            basket.products.add(maybellineLipstick);
            assertEquals(1L, maybellineBOGOFOffer.isApplicable(basket));
            // Check correct amount to subtract is returned by applyOffer(basket.products);
            assertEquals(maybellineLipstick.price, maybellineBOGOFOffer.applyOffer(basket.products));
        }
        {
            var basket = new Basket(new ArrayList<>());
            basket.products.add(maybellineMascara);
            basket.products.add(maybellineMascara);
            // Check offer is applicable 1 time to basket with 2 Maybelline mascaras.
            assertEquals(1L, maybellineBOGOFOffer.isApplicable(basket));
            // Check correct amount to subtract is returned by applyOffer(basket.products);
            assertEquals(maybellineMascara.price, maybellineBOGOFOffer.applyOffer(basket.products));
        }
        {
            var basket = new Basket(new ArrayList<>());
            basket.products.add(maybellineLipstick);
            basket.products.add(maybellineMascara);
            // Check offer is applicable 1 time to basket with 1 Maybelline lipstick and 1 maybelline mascara.
            assertEquals(1L, maybellineBOGOFOffer.isApplicable(basket));
            // Check correct amount to subtract is returned by applyOffer(basket.products);
            assertEquals(cheapest, maybellineBOGOFOffer.applyOffer(basket.products));
        }
        {
            var basket = new Basket(new ArrayList<>());
            basket.products.add(maybellineLipstick);
            basket.products.add(maybellineLipstick);
            basket.products.add(maybellineLipstick);
            // Check offer is applicable 1 time to basket with 3 Maybelline lipsticks.
            assertEquals(1L, maybellineBOGOFOffer.isApplicable(basket));
            // Check if offer is not implemented for >2 products.
            assertThrows(UnsupportedOperationException.class, () -> maybellineBOGOFOffer.applyOffer(basket.products));
        }
        {
            var basket = new Basket(new ArrayList<>());
            basket.products.add(maybellineLipstick);
            basket.products.add(maybellineLipstick);
            basket.products.add(maybellineLipstick);
            basket.products.add(maybellineLipstick);
            // Check offer is applicable 2 times to basket with 4 Maybelline lipsticks.
            assertEquals(2L, maybellineBOGOFOffer.isApplicable(basket));
            // Check if offer is not implemented for >3 products.
            assertThrows(UnsupportedOperationException.class, () -> maybellineBOGOFOffer.applyOffer(basket.products));
        }
        {
            var basket = new Basket(new ArrayList<>());
            basket.products.add(maybellineLipstick);
            basket.products.add(maybellineLipstick);
            basket.products.add(maybellineLipstick);
            basket.products.add(maybellineLipstick);
            basket.products.add(maybellineLipstick);
            // Check offer is applicable 2 time to basket with 5 Maybelline lipsticks.
            assertEquals(2L, maybellineBOGOFOffer.isApplicable(basket));
            // Check if offer is not implemented for >4 products.
            assertThrows(UnsupportedOperationException.class, () -> maybellineBOGOFOffer.applyOffer(basket.products));
        }
    }
    @Test
    void createMealDealOfferTest() {
        // ToDo: impl!
    }
    @Test
    void createOffersTest() {
        // ToDo: impl!
    }
    @Test
    void createSampleBasketTest() {
        // ToDo: impl!
    }
}
