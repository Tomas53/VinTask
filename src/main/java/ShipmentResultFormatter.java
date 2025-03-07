/**
 * Formats shipment results with pricing and discount information.
 * Provides string representation for output.
 */
public class ShipmentResultFormatter {
    private final BasicShipment basicShipment;
    private final double originalPrice;
    private final double finalPrice;
    private final double discount;

    /**
     * Creates a new result formatter with shipment and pricing details.
     *
     * @param basicShipment The shipment
     * @param originalPrice Original price before discount
     * @param finalPrice Final price after discount
     * @param discount Discount amount applied
     */
    public ShipmentResultFormatter(BasicShipment basicShipment, double originalPrice, double finalPrice, double discount) {
        this.basicShipment = basicShipment;
        this.originalPrice = originalPrice;
        this.finalPrice = finalPrice;
        this.discount = discount;
    }

    public BasicShipment getBasicShipment() {
        return basicShipment;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public double getDiscount() {
        return discount;
    }

    /**
     * Formats the shipment result as a string.
     * For regular shipments: "date size provider price discount"
     * For invalid shipments: "date provider Ignored"
     *
     * @return Formatted result string
     */
    @Override
    public String toString() {
        if (!(basicShipment instanceof Shipment)) {
            return basicShipment.getDate() + " " + basicShipment.getShippingProvider() + " Ignored";
        }

        Shipment sizedShipment = (Shipment) basicShipment;

        String formattedPrice = String.format("%.2f", finalPrice);

        String formattedDiscount;
        if (discount > 0) {
            formattedDiscount = String.format("%.2f", discount);
        } else {
            formattedDiscount = "-";
        }

        return sizedShipment.getDate() + " " + sizedShipment.getSize() + " " + sizedShipment.getShippingProvider() + " " + formattedPrice + " " + formattedDiscount;
    }
}