class PayrollAccount {

    private double basicSalary;
    private double bonus;

    // Constructor
    public PayrollAccount(double basicSalary) {

        if (basicSalary < 0) {
            System.out.println("Warning: Negative salary not allowed. Starting at 0.");
            this.basicSalary = 0;
        } else {
            this.basicSalary = basicSalary;
        }

        this.bonus = 0;
    }

    // Credit bonus
    public void creditBonus(double amount) {

        if (amount <= 0) {
            System.out.println("Invalid bonus: Amount must be greater than 0.");
        } else {
            bonus += amount;
            System.out.println("Bonus credited: Rs " + amount);
        }
    }

    // Deduct tax
    public void deductTax(double percent) {

        if (percent < 0 || percent > 100) {
            System.out.println("Invalid tax percentage.");
        } else {
            basicSalary = basicSalary - (basicSalary * percent / 100);
            System.out.println("Tax deducted: " + percent + "%");
        }
    }

    // Read-only access to net salary
    public double getNetSalary() {
        return basicSalary + bonus;
    }
}

public class Main {
    public static void main(String[] args) {

        PayrollAccount account = new PayrollAccount(50000);

        account.creditBonus(5000);

        account.deductTax(10);

        System.out.println("Net salary: Rs " + account.getNetSalary());
    }
}