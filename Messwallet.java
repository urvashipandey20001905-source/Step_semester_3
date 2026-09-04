class MessWallet {

    private double balance;

    public MessWallet(double openingBalance) {

        if (openingBalance < 0) {
            System.out.println(
                "Warning: Negative opening balance. Starting at 0."
            );
            balance = 0;
        } else {
            balance = openingBalance;
        }
    }

    public void topUp(double amount) {

        if (amount <= 0) {
            System.out.println("Top-up rejected: invalid amount");
        } else {
            balance += amount;
        }
    }

    public void deduct(double amount) {

        if (amount > balance) {
            System.out.println(
                "Deduct rejected: insufficient balance"
            );
        } 
        else if (amount <= 0) {
            System.out.println(
                "Deduction rejected: invalid amount"
            );
        } 
        else {
            balance -= amount;
        }
    }

    public double getBalance() {
        return balance;
    }
}

public class Main {
    public static void main(String[] args) {

        MessWallet wallet = new MessWallet(500);

        wallet.topUp(200);

        System.out.println(
            "Balance after top-up: " + wallet.getBalance()
        );

        wallet.deduct(1000);

        System.out.println(
            "Final balance: " + wallet.getBalance()
        );
    }
}
