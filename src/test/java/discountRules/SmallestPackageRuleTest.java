package discountRules;

import static org.junit.jupiter.api.Assertions.*;

import service.DiscountStateTracker;
import service.ShipmentPriceProvider;
import shipmentModel.Shipment;
import org.junit.Test;
import java.util.Optional;

public class SmallestPackageRuleTest {

    /**
     * Tests the applies method of the rule.
     * Verifies that the rule applies only to small packages
     * and not to other sizes.
     */
    @Test
    public void testApplies() {
        ShipmentPriceProvider provider = new MockPriceProvider();
        SmallestPackageRule rule = new SmallestPackageRule(provider);

        Shipment smallShipment = new Shipment("2023-01-15", "S", "LP");
        Shipment mediumShipment = new Shipment("2023-01-15", "M", "LP");

        assertTrue(rule.applies(smallShipment));
        assertFalse(rule.applies(mediumShipment));
    }

    /**
     * Tests the discount calculation of the rule.
     * Verifies that:
     * - Discount equals the difference between original price and lowest price
     * - No discount is applied when original price is already the lowest
     */
    @Test
    public void testCalculateDiscount() {
        ShipmentPriceProvider provider = new MockPriceProvider();
        SmallestPackageRule rule = new SmallestPackageRule(provider);
        DiscountStateTracker tracker = new DiscountStateTracker(10.0);

        Shipment smallShipment = new Shipment("2023-01-15", "S", "LP");

        // Original price is 2.0, lowest price is 1.5
        double discount = rule.calculateDiscount(smallShipment, 2.0, tracker);
        assertEquals(0.5, discount, 0.001);

        // When original price is already the lowest
        double noDiscount = rule.calculateDiscount(smallShipment, 1.5, tracker);
        assertEquals(0.0, noDiscount, 0.001);
    }

    /**
     * Mock implementation of service.ShipmentPriceProvider for testing.
     */
    private static class MockPriceProvider implements ShipmentPriceProvider {
        @Override
        public double getPrice(String provider, String size) {
            return size.equals("S") ? 1.5 : (size.equals("M") ? 3.0 : 4.0);
        }

        @Override
        public Optional<Double> getLowestPrice(String size) {
            return Optional.of(1.5);
        }
    }
}