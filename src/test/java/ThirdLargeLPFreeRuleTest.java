import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;

public class ThirdLargeLPFreeRuleTest {

    /**
     * Tests the applies method of the rule.
     * Verifies that the rule applies only to large LP shipments
     * and not to other size/provider combinations.
     */
    @Test
    public void testApplies() {
        ThirdLargeLPFreeRule rule = new ThirdLargeLPFreeRule();

        Shipment lpLargeShipment = new Shipment("2023-01-15", "L", "LP");
        Shipment lpMediumShipment = new Shipment("2023-01-15", "M", "LP");
        Shipment mrLargeShipment = new Shipment("2023-01-15", "L", "MR");

        assertTrue(rule.applies(lpLargeShipment));
        assertFalse(rule.applies(lpMediumShipment));
        assertFalse(rule.applies(mrLargeShipment));
    }

    /**
     * Tests the discount calculation of the rule.
     * Verifies that:
     * - No discount is applied to the first and second large LP shipments
     * - Full discount is applied to the third large LP shipment
     * - No discount is applied to subsequent shipments after the third
     *   (free shipment can only be applied once per month)
     */
    @Test
    public void testCalculateDiscount() {
        ThirdLargeLPFreeRule rule = new ThirdLargeLPFreeRule();
        DiscountStateTracker tracker = new DiscountStateTracker(10.0);

        Shipment lpLargeShipment1 = new Shipment("2023-01-15", "L", "LP");
        Shipment lpLargeShipment2 = new Shipment("2023-01-16", "L", "LP");
        Shipment lpLargeShipment3 = new Shipment("2023-01-17", "L", "LP");

        // First shipment - no discount
        assertEquals(0.0, rule.calculateDiscount(lpLargeShipment1, 6.5, tracker), 0.001);

        // Second shipment - no discount
        assertEquals(0.0, rule.calculateDiscount(lpLargeShipment2, 6.5, tracker), 0.001);

        // Third shipment - full discount
        assertEquals(6.5, rule.calculateDiscount(lpLargeShipment3, 6.5, tracker), 0.001);

        // Fourth shipment - no discount (already applied this month)
        Shipment lpLargeShipment4 = new Shipment("2023-01-18", "L", "LP");
        assertEquals(0.0, rule.calculateDiscount(lpLargeShipment4, 6.5, tracker), 0.001);
    }
}