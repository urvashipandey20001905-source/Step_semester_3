import java.util.*;

public class Main {
    static abstract class BankAccount {
        String id;
        long balance;

        BankAccount(String id, long balance) {
            this.id = id;
            this.balance = balance;
        }

        abstract String withdrawalError(long amount);

        boolean withdraw(long amount) {
            String error = withdrawalError(amount);
            if (error != null) {
                System.out.println(id + " rejected: " + error);
                return false;
            }

            balance -= amount;
            System.out.println(id + " balance " + balance);
            return true;
        }
    }

    static class SavingsAccount extends BankAccount {
        SavingsAccount(String id, long balance) {
            super(id, balance);
        }

        @Override
        String withdrawalError(long amount) {
            if (balance - amount < 1000) return "minimum balance 1000";
            return null;
        }
    }

    static class CurrentAccount extends BankAccount {
        CurrentAccount(String id, long balance) {
            super(id, balance);
        }

        @Override
        String withdrawalError(long amount) {
            if (balance - amount < -5000) return "overdraft limit 5000";
            return null;
        }
    }

    public static void main(String[] args) {
        Map<String, BankAccount> accounts = new HashMap<>();
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split("\\s+");

            if (parts[0].equalsIgnoreCase("Savings")) {
                accounts.put(parts[1],
                        new SavingsAccount(parts[1], Long.parseLong(parts[2])));
            } else if (parts[0].equalsIgnoreCase("Current")) {
                accounts.put(parts[1],
                        new CurrentAccount(parts[1], Long.parseLong(parts[2])));
            } else if (parts[0].equalsIgnoreCase("WITHDRAW")) {
                BankAccount account = accounts.get(parts[1]);
                if (account == null) {
                    System.out.println("Account not found");
                } else {
                    account.withdraw(Long.parseLong(parts[2]));
                }
            }
        }

        scanner.close();
    }
}