package discount;

import shipmentModel.Shipment;

/**
 * Interface defining contract rules for calculating shipment discounts.
 * Allows for flexible addition and modification of discount rules.
 */
public interface DiscountContractRule {
    /**
     * Determines if this rule applies to a given shipment.
     *
     * @param shipment The shipment to check
     * @return true if the rule applies, false otherwise
     */
    boolean applies(Shipment shipment);

    /**
     * Calculates the discount amount for a shipment when this rule applies.
     *
     * @param shipment The shipment to calculate discount for
     * @param originalPrice The original price of the shipment
     * @param discountStateTracker Tracker for discount state (e.g., monthly limits)
     * @return The calculated discount amount
     */
    double calculateDiscount(Shipment shipment, double originalPrice, DiscountStateTracker discountStateTracker);
}