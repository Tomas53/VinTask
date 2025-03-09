package service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Service for managing shipping provider prices and retrieving
 * prices based on provider and package size.
 * Implements ShipmentPriceProvider interface.
 */
public class ShipmentPriceService implements ShipmentPriceProvider {
    private final Map<String, ShippingProviderPricing> providerPrices = new HashMap<>();

    public ShipmentPriceService() {
    }

    /**
     * Adds a provider and its pricing structure to the service.
     *
     * @param provider Provider code
     * @param shippingProviderPricing Pricing structure for the provider
     */
    public void addProviderAndPrices(String provider, ShippingProviderPricing shippingProviderPricing) {
        providerPrices.put(provider, shippingProviderPricing);
    }

    /**
     * Gets the pricing structure for a specific provider.
     *
     * @param provider Provider code
     * @return Pricing structure for the provider
     */
    public ShippingProviderPricing getProviderPrice(String provider) {
        return providerPrices.get(provider);
    }

    /**
     * Gets the price for a specific provider and package size.
     *
     * @param provider Provider code
     * @param size Package size code
     * @return Price for the specified provider and size, or -1 for invalid size/provider
     */
    @Override
    public double getPrice(String provider, String size) {
        ShippingProviderPricing shippingProviderPricing = providerPrices.get(provider);
        if (shippingProviderPricing == null) {
            return -1; // Invalid provider
        }

        switch (size) {
            case "L": return shippingProviderPricing.getLarge();
            case "M": return shippingProviderPricing.getMedium();
            case "S": return shippingProviderPricing.getSmall();
            default: return -1; // Invalid size
        }
    }

    /**
     * Gets the lowest price available for a specific package size
     * across all providers.
     *
     * @param size Package size code
     * @return Optional containing the lowest price, or empty if size is invalid
     */
    @Override
    public Optional<Double> getLowestPrice(String size) {
        if (!isValidSize(size)) {
            return Optional.empty();
        }

        Double lowestPrice = null;

        for (ShippingProviderPricing shippingProviderPricing : providerPrices.values()) {
            double priceForSize;

            switch (size) {
                case "S":
                    priceForSize = shippingProviderPricing.getSmall();
                    break;
                case "M":
                    priceForSize = shippingProviderPricing.getMedium();
                    break;
                case "L":
                    priceForSize = shippingProviderPricing.getLarge();
                    break;
                default:
                    continue;
            }

            if (lowestPrice == null || priceForSize < lowestPrice) {
                lowestPrice = priceForSize;
            }
        }

        return Optional.ofNullable(lowestPrice);
    }

    /**
     * Checks if a size code is valid.
     *
     * @param size Size code to check
     * @return true if size is valid, false otherwise
     */
    private boolean isValidSize(String size) {
        return "S".equals(size) || "M".equals(size) || "L".equals(size);
    }
}