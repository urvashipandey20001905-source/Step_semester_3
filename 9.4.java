import java.util.Arrays;

public class Main {
    static boolean hasPairWithSum(int[] nums, int target) {
        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        int left = 0;
        int right = sorted.length - 1;

        while (left < right) {
            long sum = (long) sorted[left] + sorted[right];

            if (sum == target) return true;
            if (sum < target) left++;
            else right--;
        }

        return false;
    }

    public static void main(String[] args) {
        System.out.println(hasPairWithSum(new int[]{2, 7, 11, 15}, 9));
        System.out.println(hasPairWithSum(new int[]{3, 4, 6}, 20));
    }
}