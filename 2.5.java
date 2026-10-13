import java.util.Scanner;

public class Main {

    static String normalizeReference(String raw) {

        String reference = raw.trim();

        if (reference.length() < 3) {
            return reference.toUpperCase();
        }

        String bankCode =
                reference.substring(0, 3).toUpperCase();

        String remaining =
                reference.substring(3);

        return bankCode + remaining;
    }

    static String validateAndFormat(String reference) {

        // Step 1: Check length
        if (reference.length() != 14) {
            return "Invalid: wrong length";
        }

        // Step 2: Check first 3 characters are letters
        for (int i = 0; i < 3; i++) {

            if (!Character.isLetter(reference.charAt(i))) {
                return "Invalid: bank code must be 3 letters";
            }
        }

        // Step 3: Check remaining 11 characters are digits
        for (int i = 3; i < reference.length(); i++) {

            if (!Character.isDigit(reference.charAt(i))) {
                return "Invalid: body must contain only digits";
            }
        }

        // Step 4: Extract parts
        String bankCode =
                reference.substring(0, 3);

        String date =
                reference.substring(3, 9);

        String sequence =
                reference.substring(9, 14);

        // Date is ddMMyy
        String day = date.substring(0, 2);
        String month = date.substring(2, 4);
        String year = date.substring(4, 6);

        // Step 5: Build formatted output
        StringBuilder result = new StringBuilder();

        result.append("[");
        result.append(bankCode);
        result.append("] DATE: ");
        result.append(day);
        result.append("/");
        result.append(month);
        result.append("/");
        result.append(year);
        result.append(" | SEQ: ");
        result.append(sequence);

        return result.toString();
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter transaction reference: ");
        String raw = sc.nextLine();

        String normalized =
                normalizeReference(raw);

        String result =
                validateAndFormat(normalized);

        System.out.println(result);

        sc.close();
    }
}