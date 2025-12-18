# Vehicle Rental Management System (CLI)

A robust, **Object-Oriented Programming (OOP)** based Java application designed to manage a vehicle rental fleet. This system provides a Command Line Interface (CLI) to handle vehicle availability, customer registrations, and rental transactions with dynamic pricing logic.



---

## 🚀 Key Features

* **Fleet Management:** Supports multiple vehicle types including **Cars**, **Bikes**, and **Trucks**.
* **Dynamic Pricing Logic:**
    * **Cars:** Standard daily rate calculation.
    * **Bikes:** Includes a 10% loyalty discount for rentals exceeding 3 days.
    * **Trucks:** Features an additional fee based on load capacity (tonnage).
* **Customer Profiles:** Stores comprehensive data including Name, Phone Number, Address, and Government ID.
* **Transaction History:** Tracks every rental, assigning unique transaction IDs and maintaining a persistent history for each customer.
* **Real-time Availability:** Automatically updates vehicle status (Available vs. Rented) upon transaction completion.

---

## 🛠️ Technical Architecture

This project serves as a practical implementation of core **Object-Oriented Programming** principles:

1.  **Abstraction:** Utilizes an `abstract class Vehicle` to define a blueprint for all fleet items while preventing direct instantiation of generic objects.
2.  **Inheritance:** Specialized classes (`Car`, `Bike`, `Truck`) inherit from the base `Vehicle` class to promote code reuse.
3.  **Polymorphism:** Implements method overriding for `calculateRentalCost()`, allowing the system to determine the correct price based on the specific object type at runtime.
4.  **Encapsulation:** Protects data integrity by using private fields and providing controlled access through public getters and setters.
5.  **Collections Framework:** leverages `ArrayList` and `List` interfaces for efficient management of the vehicle fleet and customer registry.

---

## 💻 Installation & Getting Started

### Prerequisites
* **Java Development Kit (JDK):** Version 8 or higher.
* **Terminal/CLI:** Access to a command-line interface (Command Prompt, PowerShell, or Terminal).

### Running the Application
1.  **Clone or Download:** Save the source code into a file named `Main.java`.
2.  **Open Terminal:** Navigate to the directory containing the file.
3.  **Compile the Code:**
    ```bash
    javac Main.java
    ```
4.  **Launch the Program:**
    ```bash
    java Main
    ```

---

## 🎮 Usage Guide

Once the application is running, use the interactive menu to navigate the system:

* **Option 1: Display Available Vehicles** – View all vehicles currently ready for rent.
* **Option 2: Add New Customer** – Register a new user by entering their personal and identification details.
* **Option 3: Display All Customers** – List all registered users in the system.
* **Option 4: Rent a Vehicle** – Link a Customer ID to a Vehicle ID and specify the duration.
* **Option 5: Return a Vehicle** – Process a return using the Vehicle ID to update its status to "Available."
* **Option 6: View Customer History** – Review all past transactions for a specific user.
* **Option 7: Exit** – Safely close the application.

---

## 📝 Future Improvements
* **Persistence:** Integrate a SQL database (like MySQL or SQLite) to save data between sessions.
* **GUI:** Develop a graphical user interface using JavaFX or Swing.
* **Authentication:** Add a login system for administrators and customers.