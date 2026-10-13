public class Main {
    static class GridSummary {
        long totalItems;
        int maxRow;
        int maxColumn;

        GridSummary(long totalItems, int maxRow, int maxColumn) {
            this.totalItems = totalItems;
            this.maxRow = maxRow;
            this.maxColumn = maxColumn;
        }

        @Override
        public String toString() {
            return "(" + totalItems + ", (" + maxRow + ", " + maxColumn + "))";
        }
    }

    static GridSummary warehouseSummary(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            throw new IllegalArgumentException("Grid must not be empty");
        }

        long total = 0;
        int max = -1;
        int maxRow = 0;
        int maxColumn = 0;

        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[row].length; column++) {
                int value = grid[row][column];
                total += value;

                // Strictly greater keeps the first maximum when values tie.
                if (value > max) {
                    max = value;
                    maxRow = row;
                    maxColumn = column;
                }
            }
        }

        return new GridSummary(total, maxRow, maxColumn);
    }

    public static void main(String[] args) {
        int[][] grid = {
                {4, 9, 2},
                {7, 1, 6},
                {3, 12, 5}
        };

        System.out.println(warehouseSummary(grid));
    }
}