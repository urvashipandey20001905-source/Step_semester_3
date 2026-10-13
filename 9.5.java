public class Main {
    static int findSlot(int[] prices, int newPrice) {
        int low = 0;
        int high = prices.length;

        // Finds the first index whose value is >= newPrice.
        while (low < high) {
            int mid = low + (high - low) / 2;

            if (prices[mid] < newPrice) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }

        return low;
    }

    static long maxContainerArea(int[] heights) {
        int left = 0;
        int right = heights.length - 1;
        long maxArea = 0;

        while (left < right) {
            long area = (long) Math.min(heights[left], heights[right])
                    * (right - left);
            maxArea = Math.max(maxArea, area);

            // Move the shorter wall inward.
            if (heights[left] < heights[right]) {
                left++;
            } else {
                right--;
            }
        }

        return maxArea;
    }

    public static void main(String[] args) {
        int[] prices = {120, 150, 200, 260};
        System.out.println(findSlot(prices, 150)); // 1
        System.out.println(findSlot(prices, 210)); // 3
        System.out.println(findSlot(prices, 300)); // 4

        int[] heights = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        System.out.println(maxContainerArea(heights)); // 49
    }
}