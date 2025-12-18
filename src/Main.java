import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class Vehicle {
    private String vehicleId;
    private String model;
    private double baseRatePerDay;
    private boolean isAvailable;

    public Vehicle(String vehicleId, String model, double baseRatePerDay) {
        this.vehicleId = vehicleId;
        this.model = model;
        this.baseRatePerDay = baseRatePerDay;
        this.isAvailable = true;
    }

    public abstract double calculateRentalCost(int days);

    public String getVehicleId() { return vehicleId; }
    public String getModel() { return model; }
    public double getBaseRatePerDay() { return baseRatePerDay; }
    
    public boolean isAvailable() { return isAvailable; }
    
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return model + " [ID: " + vehicleId + "]";
    }
}

class Car extends Vehicle {
    public Car(String vehicleId, String model, double baseRatePerDay) {
        super(vehicleId, model, baseRatePerDay);
    }

    @Override
    public double calculateRentalCost(int days) {
        return getBaseRatePerDay() * days;
    }
}

class Bike extends Vehicle {
    public Bike(String vehicleId, String model, double baseRatePerDay) {
        super(vehicleId, model, baseRatePerDay);
    }

    @Override
    public double calculateRentalCost(int days) {
        double cost = getBaseRatePerDay() * days;
        if (days > 3) {
            cost *= 0.90;
        }
        return cost;
    }
}

class Truck extends Vehicle {
    private double loadCapacityTons;

    public Truck(String vehicleId, String model, double baseRatePerDay, double loadCapacityTons) {
        super(vehicleId, model, baseRatePerDay);
        this.loadCapacityTons = loadCapacityTons;
    }

    @Override
    public double calculateRentalCost(int days) {
        return (getBaseRatePerDay() * days) + (loadCapacityTons * 10);
    }
}

class RentalTransaction {
    private String transactionId;
    private Vehicle vehicle;
    private int days;
    private double totalCost;

    public RentalTransaction(String transactionId, Vehicle vehicle, int days, double totalCost) {
        this.transactionId = transactionId;
        this.vehicle = vehicle;
        this.days = days;
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return String.format("Transaction %s: %s for %d days | Cost: ETB %.2f", 
                transactionId, vehicle.getModel(), days, totalCost);
    }
}

class Customer {
    private String customerId;
    private String name;
    private String phoneNumber;
    private String address;
    private String govIdNumber;
    private List<RentalTransaction> rentalHistory;

    public Customer(String customerId, String name, String phoneNumber, String address, String govIdNumber) {
        this.customerId = customerId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.govIdNumber = govIdNumber;
        this.rentalHistory = new ArrayList<>();
    }

    public void addRentalTransaction(RentalTransaction transaction) {
        rentalHistory.add(transaction);
    }

    public String getName() { return name; }
    public String getCustomerId() { return customerId; }
    
    public int getHistorySize() {
        return rentalHistory.size();
    }

    public void showHistory() {
        System.out.println("Rental History for " + name + " (ID: " + customerId + "):");
        if (rentalHistory.isEmpty()) {
            System.out.println("  No rentals found.");
        } else {
            for (RentalTransaction t : rentalHistory) {
                System.out.println("  " + t);
            }
        }
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Name: %s | Phone: %s | Address: %s | GovID: %s", 
            customerId, name, phoneNumber, address, govIdNumber);
    }
}

class RentalSystem {
    public List<Vehicle> vehicles; 
    public List<Customer> customers;

    public RentalSystem() {
        vehicles = new ArrayList<>();
        customers = new ArrayList<>();
    }

    public void addVehicle(Vehicle v) {
        vehicles.add(v);
    }

    public void addCustomer(Customer c) {
        customers.add(c);
    }

    public void displayCustomers() {
        System.out.println("\n--- Registered Customers ---");
        for (Customer c : customers) {
            System.out.println(c);
        }
    }

