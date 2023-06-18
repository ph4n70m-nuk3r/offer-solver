package com.github.ph4n70mnuk3r;

import java.util.List;

/**
 * Class which represents a customer's basket.
 */
public class Basket {
    final List<Product> products;

    public Basket(List<Product> products) {
        this.products = products;
    }
}