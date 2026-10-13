import java.time.LocalDate;
import java.util.*;

abstract class Room {
    private final String id;

    protected Room(String id) { this.id = id; }
    public String getId() { return id; }
    public abstract double pricePerNight();

    public double calculatePrice(LocalDate checkIn, LocalDate checkOut) {
        long nights = checkOut.toEpochDay() - checkIn.toEpochDay();
        if (nights <= 0) throw new IllegalArgumentException("Invalid stay dates");
        return nights * pricePerNight();
    }
}

class StandardRoom extends Room {
    public StandardRoom(String id) { super(id); }
    @Override public double pricePerNight() { return 100.0; }
}
class DeluxeRoom extends Room {
    public DeluxeRoom(String id) { super(id); }
    @Override public double pricePerNight() { return 180.0; }
}
class Suite extends Room {
    public Suite(String id) { super(id); }
    @Override public double pricePerNight() { return 300.0; }
}

class Customer {
    private final String name;
    public Customer(String name) { this.name = name; }
    public String getName() { return name; }
}

class Reservation {
    private final Room room;
    private final Customer customer;
    private final LocalDate checkIn;
    private final LocalDate checkOut;
    private final LocalDate cancellationDeadline;
    private boolean active = true;

    public Reservation(Room room, Customer customer, LocalDate checkIn,
                       LocalDate checkOut, LocalDate cancellationDeadline) {
        this.room = room;
        this.customer = customer;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.cancellationDeadline = cancellationDeadline;
    }

    public boolean overlaps(LocalDate start, LocalDate end) {
        return active && checkIn.isBefore(end) && start.isBefore(checkOut);
    }

    public Room getRoom() { return room; }
    public Customer getCustomer() { return customer; }
    public LocalDate getCheckIn() { return checkIn; }
    public LocalDate getCheckOut() { return checkOut; }
    public boolean isActive() { return active; }
    public void cancel() { active = false; }
    public boolean isPastCancellationDeadline(LocalDate today) {
        return today.isAfter(cancellationDeadline);
    }
}

class BookingSystem {
    private final List<Reservation> reservations = new ArrayList<>();

    public boolean isAvailable(Room room, LocalDate start, LocalDate end) {
        return reservations.stream().noneMatch(r ->
                r.getRoom().equals(room) && r.overlaps(start, end));
    }

    public Reservation reserve(Room room, Customer customer, LocalDate start,
                               LocalDate end, LocalDate cancellationDeadline) {
        if (!isAvailable(room, start, end)) {
            System.out.println(room.getId() + " is not available from "
                    + start + " to " + end + ".");
            return null;
        }

        Reservation reservation = new Reservation(room, customer, start, end,
                cancellationDeadline);
        reservations.add(reservation);
        System.out.printf("Reservation confirmed for %s, %s (%s to %s). Price: $%.2f%n",
                customer.getName(), room.getId(), start, end,
                room.calculatePrice(start, end));
        return reservation;
    }

    public boolean cancel(Reservation reservation, LocalDate today) {
        if (reservation == null || !reservation.isActive()) return false;
        if (reservation.isPastCancellationDeadline(today)) {
            System.out.println("Cancellation deadline has passed.");
            return false;
        }
        reservation.cancel();
        System.out.println("Reservation for " + reservation.getCustomer().getName()
                + ", " + reservation.getRoom().getId() + " cancelled successfully.");
        return true;
    }
}

public class Main {
    public static void main(String[] args) {
        BookingSystem system = new BookingSystem();
        Room standard = new StandardRoom("Standard Room 101");
        Customer customerA = new Customer("Customer A");

        LocalDate start = LocalDate.of(2027, 1, 1);
        LocalDate end = LocalDate.of(2027, 1, 5);

        System.out.println(standard.getId() + " is "
                + (system.isAvailable(standard, start, end) ? "available" : "not available")
                + " from " + start + " to " + end + ".");

        Reservation reservation = system.reserve(standard, customerA, start, end,
                LocalDate.of(2026, 12, 20));

        system.reserve(standard, new Customer("Customer B"),
                LocalDate.of(2027, 1, 3), LocalDate.of(2027, 1, 7),
                LocalDate.of(2026, 12, 20));

        system.cancel(reservation, LocalDate.of(2026, 12, 15));
    }
}