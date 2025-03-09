package shipmentModel;

import org.junit.Test;
import static org.junit.Assert.*;

public class BasicShipmentTest {

    /**
     * Tests that the BasicShipment constructor correctly initializes properties
     * and that getter methods return the expected values.
     */
    @Test
    public void testConstructorAndGetters() {
        BasicShipment shipment = new BasicShipment("2023-01-15", "LP");
        assertEquals("2023-01-15", shipment.getDate());
        assertEquals("LP", shipment.getShippingProvider());
    }
}