package com.github.ph4n70mnuk3r;

import java.util.List;

public interface OfferApplier {

    /**
     * Function used to apply the offer.
     * @param products the products which the offer is to be applied to.
     * @return the amount to subtract from the basket total.
     */
    Double apply(List<Product> products);
}
