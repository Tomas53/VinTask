package service;

import static org.junit.jupiter.api.Assertions.*;

import discount.ShipmentDiscountCalculator;
import inputAndOutput.ShipmentResultFormatter;
import shipmentModel.BasicShipment;
import shipmentModel.Shipment;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class ShipmentManagerTest {

    /**
     * Tests the management of multiple shipments.
     * Verifies that the manager:
     * - Processes each shipment through the discount calculator
     * - Returns a list of formatted results with the expected size
     * - Returns results with the expected format
     */
    @Test
    public void testManageShipments() {
        MockDiscountCalculator calculator = new MockDiscountCalculator();
        ShipmentManager manager = new ShipmentManager(calculator);

        List<BasicShipment> shipments = new ArrayList<>();
        shipments.add(new Shipment("2023-01-15", "S", "LP"));
        shipments.add(new BasicShipment("2023-01-16", "MR"));

        List<String> results = manager.manageShipments(shipments);

        assertEquals(2, results.size());
        assertEquals("Mock Result", results.get(0));
        assertEquals("Mock Result", results.get(1));
    }

    /**
     * Mock implementation of discount.ShipmentDiscountCalculator for testing.
     * Always returns a MockResultFormatter object.
     */
    private static class MockDiscountCalculator extends ShipmentDiscountCalculator {
        public MockDiscountCalculator() {
            super(null, null, null);
        }

        @Override
        public ShipmentResultFormatter calculateDiscount(BasicShipment basicShipment) {
            return new MockResultFormatter();
        }
    }

    /**
     * Mock implementation of inputAndOutput.ShipmentResultFormatter for testing.
     * Always returns "Mock Result" from toString().
     */
    private static class MockResultFormatter extends ShipmentResultFormatter {
        public MockResultFormatter() {
            super(null, 0, 0, 0);
        }

        @Override
        public String toString() {
            return "Mock Result";
        }
    }
}