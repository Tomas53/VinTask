package inputAndOutput;

import static org.junit.jupiter.api.Assertions.*;

import shipmentModel.BasicShipment;
import shipmentModel.Shipment;
import org.junit.Test;

public class ShipmentResultFormatterTest {

    /**
     * Tests that the getter methods return the expected values.
     */
    @Test
    public void testGetters() {
        Shipment shipment = new Shipment("2023-01-15", "S", "LP");
        ShipmentResultFormatter formatter = new ShipmentResultFormatter(shipment, 2.0, 1.5, 0.5);

        assertEquals(shipment, formatter.getBasicShipment());
        assertEquals(2.0, formatter.getOriginalPrice(), 0.001);
        assertEquals(1.5, formatter.getFinalPrice(), 0.001);
        assertEquals(0.5, formatter.getDiscount(), 0.001);
    }

    /**
     * Tests the string formatting of a shipment with a discount.
     * Verifies that date, size, provider, price, and discount
     * are formatted correctly.
     */
    @Test
    public void testToStringWithDiscount() {
        Shipment shipment = new Shipment("2023-01-15", "S", "LP");
        ShipmentResultFormatter formatter = new ShipmentResultFormatter(shipment, 2.0, 1.5, 0.5);

        assertEquals("2023-01-15 S LP 1.50 0.50", formatter.toString());
    }

    /**
     * Tests the string formatting of a shipment without a discount.
     * Verifies that a "-" character is used for the discount field.
     */
    @Test
    public void testToStringWithoutDiscount() {
        Shipment shipment = new Shipment("2023-01-15", "S", "LP");
        ShipmentResultFormatter formatter = new ShipmentResultFormatter(shipment, 2.0, 2.0, 0.0);

        assertEquals("2023-01-15 S LP 2.00 -", formatter.toString());
    }

    /**
     * Tests the string formatting of a basic shipment (without size).
     * Verifies that the "Ignored" text is appended for basic shipments.
     */
    @Test
    public void testToStringWithBasicShipment() {
        BasicShipment shipment = new BasicShipment("2023-01-15", "LP");
        ShipmentResultFormatter formatter = new ShipmentResultFormatter(shipment, 0.0, 0.0, 0.0);

        assertEquals("2023-01-15 LP Ignored", formatter.toString());
    }
}