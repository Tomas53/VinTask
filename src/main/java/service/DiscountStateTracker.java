package service;

import java.util.HashMap;
import java.util.Map;

/**
 * Tracks discount state for shipments, including monthly discount totals
 * and rule-specific tracking (like LP Large package counts).
 * Enforces monthly discount limits.
 */
public class DiscountStateTracker {

    private final Map<String, Double> totalMonthDiscounts = new HashMap<>();
    private final Map<String, Integer> lpLargeCounts = new HashMap<>();
    private final Map<String, Boolean> lpLargeFreeShipmentApplied = new HashMap<>();

    private double discountLimit;

    /**
     * Creates a new discount tracker with the specified monthly discount limit.
     *
     * @param discountLimit Maximum discount amount allowed per month
     */
    public DiscountStateTracker(double discountLimit) {
        this.discountLimit = discountLimit;
    }

    /**
     * Gets the total discount applied for a specific month.
     *
     * @param month Month in format YYYY-MM
     * @return Total discount amount for the month
     */
    public double getTotalMonthDiscount(String month) {
        if (totalMonthDiscounts.containsKey(month)) {
            return totalMonthDiscounts.get(month);
        } else {
            return 0.0;
        }
    }

    /**
     * Adds a discount amount to the monthly total.
     *
     * @param month Month in format YYYY-MM
     * @param discount Discount amount to add
     */
    public void addDiscount(String month, double discount) {
        double currentDiscount=getTotalMonthDiscount(month);
        currentDiscount = currentDiscount + discount;
        totalMonthDiscounts.put(month, currentDiscount);
    }

    /**
     * Gets the remaining discount amount available for a month.
     *
     * @param month Month in format YYYY-MM
     * @return Remaining discount amount available
     */
    public double getRemainingDiscount(String month) {
        return discountLimit - getTotalMonthDiscount(month);
    }

    /**
     * Gets the count of large LP shipments for a month.
     * Used for the third-LP-large-free rule.
     *
     * @param month Month in format YYYY-MM
     * @return Count of large LP shipments
     */
    public int getLpLargeCount(String month) {
        if (lpLargeCounts.containsKey(month)) {
            return lpLargeCounts.get(month);
        } else {
            return 0;
        }
    }

    /**
     * Increments the count of large LP shipments for a month.
     *
     * @param month Month in format YYYY-MM
     */
    public void incrementLpLargeCount(String month) {
        int current = getLpLargeCount(month);
        lpLargeCounts.put(month, current + 1);
    }

    /**
     * Checks if the free large LP shipment has been applied for a month.
     *
     * @param month Month in format YYYY-MM
     * @return true if free shipment has been applied, false otherwise
     */
    public boolean isLpLargeFreeShipmentApplied(String month) {
        if (lpLargeFreeShipmentApplied.containsKey(month)) {
            return lpLargeFreeShipmentApplied.get(month);
        } else {
            return false;
        }
    }

    /**
     * Marks that the free large LP shipment has been applied for a month.
     *
     * @param month Month in format YYYY-MM
     */
    public void markLpLargeFreeShipmentApplied(String month) {
        lpLargeFreeShipmentApplied.put(month, true);
    }
}