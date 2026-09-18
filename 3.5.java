class Employee {
    String empName;
    double salary;

    static String companyName = "Bright Horizon Technologies";
    static int employeeCount = 0;

    Employee(String empName, double salary) {
        this.empName = empName;
        this.salary = salary;

        employeeCount++;
    }

    static void printCompanyInfo() {
        System.out.println(companyName);
        System.out.println("Employees on record: " + employeeCount);
    }
}

public class Main {
    public static void main(String[] args) {

        Employee e1 = new Employee("Aarav", 50000);
        Employee e2 = new Employee("Ishita", 60000);
        Employee e3 = new Employee("Vedant", 55000);

        Employee.printCompanyInfo();
    }
}