import java.util.Arrays;

public class Main {
    static int countAlerts(int[] readings, int k, int threshold) {
        if (k < 1 || k > readings.length) {
            throw new IllegalArgumentException("Invalid window size");
        }

        long windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += readings[i];
        }

        int alerts = windowSum >= (long) k * threshold ? 1 : 0;

        for (int right = k; right < readings.length; right++) {
            windowSum += readings[right];
            windowSum -= readings[right - k];

            if (windowSum >= (long) k * threshold) {
                alerts++;
            }
        }

        return alerts;
    }

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
        int[] readings = {2, 2, 2, 2, 5, 5, 5, 8};
        System.out.println(countAlerts(readings, 3, 4));

        System.out.println(hasPairWithSum(new int[]{2, 7, 11, 15}, 9));
        System.out.println(hasPairWithSum(new int[]{3, 4, 6}, 20));
    }
}