    public void displayAvailableVehicles() {
        System.out.println("\n--- Available Vehicles ---");
        boolean found = false;
        for (Vehicle v : vehicles) {
            if (v.isAvailable()) {
                System.out.printf("ID: %s | %s (%s) - Rate: ETB %.2f/day%n", 
                    v.getVehicleId(), v.getModel(), v.getClass().getSimpleName(), v.getBaseRatePerDay());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No vehicles currently available.");
        }
    }

    public void rentVehicle(String customerId, String vehicleId, int days) {
        Customer customer = findCustomer(customerId);
        Vehicle vehicle = findVehicle(vehicleId);

        if (customer == null || vehicle == null) {
            System.out.println("Error: Invalid customer or vehicle ID.");
            return;
        }

        if (!vehicle.isAvailable()) {
            System.out.println("Sorry, " + vehicle.getModel() + " is currently unavailable.");
            return;
        }
        if (days <= 0) {
            System.out.println("Error: Rental days must be greater than zero.");
            return;
        }

        vehicle.setAvailable(false);
        double cost = vehicle.calculateRentalCost(days);

        String transId = "T" + (customer.getHistorySize() + 1); 
        RentalTransaction trans = new RentalTransaction(transId, vehicle, days, cost);
        customer.addRentalTransaction(trans);

        System.out.printf("Success! %s rented %s for %d days. Total Cost: ETB %.2f%n", 
                customer.getName(), vehicle.getModel(), days, cost);
    }

    public void returnVehicle(String vehicleId) {
        Vehicle vehicle = findVehicle(vehicleId);
        if (vehicle != null) {
            if (vehicle.isAvailable()) {
                System.out.println("Error: Vehicle " + vehicle.getModel() + " was not marked as rented.");
                return;
            }
            vehicle.setAvailable(true);
            System.out.println("Vehicle " + vehicle.getModel() + " (ID: " + vehicleId + ") has been successfully returned.");
        } else {
            System.out.println("Error: Invalid vehicle ID.");
        }
    }

    public Customer findCustomer(String id) {
        for (Customer c : customers) {
            if (c.getCustomerId().equalsIgnoreCase(id)) return c;
        }
        return null;
    }

    private Vehicle findVehicle(String id) {
        for (Vehicle v : vehicles) {
            if (v.getVehicleId().equalsIgnoreCase(id)) return v;
        }
        return null;
    }
}

public class Main {
    
    private static void printSeparator() {
        System.out.println("\n------------------------------------------\n");
    }

    private static void addNewCustomer(Scanner scanner, RentalSystem system) {
        System.out.println("\n--- Add New Customer ---");
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Phone Number: ");
        String phone = scanner.nextLine();
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();
        System.out.print("Enter Government ID Number: ");
        String govId = scanner.nextLine();

        int nextIdNum = system.customers.size() + 1;
        String newId = "C" + nextIdNum;

        Customer newCustomer = new Customer(newId, name, phone, address, govId);
        system.addCustomer(newCustomer);
        
        System.out.println("Successfully added new customer: " + newCustomer.getName() + " with ID: " + newCustomer.getCustomerId());
    }

    public static void main(String[] args) {
        RentalSystem system = new RentalSystem();
        Scanner scanner = new Scanner(System.in);

        system.addVehicle(new Car("V1", "Toyota Camry", 500.0));
        system.addVehicle(new Bike("V2", "Mountain Bike", 150.0));
        system.addVehicle(new Truck("V3", "Ford F-150", 1000.0, 2.5));
        system.addVehicle(new Car("V4", "Honda Accord", 550.0));
        system.addVehicle(new Car("V5", "Mercedes C-Class", 900.0));
        system.addVehicle(new Bike("V6", "Road Bike", 180.0));
        system.addVehicle(new Truck("V7", "Chevy Silverado 3500", 1100.0, 4.0));
        system.addVehicle(new Car("V8", "BMW X5 SUV", 1200.0));
        system.addVehicle(new Car("V9", "Nissan Versa", 400.0));
        system.addVehicle(new Bike("V10", "Kids Bike", 100.0));

        system.addCustomer(new Customer("C1", "Mulugeta Daba", "091011121314", "Jimma JIT", "G12345"));
        system.addCustomer(new Customer("C2", "Samuel Fayisa", "091516171819", "Jimma JIT", "G67890"));

        while (true) {
            printSeparator();
            System.out.println("Welcome to the Vehicle Rental System CLI");
            System.out.println("1. Display Available Vehicles");
            System.out.println("2. Add New Customer");
            System.out.println("3. Display All Customers");
            System.out.println("4. Rent a Vehicle");
            System.out.println("5. Return a Vehicle");
            System.out.println("6. View Customer History");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 7.");
                continue;
            }

            switch (choice) {
                case 1:
                    system.displayAvailableVehicles();
                    break;
                case 2:
                    addNewCustomer(scanner, system);
                    break;
                case 3:
                    system.displayCustomers();
                    break;
                case 4:
                    System.out.print("Enter Customer ID: "); String custIdRent = scanner.nextLine();
                    System.out.print("Enter Vehicle ID: "); String vehicleIdRent = scanner.nextLine();
                    System.out.print("Enter number of rental days: ");
                    try {
                        int days = Integer.parseInt(scanner.nextLine());
                        system.rentVehicle(custIdRent, vehicleIdRent, days);
                    } catch (NumberFormatException e) {
                         System.out.println("Invalid number format for days. Please enter a valid integer.");
                    }
                    break;
                case 5:
                    System.out.print("Enter Vehicle ID to return: "); String vehicleIdReturn = scanner.nextLine();
                    system.returnVehicle(vehicleIdReturn);
                    break;
                case 6:
                    System.out.print("Enter Customer ID to view history: "); String custIdHistory = scanner.nextLine();
                    Customer c = system.findCustomer(custIdHistory);
                    if (c != null) {
                        c.showHistory();
                    } else {
                        System.out.println("Customer not found.");
                    }
                    break;
                case 7:
                    System.out.println("Exiting the system. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
}