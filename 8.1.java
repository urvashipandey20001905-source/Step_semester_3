import java.util.ArrayList;
import java.util.List;

// Laundry system

interface WashOption {
    String name();
    int durationMinutes();
    double charge();
}

class QuickWash implements WashOption {
    public String name() { return "Quick"; }
    public int durationMinutes() { return 30; }
    public double charge() { return 20; }
}

class NormalWash implements WashOption {
    public String name() { return "Normal"; }
    public int durationMinutes() { return 45; }
    public double charge() { return 30; }
}

class HeavyWash implements WashOption {
    public String name() { return "Heavy"; }
    public int durationMinutes() { return 60; }
    public double charge() { return 45; }
}

class Student {
    private final String name;

    Student(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }
}

class WashingMachine {
    private final String id;
    private WashCycle activeCycle;

    WashingMachine(String id) {
        this.id = id;
    }

    String getId() {
        return id;
    }

    boolean isFree() {
        return activeCycle == null;
    }

    void start(WashCycle cycle) {
        if (!isFree()) {
            throw new IllegalStateException("Machine is busy");
        }
        activeCycle = cycle;
    }

    void completeCycle() {
        if (isFree()) {
            System.out.println(id + " has no active cycle.");
            return;
        }

        activeCycle = null;
        System.out.println(id + " cycle completed. " + id + " is now free.");
    }
}

class WashCycle {
    private final Student student;
    private final WashingMachine machine;
    private final WashOption option;

    WashCycle(Student student, WashingMachine machine, WashOption option) {
        this.student = student;
        this.machine = machine;
        this.option = option;
    }

    Student getStudent() { return student; }
    WashingMachine getMachine() { return machine; }
    WashOption getOption() { return option; }
}

class LaundrySystem {
    public boolean startWash(
            Student student, WashingMachine machine, WashOption option) {
        if (!machine.isFree()) {
            System.out.println("Machine " + machine.getId() + " is currently busy.");
            return false;
        }

        machine.start(new WashCycle(student, machine, option));
        System.out.printf(
                "%s wash started on %s for %s (%d min). Charge: ₹%.2f.%n",
                option.name(),
                machine.getId(),
                student.getName(),
                option.durationMinutes(),
                option.charge()
        );
        return true;
    }

    public void completeWash(WashingMachine machine) {
        machine.completeCycle();
    }
}

// Vehicle rental system

abstract class Vehicle {
    private final String id;
    private boolean available = true;

    public Vehicle(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public boolean isAvailable() {
        return available;
    }

    void setAvailable(boolean available) {
        this.available = available;
    }

    public abstract double calculateCharge(int days);
}

class Sedan extends Vehicle {
    public Sedan(String id) {
        super(id);
    }

    @Override
    public double calculateCharge(int days) {
        return days * 50.0;
    }
}

class SUV extends Vehicle {
    public SUV(String id) {
        super(id);
    }

    @Override
    public double calculateCharge(int days) {
        return days * 80.0;
    }
}

class Truck extends Vehicle {
    public Truck(String id) {
        super(id);
    }

    @Override
    public double calculateCharge(int days) {
        return days * 100.0;
    }
}

class Customer {
    private final String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
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

    public double getCharge() {
        return vehicle.calculateCharge(days);
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Customer getCustomer() {
        return customer;
    }

    public boolean isActive() {
        return active;
    }

    void close() {
        active = false;
    }
}

class RentalSystem {
    private final List<Rental> rentals = new ArrayList<>();

    public Rental rent(Vehicle vehicle, Customer customer, int days) {
        if (days <= 0) {
            throw new IllegalArgumentException("Days must be positive");
        }

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
        // Run the laundry example
        LaundrySystem laundry = new LaundrySystem();
        WashingMachine m1 = new WashingMachine("M1");
        WashingMachine m2 = new WashingMachine("M2");

        laundry.startWash(new Student("Asha"), m1, new QuickWash());
        laundry.startWash(new Student("Ravi"), m1, new HeavyWash());
        laundry.startWash(new Student("Ravi"), m2, new HeavyWash());
        laundry.completeWash(m1);
        laundry.startWash(new Student("Neha"), m1, new NormalWash());

        System.out.println();

        // Run the vehicle rental example
        RentalSystem rentalSystem = new RentalSystem();
        Vehicle sedan = new Sedan("Sedan A");
        Vehicle suv = new SUV("SUV B");

        Customer c1 = new Customer("Customer 1");
        Customer c2 = new Customer("Customer 2");
        Customer c3 = new Customer("Customer 3");

        Rental firstRental = rentalSystem.rent(sedan, c1, 3);
        rentalSystem.rent(sedan, c2, 2);
        rentalSystem.returnVehicle(firstRental);
        rentalSystem.rent(suv, c3, 5);
    }
}