import java.util.*;

public class Main {
    static class Student {
        String rollNumber;
        String name;

        Student(String rollNumber, String name) {
            this.rollNumber = rollNumber;
            this.name = name;
        }

        // Equality is based only on roll number.
        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Student)) return false;
            Student other = (Student) object;
            return rollNumber.equals(other.rollNumber);
        }

        @Override
        public int hashCode() {
            return rollNumber.hashCode();
        }
    }

    public static void main(String[] args) {
        Set<Student> members = new HashSet<>();
        List<String> output = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        boolean countPrinted = false;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split("\\s+");
            Student student = new Student(parts[1], parts[2]);

            if (parts[0].equalsIgnoreCase("ADD")) {
                output.add(members.add(student) ? "Added" : "duplicate rejected");
            } else if (parts[0].equalsIgnoreCase("CONTAINS")) {
                if (!countPrinted) {
                    output.add("member count " + members.size());
                    countPrinted = true;
                }
                output.add("contains: " + members.contains(student));
            }
        }

        if (!countPrinted) {
            output.add("member count " + members.size());
        }

        for (String line : output) {
            System.out.println(line);
        }

        scanner.close();
    }
}