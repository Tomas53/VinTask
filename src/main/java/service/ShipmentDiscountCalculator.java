package service;

import inputAndOutput.ShipmentResultFormatter;
import shipmentModel.BasicShipment;
import shipmentModel.Shipment;

import java.util.List;

/**
 * Calculates discounts for shipments by applying applicable discount rules
 * and enforcing monthly discount limits.
 */
public class ShipmentDiscountCalculator {
    private final ShipmentPriceProvider shipmentPriceProvider;
    private final DiscountStateTracker discountStateTracker;
    private final List<DiscountContractRule> rules;

    /**
     * Creates a new discount calculator with price provider, state tracker, and rules.
     *
     * @param shipmentPriceProvider Provider of shipment prices
     * @param discountStateTracker Tracker for discount state
     * @param rules List of discount rules to apply
     */
    public ShipmentDiscountCalculator(ShipmentPriceProvider shipmentPriceProvider, DiscountStateTracker discountStateTracker, List<DiscountContractRule> rules) {
        this.shipmentPriceProvider = shipmentPriceProvider;
        this.discountStateTracker = discountStateTracker;
        this.rules = rules;
    }

    /**
     * Calculates discount for a shipment by applying all applicable rules
     * and respecting monthly discount limits.
     *
     * @param basicShipment The shipment to calculate discount for
     * @return Formatted result with original price, final price, and discount
     */
    public ShipmentResultFormatter calculateDiscount(BasicShipment basicShipment) {
        // Only process Shipment objects with size information
        if (!(basicShipment instanceof Shipment)) {
            return new ShipmentResultFormatter(basicShipment, 0, 0, 0);
        }

        Shipment sizedShipment = (Shipment) basicShipment;

        // Get original price based on provider and size
        double originalPrice = shipmentPriceProvider.getPrice(sizedShipment.getShippingProvider(), sizedShipment.getSize());
        if (originalPrice < 0) {
            return new ShipmentResultFormatter(basicShipment, 0, 0, 0);
        }

        // Extract month from date (YYYY-MM)
        String month = sizedShipment.getDate().substring(0, 7);
        double remainingMonthlyDiscount = discountStateTracker.getRemainingDiscount(month);

        // If no discount remaining for the month, return original price
        if (remainingMonthlyDiscount <= 0) {
            return new ShipmentResultFormatter(basicShipment, originalPrice, originalPrice, 0);
        }

        // Apply all applicable discount rules
        double totalDiscount = 0;
        for (DiscountContractRule rule : rules) {
            if (rule.applies(sizedShipment)) {
                double ruleDiscount = rule.calculateDiscount(sizedShipment, originalPrice, discountStateTracker);

                // Enforce monthly discount limit
                if (totalDiscount + ruleDiscount > remainingMonthlyDiscount) {
                    ruleDiscount = remainingMonthlyDiscount - totalDiscount;
                    totalDiscount += ruleDiscount;
                    break;
                } else {
                    totalDiscount += ruleDiscount;
                }
            }
        }

        // Record the discount in the tracker
        discountStateTracker.addDiscount(month, totalDiscount);

        // Calculate final price after discount
        double finalPrice = originalPrice - totalDiscount;

        return new ShipmentResultFormatter(basicShipment, originalPrice, finalPrice, totalDiscount);
    }
}