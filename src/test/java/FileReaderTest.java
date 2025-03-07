import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;
import org.junit.Before;
import java.util.Arrays;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class FileReaderTest {

    private FileReader fileReader;
    private final String TEST_FILE = "src/test/resources/test_input.txt";

    /**
     * Sets up the test environment before each test.
     * Creates a test file with sample shipping data.
     */
    @Before
    public void setUp() throws IOException {
        fileReader = new FileReader();
        List<String> lines = Arrays.asList(
                "2023-01-15 S LP",
                "2023-01-16 M MR",
                "2023-01-17 LP"
        );
        Files.write(Paths.get(TEST_FILE), lines);
    }

    /**
     * Tests that extractEachLine method correctly reads all lines from a file.
     */
    @Test
    public void testExtractEachLine() {
        List<String> lines = fileReader.extractEachLine(TEST_FILE);
        assertEquals(3, lines.size());
        assertEquals("2023-01-15 S LP", lines.get(0));
    }

    /**
     * Tests that createShipments method correctly parses different line formats
     * and creates appropriate shipment objects.
     * Verifies:
     * - Lines with 3 parts create Shipment objects
     * - Lines with 2 parts create BasicShipment objects
     * - Properties are correctly set in the created objects
     */
    @Test
    public void testCreateShipments() {
        List<String> lines = Arrays.asList("2023-01-15 S LP", "2023-01-16 MR");
        List<BasicShipment> shipments = fileReader.createShipments(lines);

        assertEquals(2, shipments.size());
        assertTrue(shipments.get(0) instanceof Shipment);
        assertFalse(shipments.get(1) instanceof Shipment);

        Shipment shipment = (Shipment) shipments.get(0);
        assertEquals("2023-01-15", shipment.getDate());
        assertEquals("S", shipment.getSize());
        assertEquals("LP", shipment.getShippingProvider());
    }
}