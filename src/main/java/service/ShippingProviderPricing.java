package service;

/**
 * Stores pricing information for a shipping provider based on package size.
 */
public class ShippingProviderPricing {
    private double small;
    private double medium;
    private double large;

    /**
     * Creates a new pricing structure for a shipping provider.
     *
     * @param small Price for small packages
     * @param medium Price for medium packages
     * @param large Price for large packages
     */
    public ShippingProviderPricing(double small, double medium, double large) {
        this.small = small;
        this.medium = medium;
        this.large = large;
    }

    public double getSmall() {
        return small;
    }

    public double getMedium() {
        return medium;
    }

    public double getLarge() {
        return large;
    }

    public void setSmall(double small) {
        this.small = small;
    }

    public void setMedium(double medium) {
        this.medium = medium;
    }

    public void setLarge(double large) {
        this.large = large;
    }
}