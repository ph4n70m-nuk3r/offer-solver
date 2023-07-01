package com.github.ph4n70mnuk3r;

import java.util.List;
import java.util.Objects;

/**
 * Class which represents an offer.
 * Implements Comparable for sorting purposes.
 * Note: Equals/Hashcode/CompareTo only considers 'name' field which is intended to be unique.
 */
public class Offer implements Comparable<Offer> {
    final String name;
    final OfferChecker offerChecker;
    final OfferApplier offerApplier;

    public Offer(String name, OfferChecker offerChecker, OfferApplier offerApplier) {
        this.name = name;
        this.offerChecker = offerChecker;
        this.offerApplier = offerApplier;
    }

    /**
     * Function which checks the maximum number of times the offer may be applicable.
     * @param basket the basket to check.
     * @return the maximum number of times this offer could be applied to the basket.
     */
    Long isApplicable(Basket basket) {
        return offerChecker.check(basket);
    }

    /**
     * Function which applies a given offer.
     * @param products the products to apply the offer to.
     * @return the amount to subtract from the basket total.
     */
    Double applyOffer(List<Product> products) {
        return offerApplier.apply(products);
    }

    @Override
    public String toString() {
        return "Offer{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return Objects.equals(name, offer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public int compareTo(Offer o) {
        return this.name.compareTo(o.name);
    }
}
