import java.util.Scanner;

public class Main {

    static String reverseCustomerName(String customerName) {

        char[] reversed = new char[customerName.length()];

        for (int i = 0; i < customerName.length(); i++) {

            reversed[i] =
                    customerName.charAt(customerName.length() - 1 - i);
        }

        return new String(reversed);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter customer name: ");
        String customerName = sc.nextLine();

        String reversedName =
                reverseCustomerName(customerName);

        System.out.println("Original Name: " + customerName);
        System.out.println("Reversed Name: " + reversedName);

        sc.close();
    }
}