
import java.time.LocalDateTime;
import java.util.*;

abstract class Seat {
    private final String id;
    Seat(String id) { this.id = id; }
    String getId() { return id; }
    abstract double price();
}

class RegularSeat extends Seat {
    RegularSeat(String id) { super(id); }
    @Override double price() { return 150; }
}
class PremiumSeat extends Seat {
    PremiumSeat(String id) { super(id); }
    @Override double price() { return 250; }
}
class ReclinerSeat extends Seat {
    ReclinerSeat(String id) { super(id); }
    @Override double price() { return 400; }
}

class Customer {
    private final String name;
    Customer(String name) { this.name = name; }
    String getName() { return name; }
}

class Show {
    private final String name;
    private final LocalDateTime startsAt;
    private final Map<String, Booking> bookedSeats = new HashMap<>();

    Show(String name, LocalDateTime startsAt) {
        this.name = name;
        this.startsAt = startsAt;
    }

    String getName() { return name; }
    boolean hasStarted(LocalDateTime now) { return !now.isBefore(startsAt); }
    boolean isAvailable(String seatId) { return !bookedSeats.containsKey(seatId); }

    void reserveSeats(Booking booking) {
        for (Seat seat : booking.getSeats()) bookedSeats.put(seat.getId(), booking);
    }

    void releaseSeats(Booking booking) {
        for (Seat seat : booking.getSeats()) bookedSeats.remove(seat.getId(), booking);
    }
}

class Booking {
    private final Customer customer;
    private final Show show;
    private final List<Seat> seats;
    private boolean active = true;

    Booking(Customer customer, Show show, List<Seat> seats) {
        this.customer = customer;
        this.show = show;
        this.seats = new ArrayList<>(seats);
    }

    List<Seat> getSeats() { return Collections.unmodifiableList(seats); }
    boolean isActive() { return active; }
    void cancel() { active = false; }

    double total() {
        return seats.stream().mapToDouble(Seat::price).sum();
    }

    String seatList() {
        List<String> ids = new ArrayList<>();
        for (Seat seat : seats) ids.add(seat.getId());
        return String.join(", ", ids);
    }

    Customer getCustomer() { return customer; }
}

class TicketDesk {
    Booking book(Customer customer, Show show, List<Seat> seats) {
        if (seats.isEmpty() || seats.size() > 6) {
            System.out.println("A booking must contain between 1 and 6 seats.");
            return null;
        }

        Set<String> uniqueIds = new HashSet<>();
        for (Seat seat : seats) {
            if (!uniqueIds.add(seat.getId())) {
                System.out.println("A seat cannot appear twice in one booking.");
                return null;
            }
            if (!show.isAvailable(seat.getId())) {
                System.out.println("Seat " + seat.getId() + " is already booked for this show.");
                return null;
            }
        }

        Booking booking = new Booking(customer, show, seats);
        show.reserveSeats(booking);
        System.out.printf("Booking confirmed for %s: %s. Total: ₹%.2f.%n",
                customer.getName(), booking.seatList(), booking.total());
        return booking;
    }

    boolean cancel(Booking booking, Show show, LocalDateTime now) {
        if (booking == null || !booking.isActive()) return false;
        if (show.hasStarted(now)) {
            System.out.println("Cannot cancel: the show has already started.");
            return false;
        }

        booking.cancel();
        show.releaseSeats(booking);
        System.out.println(booking.getCustomer().getName() + "'s booking cancelled. Seats "
                + booking.seatList() + " released.");
        return true;
    }
}

public class Main {
    public static void main(String[] args) {
        LocalDateTime showtime = LocalDateTime.of(2026, 10, 14, 19, 0);
        Show show = new Show("7 PM show", showtime);
        TicketDesk desk = new TicketDesk();

        Booking asha = desk.book(new Customer("Asha"), show, Arrays.asList(
                new RegularSeat("A1"), new RegularSeat("A2"), new PremiumSeat("F5")));
        desk.book(new Customer("Ravi"), show, Arrays.asList(new RegularSeat("A2")));
        desk.book(new Customer("Ravi"), show, Arrays.asList(new ReclinerSeat("R1")));

        desk.cancel(asha, show, LocalDateTime.of(2026, 10, 14, 18, 0));
        desk.book(new Customer("Neha"), show, Arrays.asList(new RegularSeat("A2")));
    }
}