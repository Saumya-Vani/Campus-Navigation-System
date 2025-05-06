package com.navigation;

import java.util.Scanner;

public class CampusNavigation {
    public static void main(String[] args) {
        // Initialize the navigation service with paths to CSV files for buildings and paths
        NavigationService service = new NavigationService(
                "src/main/resources/buildings.csv", "src/main/resources/paths.csv"
        );

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        // Main loop for user interaction
        while (running) {
            // Display menu options
            System.out.println("\n=== Campus Navigation System ===");
            System.out.println("1. List Buildings");
            System.out.println("2. Find Shortest Path");
            System.out.println("3. Add Building");
            System.out.println("4. Remove Building");
            System.out.println("5. Add Path");
            System.out.println("6. Remove Path");
            System.out.println("7. Update Path");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");

            // Read user input for menu selection
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    // Option 1: List all buildings in the system
                    service.listBuildings();
                    break;

                case 2:
                    // Option 2: Find the shortest path between two buildings
                    System.out.print("Enter from building ID: ");
                    int fromId = Integer.parseInt(scanner.nextLine());

                    System.out.print("Enter to building ID: ");
                    int toId = Integer.parseInt(scanner.nextLine());

                    System.out.print("Accessible only? (true/false): ");
                    boolean accessible = Boolean.parseBoolean(scanner.nextLine());

                    service.navigate(fromId, toId, accessible);
                    break;

                case 3:
                    // Option 3: Add a new building
                    System.out.print("Enter new building ID: ");
                    int newId = Integer.parseInt(scanner.nextLine());

                    System.out.print("Enter new building name: ");
                    String newName = scanner.nextLine();

                    service.addBuilding(newId, newName);
                    break;

                case 4:
                    // Option 4: Remove an existing building
                    System.out.print("Enter building ID to remove: ");
                    int removeId = Integer.parseInt(scanner.nextLine());

                    service.removeBuilding(removeId);
                    break;

                case 5:
                    // Option 5: Add a new path between two buildings
                    System.out.print("Enter from ID: ");
                    int eFrom = Integer.parseInt(scanner.nextLine());

                    System.out.print("Enter to ID: ");
                    int eTo = Integer.parseInt(scanner.nextLine());

                    System.out.print("Enter distance: ");
                    double dist = Double.parseDouble(scanner.nextLine());

                    System.out.print("One way? (true/false): ");
                    boolean oneWay = Boolean.parseBoolean(scanner.nextLine());

                    System.out.print("Accessible? (true/false): ");
                    boolean acc = Boolean.parseBoolean(scanner.nextLine());

                    service.addPath(eFrom, eTo, dist, oneWay, acc);
                    break;

                case 6:
                    // Option 6: Remove a path between two buildings
                    System.out.print("Enter from ID: ");
                    int rFrom = Integer.parseInt(scanner.nextLine());

                    System.out.print("Enter to ID: ");
                    int rTo = Integer.parseInt(scanner.nextLine());

                    service.removePath(rFrom, rTo);
                    break;

                case 7:
                    // Option 7: Update an existing path (distance or accessibility)
                    System.out.print("Enter from ID: ");
                    int uFrom = Integer.parseInt(scanner.nextLine());

                    System.out.print("Enter to ID: ");
                    int uTo = Integer.parseInt(scanner.nextLine());

                    System.out.print("Enter new distance: ");
                    double newDist = Double.parseDouble(scanner.nextLine());

                    System.out.print("Accessible? (true/false): ");
                    boolean newAcc = Boolean.parseBoolean(scanner.nextLine());

                    service.updatePath(uFrom, uTo, newDist, newAcc);
                    break;

                case 8:
                    // Option 8: Exit the application
                    running = false;
                    break;

                default:
                    // Invalid input handling
                    System.out.println("Invalid option. Please try again.");
            }
        }

        // Exit message and close the scanner resource
        System.out.println("Exiting Campus Navigation System.");
        scanner.close();
    }
}
