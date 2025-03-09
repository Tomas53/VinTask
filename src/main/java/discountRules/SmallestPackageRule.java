package discountRules;

import service.DiscountContractRule;
import service.DiscountStateTracker;
import service.ShipmentPriceProvider;
import shipmentModel.Shipment;

import java.util.Optional;

/**
 * Discount rule that makes all small package shipments match the lowest
 * price available for small packages across all providers.
 */
public class SmallestPackageRule implements DiscountContractRule {
    private final ShipmentPriceProvider shipmentPriceProvider;

    /**
     * Creates a new rule with the specified price provider.
     *
     * @param shipmentPriceProvider Provider for looking up prices
     */
    public SmallestPackageRule(ShipmentPriceProvider shipmentPriceProvider) {
        this.shipmentPriceProvider = shipmentPriceProvider;
    }

    /**
     * Rule applies only to small packages.
     *
     * @param shipment Shipment to check
     * @return true for small packages, false otherwise
     */
    @Override
    public boolean applies(Shipment shipment) {
        return shipment.getSize().equals("S");
    }

    /**
     * Calculates discount to match the lowest small package price.
     *
     * @param shipment Small package shipment
     * @param originalPrice Original price of the shipment
     * @param discountStateTracker Discount state tracker
     * @return Discount amount (difference between original and lowest price)
     */
    @Override
    public double calculateDiscount(Shipment shipment, double originalPrice, DiscountStateTracker discountStateTracker) {
        Optional<Double> lowestPriceOpt = shipmentPriceProvider.getLowestPrice("S");

        if (lowestPriceOpt.isPresent()) {
            double lowestPrice = lowestPriceOpt.get();
            return Math.max(0, originalPrice - lowestPrice);
        } else {
            return 0.0;
        }
    }
}