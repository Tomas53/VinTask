/**
 * Discount rule that makes the third large LP shipment in a calendar month free.
 * The free shipment is applied only once per month.
 */
public class ThirdLargeLPFreeRule implements DiscountContractRule{
    /**
     * Rule applies only to large LP shipments.
     *
     * @param shipment Shipment to check
     * @return true for large LP shipments, false otherwise
     */
    @Override
    public boolean applies(Shipment shipment) {
        return shipment.getSize().equals("L") && shipment.getShippingProvider().equals("LP");
    }

    /**
     * Calculates discount for the third large LP shipment in a month.
     * Tracks count of large LP shipments and marks when the free shipment is applied.
     *
     * @param shipment Large LP shipment
     * @param originalPrice Original price of the shipment
     * @param discountStateTracker Discount state tracker
     * @return Full price as discount for third shipment, 0 otherwise
     */
    @Override
    public double calculateDiscount(Shipment shipment, double originalPrice, DiscountStateTracker discountStateTracker) {
        String month = shipment.getDate().substring(0, 7);

        discountStateTracker.incrementLpLargeCount(month);

        if (discountStateTracker.getLpLargeCount(month) == 3 && !discountStateTracker.isLpLargeFreeShipmentApplied(month)) {
            discountStateTracker.markLpLargeFreeShipmentApplied(month);

            return originalPrice;
        }

        return 0;
    }
}