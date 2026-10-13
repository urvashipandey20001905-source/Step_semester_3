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
        for (Seat seat : booking.getSeats()) {
            bookedSeats.put(seat.getId(), booking);
        }
    }

    void releaseSeats(Booking booking) {
        for (Seat seat : booking.getSeats()) {
            bookedSeats.remove(seat.getId(), booking);
        }
    }
}

class Booking {
    private final Customer customer;
    private final List<Seat> seats;
    private boolean active = true;

    Booking(Customer customer, List<Seat> seats) {
        this.customer = customer;
        this.seats = new ArrayList<>(seats);
    }

    List<Seat> getSeats() { return Collections.unmodifiableList(seats); }
    boolean isActive() { return active; }
    void cancel() { active = false; }
    Customer getCustomer() { return customer; }

    double total() {
        return seats.stream().mapToDouble(Seat::price).sum();
    }

    String seatList() {
        List<String> ids = new ArrayList<>();
        for (Seat seat : seats) ids.add(seat.getId());
        return String.join(", ", ids);
    }
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
                System.out.println("Seat " + seat.getId()
                        + " is already booked for this show.");
                return null;
            }
        }

        Booking booking = new Booking(customer, seats);
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
        System.out.println(booking.getCustomer().getName()
                + "'s booking cancelled. Seats " + booking.seatList() + " released.");
        return true;
    }
}

abstract class Question {
    private final String id;
    private final int points;
    private final String prompt;

    protected Question(String id, String prompt, int points) {
        this.id = id;
        this.prompt = prompt;
        this.points = points;
    }

    public String getId() { return id; }
    public String getPrompt() { return prompt; }
    public int getPoints() { return points; }
    public abstract boolean isCorrect(String answer);
}

class MultipleChoiceQuestion extends Question {
    private final String correctOption;

    public MultipleChoiceQuestion(String id, String prompt, int points,
                                  String correctOption) {
        super(id, prompt, points);
        this.correctOption = correctOption;
    }

    @Override
    public boolean isCorrect(String answer) {
        return correctOption.equalsIgnoreCase(answer.trim());
    }
}

class TrueFalseQuestion extends Question {
    private final boolean correctAnswer;

    public TrueFalseQuestion(String id, String prompt, int points,
                             boolean correctAnswer) {
        super(id, prompt, points);
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean isCorrect(String answer) {
        return Boolean.toString(correctAnswer).equalsIgnoreCase(answer.trim());
    }
}

class ShortAnswerQuestion extends Question {
    private final String expectedAnswer;

    public ShortAnswerQuestion(String id, String prompt, int points,
                               String expectedAnswer) {
        super(id, prompt, points);
        this.expectedAnswer = expectedAnswer;
    }

    @Override
    public boolean isCorrect(String answer) {
        return expectedAnswer.equalsIgnoreCase(answer.trim());
    }
}

class Examination {
    private final String name;
    private final List<Question> questions;

    public Examination(String name, List<Question> questions) {
        this.name = name;
        this.questions = new ArrayList<>(questions);
    }

    public String getName() { return name; }
    public List<Question> getQuestions() {
        return Collections.unmodifiableList(questions);
    }
}

class Attempt {
    private final String student;
    private final Examination examination;
    private final Map<String, String> answers = new HashMap<>();
    private boolean submitted;

    public Attempt(String student, Examination examination) {
        this.student = student;
        this.examination = examination;
    }

    public void answer(String questionId, String answer) {
        if (submitted) {
            System.out.println("Cannot change answers for a submitted examination.");
            return;
        }

        boolean exists = examination.getQuestions().stream()
                .anyMatch(q -> q.getId().equals(questionId));
        if (!exists) throw new IllegalArgumentException("Question not in examination");

        answers.put(questionId, answer);
        System.out.println("Answer recorded for " + questionId + ".");
    }

    public void submit() {
        if (submitted) return;
        submitted = true;
        System.out.println(examination.getName() + " submitted by " + student + ".");

        int score = 0;
        int total = 0;
        for (Question question : examination.getQuestions()) {
            total += question.getPoints();
            boolean correct = question.isCorrect(
                    answers.getOrDefault(question.getId(), ""));
            int earned = correct ? question.getPoints() : 0;
            score += earned;
            System.out.println("Question " + question.getId() + ": "
                    + (correct ? "Correct" : "Incorrect")
                    + " (" + earned + " points)");
        }
        System.out.println("Total score: " + score + "/" + total);
    }
}

public class Main {
    public static void main(String[] args) {
        // Ticket booking example
        LocalDateTime showtime = LocalDateTime.of(2026, 10, 14, 19, 0);
        Show show = new Show("7 PM show", showtime);
        TicketDesk desk = new TicketDesk();

        Booking asha = desk.book(new Customer("Asha"), show, Arrays.asList(
                new RegularSeat("A1"),
                new RegularSeat("A2"),
                new PremiumSeat("F5")
        ));
        desk.book(new Customer("Ravi"), show,
                Arrays.asList(new RegularSeat("A2")));
        desk.book(new Customer("Ravi"), show,
                Arrays.asList(new ReclinerSeat("R1")));

        desk.cancel(asha, show, LocalDateTime.of(2026, 10, 14, 18, 0));
        desk.book(new Customer("Neha"), show,
                Arrays.asList(new RegularSeat("A2")));

        System.out.println();

        // Examination example
        Examination exam = new Examination("Exam A", Arrays.asList(
                new MultipleChoiceQuestion("1", "Choose an option", 5, "C"),
                new TrueFalseQuestion("2", "Java is an OOP language", 5, false)
        ));

        Attempt attempt = new Attempt("Student 1", exam);
        System.out.println("Exam A started by Student 1.");
        attempt.answer("1", "C");
        attempt.answer("2", "True");
        attempt.submit();
        attempt.answer("1", "A");
    }
}