package inputAndOutput;

import shipmentModel.BasicShipment;
import shipmentModel.Shipment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for reading shipment data from files and creating shipment objects.
 */
public class FileReader {
    public FileReader() {
    }

    /**
     * Reads a file and extracts each line as a string.
     *
     * @param fileName Path to the file to read
     * @return List of strings, each representing a line from the file
     */
    public List<String> extractEachLine(String fileName) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Creates shipment objects from lines of text.
     * Handles both ShipmentModel.BasicShipment (2 parts) and ShipmentModel.Shipment (3 parts) formats.
     *
     * @param lines List of strings, each representing a shipment
     * @return List of ShipmentModel.BasicShipment objects (including ShipmentModel.Shipment objects)
     */
    public List<BasicShipment> createShipments(List<String> lines) {
        List<BasicShipment> shipments = new ArrayList<>();
        for (String line : lines) {
            String[] lineParts = line.split(" ");
            if (lineParts.length == 3) {
                String date = lineParts[0].trim();
                String size = lineParts[1].trim();
                String provider = lineParts[2].trim();
                Shipment shipment = new Shipment(date, size, provider);
                shipments.add(shipment);
            } else if (lineParts.length == 2) {
                String date = lineParts[0];
                String provider = lineParts[1];
                BasicShipment shipment = new BasicShipment(date, provider);
                shipments.add(shipment);
            }
        }
        return shipments;
    }
}