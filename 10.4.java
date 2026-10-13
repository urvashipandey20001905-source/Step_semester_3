import java.util.*;

public class Main {
    static class StudentMarks {
        String name;
        int[] marks;

        StudentMarks(String name, int[] marks) {
            this.name = name;
            this.marks = marks;
        }

        int total() {
            return marks[0] + marks[1] + marks[2];
        }
    }

    public static void main(String[] args) {
        List<StudentMarks> students = new ArrayList<>();
        int[] subjectTotals = new int[3];
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split("\\s+", 2);
            String name = parts[0];

            String marksText = parts[1]
                    .replace("[", "")
                    .replace("]", "")
                    .replace(",", " ")
                    .trim();

            String[] values = marksText.split("\\s+");
            int[] marks = new int[3];

            for (int i = 0; i < 3; i++) {
                marks[i] = Integer.parseInt(values[i]);
                subjectTotals[i] += marks[i];
            }

            students.add(new StudentMarks(name, marks));
        }

        if (students.isEmpty()) {
            scanner.close();
            return;
        }

        StringBuilder totalsOutput = new StringBuilder("Totals ");
        StudentMarks topper = students.get(0);

        for (int i = 0; i < students.size(); i++) {
            StudentMarks student = students.get(i);

            if (i > 0) totalsOutput.append(", ");
            totalsOutput.append(student.name).append(" ").append(student.total());

            if (student.total() > topper.total()) {
                topper = student;
            }
        }

        System.out.println(totalsOutput);

        System.out.printf(Locale.US, "averages %.2f, %.2f, %.2f%n",
                subjectTotals[0] / (double) students.size(),
                subjectTotals[1] / (double) students.size(),
                subjectTotals[2] / (double) students.size());

        System.out.println("topper " + topper.name + " (" + topper.total() + ")");
        scanner.close();
    }
}