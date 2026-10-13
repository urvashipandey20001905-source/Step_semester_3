import java.util.*;

public class Main {
    static class Result {
        String item;
        int count;

        Result(String item, int count) {
            this.item = item;
            this.count = count;
        }

        @Override
        public String toString() {
            return "(\"" + item + "\", " + count + ")";
        }
    }

    static Result mostPopular(String[] orders) {
        if (orders == null || orders.length == 0) {
            throw new IllegalArgumentException("Orders must not be empty");
        }

        Map<String, Integer> counts = new HashMap<>();

        for (String item : orders) {
            counts.put(item, counts.getOrDefault(item, 0) + 1);
        }

        String bestItem = orders[0];
        int bestCount = counts.get(bestItem);

        // Scan original order; update only for a strictly higher count.
        for (String item : orders) {
            int count = counts.get(item);
            if (count > bestCount) {
                bestItem = item;
                bestCount = count;
            }
        }

        return new Result(bestItem, bestCount);
    }

    public static void main(String[] args) {
        String[] orders = {"tea", "coffee", "coffee", "tea"};
        System.out.println(mostPopular(orders));
    }
}