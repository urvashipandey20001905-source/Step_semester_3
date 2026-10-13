public class Main {
    static long maxContainerArea(int[] heights) {
        int left = 0;
        int right = heights.length - 1;
        long maxArea = 0;

        while (left < right) {
            long area = (long) Math.min(heights[left], heights[right])
                    * (right - left);
            maxArea = Math.max(maxArea, area);

            // The shorter wall limits the water height.
            if (heights[left] < heights[right]) {
                left++;
            } else {
                right--;
            }
        }

        return maxArea;
    }

    public static void main(String[] args) {
        int[] heights = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        System.out.println(maxContainerArea(heights));
    }
}