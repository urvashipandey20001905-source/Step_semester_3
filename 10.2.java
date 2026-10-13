import java.util.*;

public class Main {
    interface Chargeable {
        boolean bookChargingBay();
    }

    static abstract class Vehicle {
        String passNumber;
        String owner;

        Vehicle(String passNumber, String owner) {
            this.passNumber = passNumber;
            this.owner = owner;
        }

        abstract String type();
        abstract int passFee();
    }

    static class Bike extends Vehicle {
        Bike(String passNumber, String owner) {
            super(passNumber, owner);
        }

        String type() { return "Bike"; }
        int passFee() { return 300; }
    }

    static class Car extends Vehicle {
        Car(String passNumber, String owner) {
            super(passNumber, owner);
        }

        String type() { return "Car"; }
        int passFee() { return 1000; }
    }

    static class EBike extends Vehicle implements Chargeable {
        EBike(String passNumber, String owner) {
            super(passNumber, owner);
        }

        String type() { return "EBike"; }
        int passFee() { return 300; }

        public boolean bookChargingBay() { return true; }
    }

    static class ECar extends Vehicle implements Chargeable {
        ECar(String passNumber, String owner) {
            super(passNumber, owner);
        }

        String type() { return "ECar"; }
        int passFee() { return 1000; }

        public boolean bookChargingBay() { return true; }
    }

    static Vehicle createVehicle(String type, String passNumber, String owner) {
        switch (type.toLowerCase()) {
            case "bike": return new Bike(passNumber, owner);
            case "car": return new Car(passNumber, owner);
            case "ebike":
            case "e-bike": return new EBike(passNumber, owner);
            case "ecar":
            case "e-car": return new ECar(passNumber, owner);
            default: throw new IllegalArgumentException("Unknown vehicle type: " + type);
        }
    }

    public static void main(String[] args) {
        Map<String, Vehicle> vehicles = new HashMap<>();
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split("\\s+");

            if (parts[0].equalsIgnoreCase("PASS")) {
                Vehicle vehicle = createVehicle(parts[1], parts[2], parts[3]);
                vehicles.put(vehicle.passNumber, vehicle);
                System.out.println(vehicle.passNumber + " (" + vehicle.type()
                        + ") pass fee " + vehicle.passFee());
            } else if (parts[0].equalsIgnoreCase("CHARGE")) {
                Vehicle vehicle = vehicles.get(parts[1]);

                if (vehicle == null) {
                    System.out.println("Vehicle not found");
                } else if (vehicle instanceof Chargeable) {
                    ((Chargeable) vehicle).bookChargingBay();
                    System.out.println(vehicle.passNumber + " charging bay allotted");
                } else {
                    System.out.println(vehicle.passNumber
                            + " rejected: charging unsupported");
                }
            }
        }

        scanner.close();
    }
}