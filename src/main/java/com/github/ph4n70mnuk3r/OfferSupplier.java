package com.github.ph4n70mnuk3r;

import java.util.List;

public interface OfferSupplier {

    /**
     * Function used to supply products from the basket to the offerApplier.
     * @param basket the basket to choose products from.
     * @return the products to be supplied to the offerApplier.
     */
    List<Product> supply(Basket basket);
}
