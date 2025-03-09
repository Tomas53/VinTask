package service;

import org.junit.Test;

import static org.junit.Assert.*;

public class ShippingProviderPricingTest {

    /**
     * Tests that the ShippingProviderPricing constructor correctly initializes
     * prices for different package sizes and that getter methods
     * return the expected values.
     */
    @Test
    public void testConstructorAndGetters() {
        ShippingProviderPricing pricing = new ShippingProviderPricing(1.5, 4.0, 6.5);
        assertEquals(1.5, pricing.getSmall(), 0.001);
        assertEquals(4.0, pricing.getMedium(), 0.001);
        assertEquals(6.5, pricing.getLarge(), 0.001);
    }
}