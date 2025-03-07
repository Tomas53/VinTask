package shipmentModel;

/**
 * Represents a basic shipment with date and shipping provider information.
 * Serves as a base class for more specialized shipment types.
 */
public class BasicShipment {
    private String date;
    private String shippingProvider;

    public BasicShipment(String date, String shippingProvider) {
        this.date = date;
        this.shippingProvider = shippingProvider;
    }

    public String getShippingProvider() {
        return shippingProvider;
    }

    public String getDate() {
        return date;
    }

    public void setShippingProvider(String shippingProvider) {
        this.shippingProvider = shippingProvider;
    }

    public void setDate(String date) {
        this.date = date;
    }
}