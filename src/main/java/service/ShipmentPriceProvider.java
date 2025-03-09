package service;

import java.util.Optional;

/**
 * Interface for providing shipment prices based on provider and size.
 * Allows for flexible implementation of different pricing strategies.
 */
public interface ShipmentPriceProvider {
    /**
     * Gets the price for a shipment based on provider and size.
     *
     * @param provider Shipping provider code
     * @param size Package size code
     * @return Price for the specified provider and size
     */
    double getPrice(String provider, String size);

    /**
     * Gets the lowest price available for a specific package size
     * across all providers.
     *
     * @param size Package size code
     * @return Optional containing the lowest price, or empty if size is invalid
     */
    Optional<Double> getLowestPrice(String size);
}