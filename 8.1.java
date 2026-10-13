import java.util.*;

abstract class Vehicle {
    private final String id;
    private boolean available = true;

    public Vehicle(String id) {
        this.id = id;
    }

    public String getId() { return id; }
    public boolean isAvailable() { return available; }
    void setAvailable(boolean available) { this.available = available; }

    public abstract double calculateCharge(int days);
}

class Sedan extends Vehicle {
    public Sedan(String id) { super(id); }
    @Override public double calculateCharge(int days) { return days * 50.0; }
}

class SUV extends Vehicle {
    public SUV(String id) { super(id); }
    @Override public double calculateCharge(int days) { return days * 80.0; }
}

class Truck extends Vehicle {
    public Truck(String id) { super(id); }
    @Override public double calculateCharge(int days) { return days * 100.0; }
}

class Customer {
    private final String name;
    public Customer(String name) { this.name = name; }
    public String getName() { return name; }
}

class Rental {
    private final Vehicle vehicle;
    private final Customer customer;
    private final int days;
    private boolean active = true;

    public Rental(Vehicle vehicle, Customer customer, int days) {
        this.vehicle = vehicle;
        this.customer = customer;
        this.days = days;
    }

    public double getCharge() { return vehicle.calculateCharge(days); }
    public Vehicle getVehicle() { return vehicle; }
    public Customer getCustomer() { return customer; }
    public boolean isActive() { return active; }
    void close() { active = false; }
}

class RentalSystem {
    private final List<Rental> rentals = new ArrayList<>();

    public Rental rent(Vehicle vehicle, Customer customer, int days) {
        if (days <= 0) throw new IllegalArgumentException("Days must be positive");
        if (!vehicle.isAvailable()) {
            System.out.println(vehicle.getId() + " is currently unavailable.");
            return null;
        }

        Rental rental = new Rental(vehicle, customer, days);
        rentals.add(rental);
        vehicle.setAvailable(false);

        System.out.printf("%s rented successfully by %s. Rental charge: $%.2f%n",
                vehicle.getId(), customer.getName(), rental.getCharge());
        return rental;
    }

    public void returnVehicle(Rental rental) {
        if (rental == null || !rental.isActive()) {
            System.out.println("Rental is not active.");
            return;
        }

        rental.close();
        rental.getVehicle().setAvailable(true);
        System.out.println(rental.getVehicle().getId()
                + " returned by " + rental.getCustomer().getName() + ".");
    }
}

public class Main {
    public static void main(String[] args) {
        RentalSystem system = new RentalSystem();
        Vehicle sedan = new Sedan("Sedan A");
        Vehicle suv = new SUV("SUV B");
        Customer c1 = new Customer("Customer 1");
        Customer c2 = new Customer("Customer 2");
        Customer c3 = new Customer("Customer 3");

        Rental firstRental = system.rent(sedan, c1, 3);
        system.rent(sedan, c2, 2);
        system.returnVehicle(firstRental);
        system.rent(suv, c3, 5);
    }
}