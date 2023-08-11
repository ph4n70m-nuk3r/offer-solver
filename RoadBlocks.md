RoadBlocks
==========
- Not sure that it makes sense to isolate logic for checking best <OFFER_NAME> offer.
  - I feel like it will function like:
    - OfferChecker will set a bound on numbers of possible offers.
    - ProductSupplier will choose which products to supply to OfferApplier.
    - OfferApplier will return potential savings for each offer.
  - From there we still need logic to choose which offers to use.
  - I guess that's where the hard part starts.
