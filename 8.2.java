import java.time.LocalDate;

abstract class Assignment {
    private final String title;
    private final int maxMarks;
    private final LocalDate dueDate;

    Assignment(String title, int maxMarks, LocalDate dueDate) {
        this.title = title;
        this.maxMarks = maxMarks;
        this.dueDate = dueDate;
    }

    String getTitle() { return title; }
    int getMaxMarks() { return maxMarks; }
    LocalDate getDueDate() { return dueDate; }
    abstract double dailyPenaltyRate();

    int applyPenalty(int awardedMarks, long lateDays) {
        double penalty = dailyPenaltyRate() * lateDays;
        return (int) Math.round(awardedMarks * Math.max(0, 1.0 - penalty));
    }
}

class CodingAssignment extends Assignment {
    CodingAssignment(String title, int maxMarks, LocalDate dueDate) {
        super(title, maxMarks, dueDate);
    }
    @Override double dailyPenaltyRate() { return 0.10; }
}

class WrittenAssignment extends Assignment {
    WrittenAssignment(String title, int maxMarks, LocalDate dueDate) {
        super(title, maxMarks, dueDate);
    }
    @Override double dailyPenaltyRate() { return 0.20; }
}

class Student {
    private final String name;
    Student(String name) { this.name = name; }
    String getName() { return name; }
}

class Submission {
    private final Student student;
    private final Assignment assignment;
    private final LocalDate submittedOn;
    private String status = "Submitted";
    private Integer finalMarks;

    Submission(Student student, Assignment assignment, LocalDate submittedOn) {
        this.student = student;
        this.assignment = assignment;
        this.submittedOn = submittedOn;
    }

    String getStatus() { return status; }

    void grade(int awardedMarks) {
        if (!"Submitted".equals(status)) {
            System.out.println("Cannot grade: submission is already graded.");
            return;
        }
        if (awardedMarks < 0 || awardedMarks > assignment.getMaxMarks()) {
            throw new IllegalArgumentException("Marks must be within the assignment's mark limit");
        }

        long lateDays = Math.max(0,
                submittedOn.toEpochDay() - assignment.getDueDate().toEpochDay());
        finalMarks = assignment.applyPenalty(awardedMarks, lateDays);
        status = "Graded";

        String lateNote = lateDays == 0 ? "" : " after "
                + (int) (assignment.dailyPenaltyRate() * lateDays * 100)
                + "% late penalty";
        System.out.println(student.getName() + " graded: " + finalMarks + "/"
                + assignment.getMaxMarks() + lateNote + ". Status: " + status + ".");
    }

    void resubmit(LocalDate newDate) {
        if ("Graded".equals(status)) {
            System.out.println("Cannot resubmit: '" + assignment.getTitle()
                    + "' has already been graded.");
            return;
        }
        System.out.println("Resubmission is allowed before grading.");
    }
}

class SubmissionPortal {
    Submission submit(Student student, Assignment assignment, LocalDate date) {
        long lateDays = Math.max(0, date.toEpochDay() - assignment.getDueDate().toEpochDay());
        System.out.println(student.getName() + "'s submission for '"
                + assignment.getTitle() + "' received"
                + (lateDays == 0 ? " (on time)." : " (" + lateDays + " days late).")
                + " Status: Submitted.");
        return new Submission(student, assignment, date);
    }
}

public class Main {
    public static void main(String[] args) {
        SubmissionPortal portal = new SubmissionPortal();

        Assignment coding = new CodingAssignment("Linked List Lab", 50,
                LocalDate.of(2026, 3, 10));
        Assignment written = new WrittenAssignment("Design Essay", 50,
                LocalDate.of(2026, 3, 12));

        Student asha = new Student("Asha");
        Student ravi = new Student("Ravi");

        Submission a = portal.submit(asha, coding, LocalDate.of(2026, 3, 10));
        Submission r = portal.submit(ravi, written, LocalDate.of(2026, 3, 14));

        a.grade(45);
        r.grade(40);
        a.resubmit(LocalDate.of(2026, 3, 11));
    }
}