package com.github.ph4n70mnuk3r;

import com.github.ph4n70mnuk3r.enums.CATEGORY_NAMES;
import com.github.ph4n70mnuk3r.enums.OFFER_NAMES;
import com.github.ph4n70mnuk3r.enums.PRODUCT_NAMES;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import static com.github.ph4n70mnuk3r.enums.OFFER_NAMES.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class.
 */
public class SampleUsageTest {
    private final TestUtils testUtils = new TestUtils();

    @Test
    void categoryMapTest() {
        System.out.println(">>> CATEGORIES <<<");
        testUtils.categoryMap.values().stream().sorted().forEach(System.out::println);
        // Check expected number of categories exist.
        assertTrue(CATEGORY_NAMES.values().length > 0);
        assertEquals(CATEGORY_NAMES.values().length, testUtils.categoryMap.size());
        // Check all enum values exist in categoryMap.
        for (var categoryName : CATEGORY_NAMES.values()) {
            assertTrue(testUtils.categoryMap.containsKey(categoryName.label));
            assertTrue(testUtils.categoryMap.containsValue(new Category(categoryName.label)));
        }
    }
    @Test
    void productMapTest() {
        System.out.println(">>> PRODUCTS <<<");
        testUtils.productMap.values().stream().sorted().forEach(System.out::println);
        // Check expected number of products exist.
        assertTrue(testUtils.productMap.size() > 0);
        assertEquals(PRODUCT_NAMES.values().length, testUtils.productMap.size());
        // Check all enum values exist in productMap.
        for (var productName : PRODUCT_NAMES.values()) {
            assertTrue(testUtils.productMap.containsKey(productName.label));
            assertEquals(productName.label, testUtils.productMap.get(productName.label).name);
        }
    }
    @Test
    void offerMapTest() {
        System.out.println(">>> BASKET <<<");
        testUtils.offerMap.values().stream().sorted().forEach(System.out::println);
        // Check expected number of offers exist.
        assertTrue(testUtils.offerMap.size() > 0);
        assertEquals(OFFER_NAMES.values().length, testUtils.offerMap.size());
        // Check all enum values exist in offerMap.
        for (var offerName : OFFER_NAMES.values()) {
            assertTrue(testUtils.offerMap.containsKey(offerName.label));
            assertEquals(offerName.label, testUtils.offerMap.get(offerName.label).name);
        }
    }
    @Test
    void sampleBasketTest() {
        System.out.println(">>> BASKET <<<");
        testUtils.basket.products.stream().sorted().forEach(System.out::println);
        // Check expected number of products exist in basket.
        assertTrue(testUtils.basket.products.size() > 0);
        var totalUniqueProducts = testUtils.productMap.size();
        var totalUniqueProductsInBasket = testUtils.basket.products.stream()
                .distinct().toList().size();
        var totalProductsInBasket = testUtils.basket.products.size();
        // Ensure all products are in basket at least once.
        assertEquals(totalUniqueProducts, totalUniqueProductsInBasket);
        // Ensure at least one product is in basket more than once.
        assertTrue(totalProductsInBasket > totalUniqueProducts);
    }
    @Test
    void maybellineBOGOFOfferTest() {
        var maybellineBOGOFOffer = testUtils.offerMap.get(MAYBELLINE_BOGOF_OFFER.label);
        // Check offer name is as expected.
        assertEquals(MAYBELLINE_BOGOF_OFFER.label, maybellineBOGOFOffer.name);

        // Maybelline products.
        var maybellineLipstick = testUtils.productMap.get(PRODUCT_NAMES.MAYBELLINE_LIPSTICK.label);
        var maybellineMascara = testUtils.productMap.get(PRODUCT_NAMES.MAYBELLINE_MASCARA.label);

        // Cheapest product.
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
    void mealDealOfferTest() {
        var mealDealOffer = testUtils.offerMap.get(MEAL_DEAL_OFFER.label);
        // Check offer name is as expected.
        assertEquals(MEAL_DEAL_OFFER.label, mealDealOffer.name);

        // Mains
        var hamSandwich = testUtils.productMap.get(PRODUCT_NAMES.HAM_SANDWICH.label);
        var bltSandwich = testUtils.productMap.get(PRODUCT_NAMES.BLT_SANDWICH.label);
        var pastaSalad = testUtils.productMap.get(PRODUCT_NAMES.PASTA_SALAD.label);
        // Snacks
        var fridgeRaiders = testUtils.productMap.get(PRODUCT_NAMES.FRIDGE_RAIDERS_PLAIN.label);
        var porkPies = testUtils.productMap.get(PRODUCT_NAMES.PORK_PIES_2PK.label);
        // Drinks
        var cocaCola = testUtils.productMap.get(PRODUCT_NAMES.COCA_COLA_500ML.label);
        var water250ml = testUtils.productMap.get(PRODUCT_NAMES.WATER_250ML.label);
        var fruitSmoothie = testUtils.productMap.get(PRODUCT_NAMES.FRUIT_SMOOTHIE.label);
        {
            var basket = new Basket(new ArrayList<>());
            // Check offer is applicable 0 times to an empty basket.
            assertEquals(0L, mealDealOffer.isApplicable(basket));
            // Check offer throws if 0 items in basket.
            assertThrows(InvalidParameterException.class, () -> mealDealOffer.applyOffer(basket.products));
            // Check offer throws if <3 items in basket.
            basket.products.add(bltSandwich);
            assertThrows(InvalidParameterException.class, () -> mealDealOffer.applyOffer(basket.products));
            basket.products.add(fridgeRaiders);
            assertThrows(InvalidParameterException.class, () -> mealDealOffer.applyOffer(basket.products));
            // Check offer is applicable 1 time to basket with item from each meal deal category
            basket.products.add(cocaCola);
            assertEquals(1L, mealDealOffer.isApplicable(basket));
            // Check correct amount to subtract is returned by applyOffer(basket.products);
            var subTotal = basket.products.stream().mapToDouble(product -> product.price).sum();
            var maxPrice = 3D;
            // If subtotal >= maxPrice return the difference (to be subtracted).
            var expected = subTotal >= maxPrice ? subTotal - maxPrice : 0L;
            assertEquals(expected, mealDealOffer.applyOffer(basket.products));
        }
        {
            var basket = new Basket(new ArrayList<>());
            basket.products.add(hamSandwich);
            basket.products.add(porkPies);
            basket.products.add(water250ml);
            // Check basket total is less than 3.00 and that no discount is applied.
            var total = basket.products.stream().mapToDouble(product -> product.price).sum();
            assertTrue(total < 3D);
            assertEquals(0, mealDealOffer.applyOffer(basket.products));
        }
        {
            var basket = new Basket(new ArrayList<>());
            basket.products.add(hamSandwich);
            basket.products.add(fridgeRaiders);
            basket.products.add(water250ml);
            basket.products.add(pastaSalad);
            // Check offer throws for case where more than 3 products are concerned.
            assertThrows(UnsupportedOperationException.class, () -> mealDealOffer.applyOffer(basket.products));
            basket.products.add(porkPies);
            basket.products.add(fruitSmoothie);
            // Check basket is eligible for 2 meal deal offers.
            assertEquals(2L, mealDealOffer.offerChecker.check(basket));
        }
    }
}
