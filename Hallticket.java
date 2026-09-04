class HallTicket {

    String studentName;
    int seatNumber;

    // Constructor
    HallTicket(String studentName, int seatNumber) {
        this.studentName = studentName;
        this.seatNumber = seatNumber;
    }
}

public class Main {
    public static void main(String[] args) {

        // Create one HallTicket object
        HallTicket priya =
                new HallTicket("Priya", 0);

        // Second variable refers to the SAME object
        HallTicket copy = priya;

        // Change through second reference
        copy.seatNumber = 45;

        // Create a separate object
        HallTicket separate =
                new HallTicket("Priya", 45);

        // Print results
        System.out.println(
                "Priya's seatNumber (via first variable): "
                + priya.seatNumber);

        System.out.println("copy == priya: " + (copy == priya));

        System.out.println(
                "separate == priya: " + (separate == priya));
    }
}