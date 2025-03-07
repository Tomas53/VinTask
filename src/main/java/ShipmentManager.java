import java.util.ArrayList;
import java.util.List;

/**
 * Manages the processing of shipments through the discount calculator.
 * Collects and returns formatted results.
 */
public class ShipmentManager {
    private final ShipmentDiscountCalculator shipmentDiscountCalculator;

    /**
     * Creates a new shipment manager with the specified discount calculator.
     *
     * @param shipmentDiscountCalculator Calculator for shipment discounts
     */
    public ShipmentManager(ShipmentDiscountCalculator shipmentDiscountCalculator) {
        this.shipmentDiscountCalculator = shipmentDiscountCalculator;
    }

    /**
     * Processes a list of shipments through the discount calculator
     * and returns formatted results.
     *
     * @param shipments List of shipments to process
     * @return List of formatted result strings
     */
    public List<String> manageShipments(List<BasicShipment> shipments) {
        List<String> results = new ArrayList<>();

        for (BasicShipment shipment : shipments) {
            ShipmentResultFormatter result = shipmentDiscountCalculator.calculateDiscount(shipment);
            results.add(result.toString());
        }

        return results;
    }
}