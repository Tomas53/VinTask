package shipmentModel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

public class ShipmentTest {

    /**
     * Tests that the ShipmentModel.Shipment constructor correctly initializes properties
     * and that getter methods return the expected values.
     * Verifies properties inherited from ShipmentModel.BasicShipment as well as
     * the size property specific to ShipmentModel.Shipment.
     */
    @Test
    public void testConstructorAndGetters() {
        Shipment shipment = new Shipment("2023-01-15", "S", "LP");
        assertEquals("2023-01-15", shipment.getDate());
        assertEquals("LP", shipment.getShippingProvider());
        assertEquals("S", shipment.getSize());
    }
}