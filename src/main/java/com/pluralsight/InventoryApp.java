package com.pluralsight;

import java.util.*;

public class InventoryApp {
    public static void main(String[] args) {
        //Create empty array of vehicles
        Vehicle[] vehicleList = new Vehicle[20];

        //Construct test vehicles and add them to the array
        vehicleList[0] = new Vehicle(101121,"Ford Explorer","red",45000,13500);
        vehicleList[1] = new Vehicle(101122,"Toyota Camry","blue",60000,11000);
        vehicleList[2] = new Vehicle(101123,"Chevrolet Malibu","black",50000,9700);
        vehicleList[3] = new Vehicle(101124,"Honda Civic","white",70000,7500);
        vehicleList[4] = new Vehicle(101125,"Subaru Outback","green",55000,14500);
        vehicleList[5] = new Vehicle(101126,"Jeep Wrangler","yellow",30000,16000);

        //Prepare variables for handling user input
        Scanner s = new Scanner(System.in);
        String menu = """
                What do you want to do?
                
                1 - List all vehicles
                2 - Search by make/model
                3 - Search by price range
                4 - Search by color
                5 - Add a vehicle
                6 - Quit
                
                Enter your command:
                """;
        String userInput = getInputFromConsole(menu, s, false);
        int command = !userInput.isEmpty() ? Integer.parseInt(userInput) : 6;
        boolean exitFlag = false;

        //Program loop
        while (!exitFlag) {
            switch (command) {
                case 1 -> displayVehicleList(vehicleList);
                case 2 -> {
                    searchVehicleByModel(getInputFromConsole("Input the model to search for:", s, true), vehicleList);
                }
                case 3 -> {
                    String lowInput = getInputFromConsole("Input the low end of your price range: ",s, true);
                    double low = !lowInput.isEmpty() ? Double.parseDouble(lowInput) : 0;
                    String highInput = getInputFromConsole("Input the high end of your price range: ",s, true);
                    double high = !highInput.isEmpty() ? Double.parseDouble(highInput) : 0;
                    searchVehicleByPriceRange(low, high, vehicleList);
                }
                case 4 -> {
                    searchVehicleByColor(getInputFromConsole("Input the color to search for:", s, true), vehicleList);
                }
                case 5 -> {
                    addVehicleToList(createVehicleFromInput(s), vehicleList);
                }
                default -> {
                    exitFlag = true;
                    System.out.println("Exit command inputted or invalid input received. Exiting program.");
                }
            }
            if (!exitFlag) {
                userInput = getInputFromConsole(menu, s, false);
                command = !userInput.isEmpty() ? Integer.parseInt(userInput) : 6;
            }
        }
    }

    public static String getInputFromConsole(String display, Scanner s, boolean clearBuffer) {
        if (clearBuffer) s.skip("\n");
        System.out.println(display);
        return s.nextLine().trim();
    }

    public static void displayVehicleList(Vehicle[] vehicleList) {
        for (Vehicle v : vehicleList) {
            if (!Objects.isNull(v)) System.out.println(v.retrieveInfo() + "\n");
        }
    }

    public static void searchVehicleByModel(String model, Vehicle[] vehicleList) {
        for (Vehicle v : vehicleList) {
            if (!Objects.isNull(v)) {
                if (model.equalsIgnoreCase(v.getMakeModel())) {
                    System.out.printf("Vehicle %d matches the given model type.\n", v.getVehicleId());
                }
            }
        }
    }

    public static void searchVehicleByPriceRange(double low, double high, Vehicle[] vehicleList) {
        for (Vehicle v : vehicleList) {
            if (!Objects.isNull(v)) {
                if (v.getPrice() >= low && v.getPrice() <= high) {
                    System.out.printf("Vehicle %d's price ($%,.2f) is within the given range.\n", v.getVehicleId(), v.getPrice());
                }
            }
        }
    }

    public static void searchVehicleByColor(String color, Vehicle[] vehicleList) {
        for (Vehicle v : vehicleList) {
            if (!Objects.isNull(v)) {
                if (color.equalsIgnoreCase(v.getColor())) {
                    System.out.printf("Vehicle %d matches the given color.\n", v.getVehicleId());
                }
            }
        }
    }

    //There's no input security on this method because I got lazy, frankly
    public static Vehicle createVehicleFromInput(Scanner s) {
        var id = Long.parseLong(getInputFromConsole("Enter the new vehicle's ID number: ", s, true));
        var model = getInputFromConsole("Enter the vehicle's make and model: ", s, true);
        var col = getInputFromConsole("Enter the vehicle's color:  ", s, true);
        var odr = Integer.parseInt(getInputFromConsole("Enter the vehicle's current odometer reading: ", s, true));
        var pri = Double.parseDouble(getInputFromConsole("Enter the vehicle's price: ", s, true));
        return new Vehicle(id, model, col, odr, pri);
    }

    public static void addVehicleToList(Vehicle v, Vehicle[] vehicleList) {
        int openIndex = 0;
        for (int i = 0; i < vehicleList.length; i++) {
            if (Objects.isNull(vehicleList[i])) {
                openIndex = i;
                break;
            }
        }
        vehicleList[openIndex] = v;
    }
}
