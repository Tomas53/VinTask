package service;

import static org.junit.jupiter.api.Assertions.*;

import discountRules.SmallestPackageRule;
import discountRules.ThirdLargeLPFreeRule;
import org.junit.Test;
import service.*;
import shipmentModel.BasicShipment;
import shipmentModel.Shipment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Integration test for the end-to-end shipment discount processing.
 * Tests the complete flow from shipment creation through discount calculation to result formatting.
 */
public class ShipmentProcessingIntegrationTest {

    /**
     * Tests the complete processing flow with multiple shipments across different dates,
     * verifying that discount rules are correctly applied and limits are enforced.
     */
    @Test
    public void testCompleteShipmentProcessing() {
        // Set up the pricing structure
        ShippingProviderPricing lpPricing = new ShippingProviderPricing(1.5, 4.9, 6.9);
        ShippingProviderPricing mrPricing = new ShippingProviderPricing(2.0, 3.0, 4.0);

        ShipmentPriceService priceService = new ShipmentPriceService();
        priceService.addProviderAndPrices("LP", lpPricing);
        priceService.addProviderAndPrices("MR", mrPricing);

        // Set up discount rules
        List<DiscountContractRule> rules = new ArrayList<>();
        rules.add(new SmallestPackageRule(priceService));
        rules.add(new ThirdLargeLPFreeRule());

        // Set up discount tracker with a limit of 10.0
        DiscountStateTracker tracker = new DiscountStateTracker(10.0);

        // Create discount calculator
        ShipmentDiscountCalculator calculator = new ShipmentDiscountCalculator(
                priceService, tracker, rules);

        // Create shipment manager
        ShipmentManager manager = new ShipmentManager(calculator);

        // Create test shipments simulating a month's worth of activity
        List<BasicShipment> shipments = Arrays.asList(
                // Small package MR (should be discounted to match LP price)
                new Shipment("2023-01-01", "S", "MR"),

                // First 2 large LP packages (no discount)
                new Shipment("2023-01-02", "L", "LP"),
                new Shipment("2023-01-03", "L", "LP"),

                // Third large LP package (should be free)
                new Shipment("2023-01-04", "L", "LP"),

                // More small packages (should be discounted)
                new Shipment("2023-01-05", "S", "MR"),
                new Shipment("2023-01-06", "S", "MR"),

                // Fourth large LP (should NOT be free, rule applies once per month)
                new Shipment("2023-01-07", "L", "LP"),

                // Basic shipment (should be ignored)
                new BasicShipment("2023-01-08", "LP"),

                // New month shipments
                new Shipment("2023-02-01", "S", "MR"),
                new Shipment("2023-02-02", "L", "LP"),
                new Shipment("2023-02-03", "L", "LP"),
                new Shipment("2023-02-04", "L", "LP")  // Third LP large in Feb, should be free
        );

        // Process shipments
        List<String> results = manager.manageShipments(shipments);

        // Verify results
        assertEquals(12, results.size());

        // Check for specific expected outcomes

        // Small package MR discounted to match LP price
        assertTrue(results.get(0).contains("1.50 0.50"));

        // First 2 large LP packages - no discount
        assertTrue(results.get(1).contains("6.90 -"));
        assertTrue(results.get(2).contains("6.90 -"));

        // Third large LP package - should be free
        assertTrue(results.get(3).contains("0.00 6.90"));

        // More small MR packages - discounted
        assertTrue(results.get(4).contains("1.50 0.50"));
        assertTrue(results.get(5).contains("1.50 0.50"));

        // Fourth large LP - should not be free
        assertTrue(results.get(6).contains("6.90 -"));

        // Basic shipment - should be ignored
        assertTrue(results.get(7).contains("Ignored"));

        // Feb small MR - discounted
        assertTrue(results.get(8).contains("1.50 0.50"));

        // Feb large LPs - first two no discount, third free
        assertTrue(results.get(9).contains("6.90 -"));
        assertTrue(results.get(10).contains("6.90 -"));
        assertTrue(results.get(11).contains("0.00 6.90"));

        // February should have its own discount cap
        assertTrue(tracker.getTotalMonthDiscount("2023-02") < 10.0);
        assertTrue(tracker.getRemainingDiscount("2023-02") > 0.0);
    }
}