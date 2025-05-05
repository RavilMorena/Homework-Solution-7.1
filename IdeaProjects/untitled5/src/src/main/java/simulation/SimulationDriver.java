package simulation;

import mediator.ControlTower;
import aircraft.Aircraft;
import aircraft.PassengerPlane;
import aircraft.CargoPlane;
import aircraft.Helicopter;
import aircraft.Operation;

import java.util.*;

public class SimulationDriver {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        ControlTower tower = new ControlTower();
        List<Aircraft> fleet = new ArrayList<>();

        printHeader();
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter choice (1-3): ");
            switch (choice) {
                case 1:
                    addAircraft(fleet, tower);
                    break;
                case 2:
                    startSimulation(fleet, tower);
                    break;
                case 3:
                    System.out.println("Goodbye! Thanks for using Airport Tower Simulator.");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1, 2 or 3.");
            }
        }
    }

    private static void printHeader() {
        System.out.println("======================================");
        System.out.println("     Welcome to Air Astana");
        System.out.println("======================================");
        System.out.println();
    }

    private static void printMenu() {
        System.out.println("Menu:");
        System.out.println("1. Add Aircraft");
        System.out.println("2. Start Simulation");
        System.out.println("3. Exit");
    }

    private static void addAircraft(List<Aircraft> fleet, ControlTower tower) {
        System.out.println("-- Add New Aircraft --");
        String type = readString("Type (P=Passenger, C=Cargo, H=Helicopter): ").toUpperCase();
        String id = readString("Identifier: ");
        int fuel = readInt("Fuel level: ");
        Aircraft a;
        switch (type) {
            case "P": a = new PassengerPlane(id, fuel, tower); break;
            case "C": a = new CargoPlane(id, fuel, tower); break;
            case "H": a = new Helicopter(id, fuel, tower); break;
            default:
                System.out.println("Unknown type. Aborting add operation.");
                return;
        }
        fleet.add(a);
        System.out.printf("Added %s '%s' with fuel %d.%n",
                a.getClass().getSimpleName(), id, fuel);
        System.out.println();
    }

    private static void startSimulation(List<Aircraft> fleet, ControlTower tower) {
        if (fleet.isEmpty()) {
            System.out.println("No aircraft in fleet. Please add at least one before starting simulation.");
            return;
        }
        System.out.println("-- Simulation Setup --");
        int cycles = readInt("Number of cycles to simulate: ");
        System.out.printf("Starting simulation (%d cycles)...%n", cycles);

        for (int i = 1; i <= cycles; i++) {
            System.out.printf("%nCycle %d of %d:%n", i, cycles);
            Aircraft a = fleet.get(new Random().nextInt(fleet.size()));
            if (a.getFuelLevel() < 15 && !a.isEmergency()) {
                a.triggerEmergency();
            } else {
                Operation op = new Random().nextBoolean()
                        ? Operation.LANDING
                        : Operation.TAKEOFF;
                a.requestRunway(op);
            }
            tower.processNext();
            sleep(1000);
        }
        System.out.println("\nSimulation completed successfully!\n");
    }

    private static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}