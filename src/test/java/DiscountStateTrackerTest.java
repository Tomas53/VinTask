import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;

public class DiscountStateTrackerTest {

    /**
     * Tests the tracking of monthly discount totals and remaining discount calculation.
     * Verifies that:
     * - Initial discount total is zero
     * - Initial remaining discount equals the discount limit
     * - Adding a discount updates the monthly total correctly
     * - Remaining discount is calculated correctly after adding a discount
     */
    @Test
    public void testDiscountTracking() {
        DiscountStateTracker tracker = new DiscountStateTracker(10.0);
        assertEquals(0.0, tracker.getTotalMonthDiscount("2023-01"), 0.001);
        assertEquals(10.0, tracker.getRemainingDiscount("2023-01"), 0.001);

        tracker.addDiscount("2023-01", 4.5);
        assertEquals(4.5, tracker.getTotalMonthDiscount("2023-01"), 0.001);
        assertEquals(5.5, tracker.getRemainingDiscount("2023-01"), 0.001);
    }

    /**
     * Tests the tracking of large LP shipments and the free shipment flag.
     * Verifies that:
     * - Initial count of large LP shipments is zero
     * - Initial free shipment applied flag is false
     * - Incrementing the count works correctly
     * - Marking free shipment as applied updates the flag correctly
     */
    @Test
    public void testLpLargeTracking() {
        DiscountStateTracker tracker = new DiscountStateTracker(10.0);
        assertEquals(0, tracker.getLpLargeCount("2023-01"));
        assertFalse(tracker.isLpLargeFreeShipmentApplied("2023-01"));

        tracker.incrementLpLargeCount("2023-01");
        tracker.incrementLpLargeCount("2023-01");
        assertEquals(2, tracker.getLpLargeCount("2023-01"));

        tracker.markLpLargeFreeShipmentApplied("2023-01");
        assertTrue(tracker.isLpLargeFreeShipmentApplied("2023-01"));
    }
}