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

        // Keep the first item in case of a tie.
        for (String item : orders) {
            int count = counts.get(item);
            if (count > bestCount) {
                bestItem = item;
                bestCount = count;
            }
        }

        return new Result(bestItem, bestCount);
    }

    static boolean hasPairWithSum(int[] nums, int target) {
        Set<Long> seen = new HashSet<>();

        for (int number : nums) {
            long complement = (long) target - number;
            if (seen.contains(complement)) {
                return true;
            }
            seen.add((long) number);
        }

        return false;
    }

    public static void main(String[] args) {
        String[] orders = {"tea", "coffee", "coffee", "tea"};
        System.out.println(mostPopular(orders));

        System.out.println(hasPairWithSum(new int[]{2, 7, 11, 15}, 9));
        System.out.println(hasPairWithSum(new int[]{3, 4, 6}, 20));
    }
}