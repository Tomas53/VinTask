```markdown
# VinTask - Shipment Discount Calculator

## Overview
VinTask is a Java application for managing shipments and calculating discounts based on various rules and conditions. The application allows for flexible discount strategies and tracks monthly discount limits.

## Prerequisites
- Java JDK 11 or higher
- Maven 3.6 or higher
- Git (optional, for cloning the repository)

## Setup

### Clone the Repository
```bash
git clone https://github.com/Tomas53/VinTask.git
cd VinTask
```

### Build the Project
```bash
mvn clean install
```

This command will compile the code, run the tests, and create a JAR file in the `target` directory.

## Running the Application

### From the Command Line
```bash
java -cp target/VinTask-1.0-SNAPSHOT.jar Main
```

### From IntelliJ IDEA
1. Open the project in IntelliJ IDEA
2. Navigate to `src/main/java/Main.java`
3. Right-click on the file and select "Run 'Main.main()'"
   - Alternatively, press Ctrl+Shift+F10 (Windows/Linux) or Ctrl+Shift+R (Mac)

## Running Tests

### From the Command Line
```bash
mvn test
```

### From IntelliJ IDEA
1. **Run All Tests**:
   - Right-click on the `src/test/java` folder in the Project view
   - Select "Run 'All Tests in...'"
   - Alternatively, press Ctrl+Shift+F10 (Windows/Linux) or Ctrl+Shift+R (Mac) while the test directory is selected

2. **Run a Specific Test Class**:
   - Navigate to the test class you want to run
   - Right-click on the file and select "Run 'TestClassName'"
   - Alternatively, press Ctrl+Shift+F10 (Windows/Linux) or Ctrl+Shift+R (Mac) while the file is open

3. **Run a Specific Test Method**:
   - Navigate to the test method you want to run
   - Click on the green arrow in the gutter next to the method declaration
   - Alternatively, place your cursor inside the method and press Ctrl+Shift+F10 (Windows/Linux) or Ctrl+Shift+R (Mac)

## Project Structure
```
VinTask/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── BasicProduct.java
│   │   │   ├── DiscountContractRule.java
│   │   │   ├── DiscountStateTracker.java
│   │   │   ├── FileReader.java
│   │   │   ├── Main.java
│   │   │   ├── Price.java
│   │   │   ├── Product.java
│   │   │   ├── ShipmentDiscountCalculator.java
│   │   │   ├── ShipmentManager.java
│   │   │   ├── ShipmentPriceService.java
│   │   │   ├── ShipmentResultFormatter.java
│   │   │   └── SmallestPackageRule.java
│   │   └── resources/
│   └── test/
│       └── java/
│           └── [test classes]
└── pom.xml
```

## Key Components

### Product and BasicProduct
Classes representing shipment products with properties like size, provider, and date.

### DiscountContractRule
Interface that defines the contract for discount rules. Different rule implementations determine how discounts are calculated.

### DiscountStateTracker
Tracks the monthly discount usage to enforce discount caps.

### ShipmentPriceService
Provides pricing information for different shipping providers and package sizes.

### ShipmentDiscountCalculator
Applies discount rules to products and calculates the final discount amount.

### ShipmentManager
Manages the process of handling shipments and applying discounts.
![image](https://github.com/user-attachments/assets/3340c622-b8a3-486d-96b0-fa085828a6a2)
