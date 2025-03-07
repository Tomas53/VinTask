/**
 * Extends BasicShipment with size information (S, M, L).
 * Represents a standard shipment with all required details.
 */
public class Shipment extends BasicShipment {
    private String size;

    /**
     * Creates a new Shipment with date, size, and provider.
     *
     * @param shippingDate Date of shipment in YYYY-MM-DD format
     * @param size Size of package (S, M, L)
     * @param shippingProvider Provider code (LP, MR)
     */
    public Shipment(String shippingDate, String size, String shippingProvider) {
        super(shippingDate, shippingProvider);
        this.size = size;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}