import java.time.LocalDate;

// Assignment submission portal

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

    @Override
    double dailyPenaltyRate() { return 0.10; }
}

class WrittenAssignment extends Assignment {
    WrittenAssignment(String title, int maxMarks, LocalDate dueDate) {
        super(title, maxMarks, dueDate);
    }

    @Override
    double dailyPenaltyRate() { return 0.20; }
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
            throw new IllegalArgumentException(
                    "Marks must be within the assignment's mark limit");
        }

        long lateDays = Math.max(0,
                submittedOn.toEpochDay() - assignment.getDueDate().toEpochDay());
        int finalMarks = assignment.applyPenalty(awardedMarks, lateDays);
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
        } else {
            System.out.println("Resubmission is allowed before grading.");
        }
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

// Employee leave workflow

enum LeaveStatus {
    PENDING, APPROVED, REJECTED
}

interface LeavePolicy {
    boolean allows(int days);
}

class FullTimePolicy implements LeavePolicy {
    @Override
    public boolean allows(int days) { return days <= 20; }
}

class PartTimePolicy implements LeavePolicy {
    @Override
    public boolean allows(int days) { return days <= 5; }
}

class ContractorPolicy implements LeavePolicy {
    @Override
    public boolean allows(int days) { return days <= 2; }
}

abstract class Employee {
    private final String name;
    private final LeavePolicy policy;

    protected Employee(String name, LeavePolicy policy) {
        this.name = name;
        this.policy = policy;
    }

    public String getName() { return name; }
    public boolean allowsLeave(int days) { return policy.allows(days); }
}

class FullTimeEmployee extends Employee {
    public FullTimeEmployee(String name) {
        super(name, new FullTimePolicy());
    }
}

class PartTimeEmployee extends Employee {
    public PartTimeEmployee(String name) {
        super(name, new PartTimePolicy());
    }
}

class Contractor extends Employee {
    public Contractor(String name) {
        super(name, new ContractorPolicy());
    }
}

class LeaveRequest {
    private final Employee employee;
    private final LocalDate start;
    private final LocalDate end;
    private LeaveStatus status = LeaveStatus.PENDING;

    public LeaveRequest(Employee employee, LocalDate start, LocalDate end) {
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("End date precedes start date");
        }
        this.employee = employee;
        this.start = start;
        this.end = end;
    }

    public LeaveStatus getStatus() { return status; }

    public boolean review(boolean approved, String reviewer) {
        if (status != LeaveStatus.PENDING) {
            System.out.println("Cannot change leave request status from "
                    + status + " to " + (approved ? "APPROVED" : "REJECTED") + ".");
            return false;
        }

        long days = end.toEpochDay() - start.toEpochDay() + 1;
        status = approved && employee.allowsLeave((int) days)
                ? LeaveStatus.APPROVED
                : LeaveStatus.REJECTED;

        System.out.println(employee.getName() + "'s leave request ("
                + start + " to " + end + ") "
                + status.toString().toLowerCase() + " by " + reviewer + ".");
        return true;
    }

    public void attemptResetToPending() {
        if (status != LeaveStatus.PENDING) {
            System.out.println("Cannot change leave request status from "
                    + status + " to PENDING.");
        }
    }
}

class LeaveService {
    public LeaveRequest submit(Employee employee, LocalDate start, LocalDate end) {
        LeaveRequest request = new LeaveRequest(employee, start, end);
        System.out.println("Leave request submitted for " + employee.getName()
                + " (" + start + " to " + end + "). Status: PENDING.");
        return request;
    }
}

public class Main {
    public static void main(String[] args) {
        // Assignment submission example
        SubmissionPortal portal = new SubmissionPortal();

        Assignment coding = new CodingAssignment("Linked List Lab", 50,
                LocalDate.of(2026, 3, 10));
        Assignment written = new WrittenAssignment("Design Essay", 50,
                LocalDate.of(2026, 3, 12));

        Submission ashaSubmission = portal.submit(
                new Student("Asha"), coding, LocalDate.of(2026, 3, 10));
        Submission raviSubmission = portal.submit(
                new Student("Ravi"), written, LocalDate.of(2026, 3, 14));

        ashaSubmission.grade(45);
        raviSubmission.grade(40);
        ashaSubmission.resubmit(LocalDate.of(2026, 3, 11));

        System.out.println();

        // Employee leave example
        LeaveService leaveService = new LeaveService();
        Employee john = new FullTimeEmployee("John");

        LeaveRequest leaveRequest = leaveService.submit(
                john, LocalDate.of(2026, 1, 1), LocalDate.of(2026, 1, 5));

        leaveRequest.review(true, "Alice");
        System.out.println("Status: " + leaveRequest.getStatus());
        leaveRequest.attemptResetToPending();
    }
}