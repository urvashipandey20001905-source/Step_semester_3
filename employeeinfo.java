class Employee {

    // Instance fields
    String empName;
    double salary;

    // Static fields
    static String companyName = "Bright Horizon Technologies";
    static int employeeCount = 0;

    // Constructor
    Employee(String empName, double salary) {
        this.empName = empName;
        this.salary = salary;

        // Increment employee count
        employeeCount++;
    }

    // Static method
    static void printCompanyInfo() {
        System.out.println(companyName);
        System.out.println("Employees on record: " + employeeCount);
    }
}

public class Main {
    public static void main(String[] args) {

        // Create three Employee objects
        Employee employee1 =
                new Employee("Divya", 65000);

        Employee employee2 =
                new Employee("Arjun", 50000);

        Employee employee3 =
                new Employee("Priya", 55000);

        // Call static method using class name
        Employee.printCompanyInfo();
    }
}