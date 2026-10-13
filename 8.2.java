import java.time.LocalDate;

enum LeaveStatus { PENDING, APPROVED, REJECTED }

interface LeavePolicy {
    boolean allows(int days);
}

class FullTimePolicy implements LeavePolicy {
    @Override public boolean allows(int days) { return days <= 20; }
}

class PartTimePolicy implements LeavePolicy {
    @Override public boolean allows(int days) { return days <= 5; }
}

class ContractorPolicy implements LeavePolicy {
    @Override public boolean allows(int days) { return days <= 2; }
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
    public FullTimeEmployee(String name) { super(name, new FullTimePolicy()); }
}
class PartTimeEmployee extends Employee {
    public PartTimeEmployee(String name) { super(name, new PartTimePolicy()); }
}
class Contractor extends Employee {
    public Contractor(String name) { super(name, new ContractorPolicy()); }
}

class LeaveRequest {
    private final Employee employee;
    private final LocalDate start;
    private final LocalDate end;
    private LeaveStatus status = LeaveStatus.PENDING;

    public LeaveRequest(Employee employee, LocalDate start, LocalDate end) {
        if (end.isBefore(start)) throw new IllegalArgumentException("End date precedes start date");
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
                ? LeaveStatus.APPROVED : LeaveStatus.REJECTED;

        System.out.println(employee.getName() + "'s leave request ("
                + start + " to " + end + ") " + status.toString().toLowerCase()
                + " by " + reviewer + ".");
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
        LeaveService service = new LeaveService();
        Employee john = new FullTimeEmployee("John");

        LeaveRequest request = service.submit(john,
                LocalDate.of(2026, 1, 1), LocalDate.of(2026, 1, 5));
        request.review(true, "Alice");
        System.out.println("Status: " + request.getStatus());
        request.attemptResetToPending();
    }
}