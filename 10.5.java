import java.util.*;

public class Main {
    static abstract class Staff {
        String name;

        Staff(String name) {
            this.name = name;
        }

        abstract String type();
        abstract double pay();

        String payslip() {
            return "Payslip[name=" + name + ", type=" + type()
                    + ", pay=" + formatPay(pay()) + "]";
        }
    }

    static class FullTime extends Staff {
        double salary;

        FullTime(String name, double salary) {
            super(name);
            this.salary = salary;
        }

        String type() { return "FullTime"; }
        double pay() { return salary; }
    }

    static class PartTime extends Staff {
        double hours;
        double rate;

        PartTime(String name, double hours, double rate) {
            super(name);
            this.hours = hours;
            this.rate = rate;
        }

        String type() { return "PartTime"; }
        double pay() { return hours * rate; }
    }

    static class Intern extends Staff {
        double stipend;

        Intern(String name, double stipend) {
            super(name);
            this.stipend = stipend;
        }

        String type() { return "Intern"; }
        double pay() { return stipend; }
    }

    static String formatPay(double amount) {
        if (amount == Math.floor(amount)) {
            return String.valueOf((long) amount);
        }
        return String.format(Locale.US, "%.2f", amount);
    }

    static Staff createStaff(String[] parts) {
        switch (parts[0]) {
            case "FullTime":
                return new FullTime(parts[1], Double.parseDouble(parts[2]));
            case "PartTime":
                return new PartTime(parts[1],
                        Double.parseDouble(parts[2]),
                        Double.parseDouble(parts[3]));
            case "Intern":
                return new Intern(parts[1], Double.parseDouble(parts[2]));
            default:
                throw new IllegalArgumentException("Unknown staff type: " + parts[0]);
        }
    }

    public static void main(String[] args) {
        List<Staff> staffMembers = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            staffMembers.add(createStaff(line.split("\\s+")));
        }

        double total = 0;
        Staff topEarner = null;

        for (Staff staff : staffMembers) {
            System.out.println(staff.payslip());
            total += staff.pay();

            if (topEarner == null || staff.pay() > topEarner.pay()) {
                topEarner = staff;
            }
        }

        System.out.println("Total " + formatPay(total));
        if (topEarner != null) {
            System.out.println("top earner " + topEarner.name);
        }

        scanner.close();
    }
}