import java.util.Arrays;
import java.util.List;

public class Main {
    static class Result {
        int rowIndex;
        int total;

        Result(int rowIndex, int total) {
            this.rowIndex = rowIndex;
            this.total = total;
        }

        @Override
        public String toString() {
            return "(" + rowIndex + ", " + total + ")";
        }
    }

    static class Book {
        String isbn;
        String title;

        Book(String isbn, String title) {
            this.isbn = isbn;
            this.title = title;
        }
    }

    static Result findTopper(int[][] marks) {
        if (marks == null || marks.length == 0) {
            throw new IllegalArgumentException("Marks must not be empty");
        }

        int bestRow = 0;
        int bestTotal = -1;

        for (int row = 0; row < marks.length; row++) {
            int total = 0;
            for (int mark : marks[row]) {
                total += mark;
            }

            // Strictly greater keeps the smaller row index when totals tie.
            if (total > bestTotal) {
                bestTotal = total;
                bestRow = row;
            }
        }

        return new Result(bestRow, bestTotal);
    }

    static String findBook(List<Book> catalog, String targetIsbn) {
        int left = 0;
        int right = catalog.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int comparison = catalog.get(mid).isbn.compareTo(targetIsbn);

            if (comparison == 0) return catalog.get(mid).title;
            if (comparison < 0) left = mid + 1;
            else right = mid - 1;
        }

        return "Not Found";
    }

    public static void main(String[] args) {
        int[][] marks = {
                {78, 85, 90},
                {88, 92, 79},
                {65, 70, 95}
        };
        System.out.println(findTopper(marks));

        List<Book> catalog = Arrays.asList(
                new Book("0001112223", "Introduction to Algebra"),
                new Book("0002223334", "Beginning Python"),
                new Book("0003334445", "Classic Mythology"),
                new Book("0004445556", "Data and Society"),
                new Book("0005556667", "European History")
        );

        System.out.println(findBook(catalog, "0003334445"));
        System.out.println(findBook(catalog, "0009998887"));
    }
}