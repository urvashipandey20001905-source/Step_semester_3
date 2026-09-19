import java.util.*;

public class Main {

    static void printFilteredWordFrequency(String feedback) {

        // Convert to lowercase
        String text = feedback.toLowerCase();

        // Remove punctuation
        text = text.replace(".", "");
        text = text.replace(",", "");

        // Split into words
        String[] words = text.split("\\s+");

        // Stop words
        HashSet<String> stopWords = new HashSet<>();

        stopWords.add("the");
        stopWords.add("was");
        stopWords.add("and");
        stopWords.add("a");
        stopWords.add("is");
        stopWords.add("of");
        stopWords.add("in");

        // Frequency map
        HashMap<String, Integer> frequency = new HashMap<>();

        for (String word : words) {

            if (stopWords.contains(word)) {
                continue;
            }

            frequency.put(word, frequency.getOrDefault(word, 0) + 1);
        }

        // Convert map entries to a list
        ArrayList<Map.Entry<String, Integer>> list =
                new ArrayList<>(frequency.entrySet());

        // Sort by count in descending order
        list.sort((a, b) -> b.getValue() - a.getValue());

        // Print result
        for (Map.Entry<String, Integer> entry : list) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String feedback = sc.nextLine();

        printFilteredWordFrequency(feedback);
    }
}