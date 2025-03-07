import discount.*;
import inputAndOutput.FileReader;
import pricing.ShipmentPriceService;
import pricing.ShippingProviderPricing;
import service.ShipmentManager;
import shipmentModel.BasicShipment;

import java.util.ArrayList;
import java.util.List;

/**
 * Main application class that sets up the shipping discount system and processes shipments.
 */
public class Main {
    public static void main(String[] args) {
        // Initialize pricing for each provider
        ShippingProviderPricing shippingProviderPricingLp = new ShippingProviderPricing(1.5, 4.9, 6.9);
        ShippingProviderPricing shippingProviderPricingMr = new ShippingProviderPricing(2.0, 3.0, 4.0);

        // Set up price service with provider pricing
        ShipmentPriceService shipmentPriceService = new ShipmentPriceService();
        shipmentPriceService.addProviderAndPrices("LP", shippingProviderPricingLp);
        shipmentPriceService.addProviderAndPrices("MR", shippingProviderPricingMr);

        // Configure discount rules
        List<DiscountContractRule> rules = new ArrayList<>();
        rules.add(new SmallestPackageRule(shipmentPriceService));
        rules.add(new ThirdLargeLPFreeRule());

        // Set monthly discount cap
        double monthlyDiscountCap = 10.0;

        // Create discount tracker
        DiscountStateTracker discountStateTracker = new DiscountStateTracker(monthlyDiscountCap);

        // Create discount calculator with rules
        ShipmentDiscountCalculator shipmentDiscountCalculator = new ShipmentDiscountCalculator(
                shipmentPriceService,
                discountStateTracker,
                rules);

        // Create shipment manager
        ShipmentManager shipmentManager = new ShipmentManager(shipmentDiscountCalculator);

        // Read shipment data from file
        String filePath = "src/main/resources/input.txt";
        FileReader fileReader = new FileReader();
        List<String> lines = fileReader.extractEachLine(filePath);
        List<BasicShipment> shipments = fileReader.createShipments(lines);

        // Process shipments and get results
        List<String> results = shipmentManager.manageShipments(shipments);

        // Output results
        for (String result : results) {
            System.out.println(result);
        }
    }
}