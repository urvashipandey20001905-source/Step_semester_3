import java.util.Arrays;

public class Main {
    static int[] mergeTokens(int[] counterA, int[] counterB) {
        int[] merged = new int[counterA.length + counterB.length];

        int i = 0;
        int j = 0;
        int k = 0;

        while (i < counterA.length && j < counterB.length) {
            if (counterA[i] <= counterB[j]) {
                merged[k++] = counterA[i++];
            } else {
                merged[k++] = counterB[j++];
            }
        }

        while (i < counterA.length) {
            merged[k++] = counterA[i++];
        }

        while (j < counterB.length) {
            merged[k++] = counterB[j++];
        }

        return merged;
    }

    public static void main(String[] args) {
        int[] counterA = {3, 8, 15, 20};
        int[] counterB = {5, 8, 12};

        System.out.println(Arrays.toString(mergeTokens(counterA, counterB)));
    }
}