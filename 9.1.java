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

            // Strictly greater preserves the smaller row index in a tie.
            if (total > bestTotal) {
                bestTotal = total;
                bestRow = row;
            }
        }

        return new Result(bestRow, bestTotal);
    }

    public static void main(String[] args) {
        int[][] marks = {
                {78, 85, 90},
                {88, 92, 79},
                {65, 70, 95}
        };

        System.out.println(findTopper(marks));
    }
}