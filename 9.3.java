import java.util.*;

public class Main {
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
        System.out.println(hasPairWithSum(new int[]{2, 7, 11, 15}, 9));
        System.out.println(hasPairWithSum(new int[]{3, 4, 6}, 20));
    }
}