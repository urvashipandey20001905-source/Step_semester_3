import java.time.LocalDate;
import java.util.*;

interface MembershipPlan {
    String name();
    int months();
    double discount();

    default double calculateFee() {
        return 1000.0 * months() * (1.0 - discount());
    }
}

class MonthlyPlan implements MembershipPlan {
    public String name() { return "Monthly"; }
    public int months() { return 1; }
    public double discount() { return 0; }
}

class QuarterlyPlan implements MembershipPlan {
    public String name() { return "Quarterly"; }
    public int months() { return 3; }
    public double discount() { return 0.10; }
}

class AnnualPlan implements MembershipPlan {
    public String name() { return "Annual"; }
    public int months() { return 12; }
    public double discount() { return 0.25; }
}

enum MembershipStatus { ACTIVE, FROZEN, EXPIRED }

class Member {
    private final String name;

    Member(String name) { this.name = name; }
    String getName() { return name; }
}

class Membership {
    private final Member member;
    private final MembershipPlan plan;
    private MembershipStatus status = MembershipStatus.ACTIVE;

    Membership(Member member, MembershipPlan plan) {
        this.member = member;
        this.plan = plan;
    }

    MembershipStatus getStatus() { return status; }

    void checkIn() {
        if (status == MembershipStatus.ACTIVE) {
            System.out.println(member.getName() + " checked in successfully.");
        } else {
            System.out.println("Check-in denied: " + member.getName()
                    + "'s membership is " + status + ".");
        }
    }

    void freeze() {
        if (status == MembershipStatus.EXPIRED) {
            System.out.println("Cannot freeze an Expired membership.");
        } else if (status == MembershipStatus.FROZEN) {
            System.out.println(member.getName()
                    + "'s membership is already Frozen.");
        } else {
            status = MembershipStatus.FROZEN;
            System.out.println(member.getName()
                    + "'s membership frozen. Status: " + status + ".");
        }
    }

    void unfreeze() {
        if (status == MembershipStatus.EXPIRED) {
            System.out.println("Cannot unfreeze an Expired membership.");
        } else if (status == MembershipStatus.FROZEN) {
            status = MembershipStatus.ACTIVE;
            System.out.println(member.getName()
                    + "'s membership unfrozen. Status: " + status + ".");
        }
    }

    void expire() {
        status = MembershipStatus.EXPIRED;
        System.out.println(member.getName()
                + "'s membership expired. Status: " + status + ".");
    }
}

class MembershipDesk {
    Membership buy(Member member, MembershipPlan plan) {
        Membership membership = new Membership(member, plan);
        System.out.printf("%s membership created for %s. Fee: ₹%.2f. Status: Active.%n",
                plan.name(), member.getName(), plan.calculateFee());
        return membership;
    }
}

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
        System.out.printf(
                "Reservation confirmed for %s, %s (%s to %s). Price: $%.2f%n",
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
                + ", " + reservation.getRoom().getId()
                + " cancelled successfully.");
        return true;
    }
}

public class Main {
    public static void main(String[] args) {
        // Membership example
        MembershipDesk desk = new MembershipDesk();
        Membership asha = desk.buy(new Member("Asha"), new QuarterlyPlan());
        Membership ravi = desk.buy(new Member("Ravi"), new MonthlyPlan());

        asha.checkIn();
        asha.freeze();
        asha.checkIn();
        ravi.expire();
        ravi.freeze();

        System.out.println();

        // Hotel booking example
        BookingSystem bookingSystem = new BookingSystem();
        Room standard = new StandardRoom("Standard Room 101");
        Customer customerA = new Customer("Customer A");

        LocalDate start = LocalDate.of(2027, 1, 1);
        LocalDate end = LocalDate.of(2027, 1, 5);

        System.out.println(standard.getId() + " is "
                + (bookingSystem.isAvailable(standard, start, end)
                        ? "available" : "not available")
                + " from " + start + " to " + end + ".");

        Reservation reservation = bookingSystem.reserve(
                standard, customerA, start, end, LocalDate.of(2026, 12, 20));

        bookingSystem.reserve(standard, new Customer("Customer B"),
                LocalDate.of(2027, 1, 3), LocalDate.of(2027, 1, 7),
                LocalDate.of(2026, 12, 20));

        bookingSystem.cancel(reservation, LocalDate.of(2026, 12, 15));
    }
}