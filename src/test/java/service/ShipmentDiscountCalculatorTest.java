package service;

import static org.junit.jupiter.api.Assertions.*;

import inputAndOutput.ShipmentResultFormatter;
import shipmentModel.BasicShipment;
import shipmentModel.Shipment;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShipmentDiscountCalculatorTest {

    /**
     * Tests the calculation of discount for a sized shipment with a single discount rule.
     * Verifies that original price, final price, and discount are calculated correctly.
     */
    @Test
    public void testCalculateDiscountForShipment() {
        MockPriceProvider priceProvider = new MockPriceProvider();
        DiscountStateTracker tracker = new DiscountStateTracker(10.0);
        List<DiscountContractRule> rules = new ArrayList<>();
        rules.add(new MockDiscountRule(true, 1.0));

        ShipmentDiscountCalculator calculator = new ShipmentDiscountCalculator(priceProvider, tracker, rules);

        Shipment sizedShipment = new Shipment("2023-01-15", "S", "LP");
        ShipmentResultFormatter result = calculator.calculateDiscount(sizedShipment);

        assertEquals(5.0, result.getOriginalPrice(), 0.001);
        assertEquals(4.0, result.getFinalPrice(), 0.001);
        assertEquals(1.0, result.getDiscount(), 0.001);
    }

    /**
     * Tests the handling of a basic shipment (without size information).
     * Verifies that the calculator returns zero values for price and discount.
     */
    @Test
    public void testCalculateDiscountForBasicShipment() {
        MockPriceProvider priceProvider = new MockPriceProvider();
        DiscountStateTracker tracker = new DiscountStateTracker(10.0);
        List<DiscountContractRule> rules = new ArrayList<>();

        ShipmentDiscountCalculator calculator = new ShipmentDiscountCalculator(priceProvider, tracker, rules);

        BasicShipment basicShipment = new BasicShipment("2023-01-15", "LP");
        ShipmentResultFormatter result = calculator.calculateDiscount(basicShipment);

        assertEquals(0.0, result.getOriginalPrice(), 0.001);
        assertEquals(0.0, result.getFinalPrice(), 0.001);
        assertEquals(0.0, result.getDiscount(), 0.001);
    }

    /**
     * Tests the calculation of discount with multiple applicable rules.
     * Verifies that discounts from multiple rules are accumulated correctly.
     */
    @Test
    public void testMultipleDiscountRules() {
        MockPriceProvider priceProvider = new MockPriceProvider();
        DiscountStateTracker tracker = new DiscountStateTracker(10.0);
        List<DiscountContractRule> rules = new ArrayList<>();
        rules.add(new MockDiscountRule(true, 1.0));
        rules.add(new MockDiscountRule(true, 2.0));

        ShipmentDiscountCalculator calculator = new ShipmentDiscountCalculator(priceProvider, tracker, rules);

        Shipment sizedShipment = new Shipment("2023-01-15", "S", "LP");
        ShipmentResultFormatter result = calculator.calculateDiscount(sizedShipment);

        assertEquals(5.0, result.getOriginalPrice(), 0.001);
        assertEquals(2.0, result.getFinalPrice(), 0.001);
        assertEquals(3.0, result.getDiscount(), 0.001);
    }

    /**
     * Tests the enforcement of monthly discount limits.
     * Verifies that total discount is capped at the monthly limit,
     * even when rule discounts would exceed it.
     */
    @Test
    public void testDiscountLimitEnforcement() {
        MockPriceProvider priceProvider = new MockPriceProvider();
        DiscountStateTracker tracker = new DiscountStateTracker(2.0);
        List<DiscountContractRule> rules = new ArrayList<>();
        rules.add(new MockDiscountRule(true, 3.0));

        ShipmentDiscountCalculator calculator = new ShipmentDiscountCalculator(priceProvider, tracker, rules);

        Shipment sizedShipment = new Shipment("2023-01-15", "S", "LP");
        ShipmentResultFormatter result = calculator.calculateDiscount(sizedShipment);

        assertEquals(5.0, result.getOriginalPrice(), 0.001);
        assertEquals(3.0, result.getFinalPrice(), 0.001);
        assertEquals(2.0, result.getDiscount(), 0.001); // Limited to 2.0
    }

    /**
     * Mock implementation of ShipmentPriceProvider for testing.
     * Returns fixed values for prices.
     */
    private static class MockPriceProvider implements ShipmentPriceProvider {
        @Override
        public double getPrice(String provider, String size) {
            return 5.0;
        }

        @Override
        public Optional<Double> getLowestPrice(String size) {
            return Optional.of(3.0);
        }
    }

    /**
     * Mock implementation of DiscountContractRule for testing.
     * Can be configured to apply or not apply, and returns a fixed discount.
     */
    private static class MockDiscountRule implements DiscountContractRule {
        private final boolean applies;
        private final double discount;

        public MockDiscountRule(boolean applies, double discount) {
            this.applies = applies;
            this.discount = discount;
        }

        @Override
        public boolean applies(Shipment shipment) {
            return applies;
        }

        @Override
        public double calculateDiscount(Shipment shipment, double originalPrice, DiscountStateTracker discountStateTracker) {
            return discount;
        }
    }
}