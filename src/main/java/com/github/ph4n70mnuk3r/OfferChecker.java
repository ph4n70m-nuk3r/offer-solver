package com.github.ph4n70mnuk3r;

interface OfferChecker {
    /**
     * Function used to check the maximum number of times an offer condition is met.
     * @param basket the basket being checked.
     * @return max number of times offer condition is met.
     */
    Long check(Basket basket);
}