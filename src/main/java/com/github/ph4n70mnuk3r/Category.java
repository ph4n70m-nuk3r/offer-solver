package com.github.ph4n70mnuk3r;

import java.util.Objects;

/**
 * Class which represents a Product Category.
 */
public class Category implements Comparable<Category> {
    private final String name;

    public Category(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public int compareTo(Category o) {
        return this.name.compareTo(o.name);
    }
}
