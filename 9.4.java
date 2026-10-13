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

    public static void main(String[] args) {
        int[] readings = {2, 2, 2, 2, 5, 5, 5, 8};
        System.out.println(countAlerts(readings, 3, 4));
    }
}