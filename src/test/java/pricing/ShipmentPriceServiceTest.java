package pricing;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;

import java.util.Optional;

public class ShipmentPriceServiceTest {

    /**
     * Tests the retrieval of prices based on provider and size.
     * Verifies that getPrice returns the correct price for
     * different provider and size combinations.
     */
    @Test
    public void testGetPrice() {
        ShipmentPriceService service = new ShipmentPriceService();
        service.addProviderAndPrices("LP", new ShippingProviderPricing(1.5, 4.0, 6.5));
        service.addProviderAndPrices("MR", new ShippingProviderPricing(2.0, 3.0, 4.0));

        assertEquals(1.5, service.getPrice("LP", "S"), 0.001);
        assertEquals(4.0, service.getPrice("LP", "M"), 0.001);
        assertEquals(6.5, service.getPrice("LP", "L"), 0.001);
        assertEquals(2.0, service.getPrice("MR", "S"), 0.001);
    }

    /**
     * Tests finding the lowest price for each package size across providers.
     * Verifies that:
     * - The lowest price is found correctly for each valid size
     * - An empty Optional is returned for invalid sizes
     */
    @Test
    public void testGetLowestPrice() {
        ShipmentPriceService service = new ShipmentPriceService();
        service.addProviderAndPrices("LP", new ShippingProviderPricing(1.5, 4.0, 6.5));
        service.addProviderAndPrices("MR", new ShippingProviderPricing(2.0, 3.0, 4.0));

        Optional<Double> lowestSmall = service.getLowestPrice("S");
        Optional<Double> lowestMedium = service.getLowestPrice("M");
        Optional<Double> lowestLarge = service.getLowestPrice("L");
        Optional<Double> lowestInvalid = service.getLowestPrice("XL");

        assertTrue(lowestSmall.isPresent());
        assertEquals(1.5, lowestSmall.get(), 0.001);
        assertTrue(lowestMedium.isPresent());
        assertEquals(3.0, lowestMedium.get(), 0.001);
        assertTrue(lowestLarge.isPresent());
        assertEquals(4.0, lowestLarge.get(), 0.001);
        assertFalse(lowestInvalid.isPresent());
    }

    /**
     * Tests the retrieval of provider pricing objects.
     * Verifies that getProviderPrice returns the same pricing
     * object that was added to the service.
     */
    @Test
    public void testGetProviderPrice() {
        ShipmentPriceService service = new ShipmentPriceService();
        ShippingProviderPricing lpPricing = new ShippingProviderPricing(1.5, 4.0, 6.5);
        service.addProviderAndPrices("LP", lpPricing);

        assertEquals(lpPricing, service.getProviderPrice("LP"));
    }
}