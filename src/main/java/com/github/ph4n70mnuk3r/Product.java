package com.github.ph4n70mnuk3r;

import java.util.List;
import java.util.Objects;

/**
 * Class which represents a Product.
 * Implements Comparable for displaying products by ID.
 */
public class Product implements Comparable<Product> {
    final Integer id;
    final String name;
    final Double price;
    final List<Category> categories;

    public Product(Integer id, String name, Double price, List<Category> categories) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categories = categories;
    }

    boolean isInCategory(Category category) {
        return categories.stream().anyMatch( c -> c.equals(category) );
    }

    boolean isInCategory(Category ... categoriesToCheck) {
        for (var categoryToCheck : categoriesToCheck) {
            if (this.categories.contains(categoryToCheck)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", categories=" + categories +
                '}';
    }

    @Override
    public int compareTo(Product o) {
        return this.id.compareTo(o.id);
    }
}
