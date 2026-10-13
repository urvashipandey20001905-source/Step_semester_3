import java.util.*;

interface NotificationChannel {
    String name();
    void send(Student student, Notice notice);
}

class EmailChannel implements NotificationChannel {
    public String name() { return "Email"; }
    public void send(Student student, Notice notice) {
        System.out.println("[Email → " + student.getName() + "] " + notice.getTitle());
    }
}

class SmsChannel implements NotificationChannel {
    public String name() { return "SMS"; }
    public void send(Student student, Notice notice) {
        System.out.println("[SMS → " + student.getName() + "] " + notice.getTitle());
    }
}

class AppChannel implements NotificationChannel {
    public String name() { return "App"; }
    public void send(Student student, Notice notice) {
        System.out.println("[App → " + student.getName() + "] " + notice.getTitle());
    }
}

class Student {
    private final String name;
    private final String department;
    private final List<NotificationChannel> channels;

    Student(String name, String department, List<NotificationChannel> channels) {
        this.name = name;
        this.department = department;
        this.channels = new ArrayList<>(channels);
    }

    String getName() { return name; }
    String getDepartment() { return department; }
    List<NotificationChannel> getChannels() {
        return Collections.unmodifiableList(channels);
    }
}

class Notice {
    private final String title;
    private final Set<String> targetDepartments;

    Notice(String title, Set<String> targetDepartments) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Notice title is required");
        }
        if (targetDepartments == null || targetDepartments.isEmpty()) {
            throw new IllegalArgumentException(
                    "At least one target department is required");
        }
        this.title = title;
        this.targetDepartments = new LinkedHashSet<>(targetDepartments);
    }

    String getTitle() { return title; }
    Set<String> getTargetDepartments() {
        return Collections.unmodifiableSet(targetDepartments);
    }
}

class NoticeBoard {
    private final List<Student> students = new ArrayList<>();

    void addStudent(Student student) {
        students.add(student);
    }

    void postNotice(Notice notice) {
        System.out.println("Notice '" + notice.getTitle() + "' posted to "
                + String.join(", ", notice.getTargetDepartments()) + ".");

        for (Student student : students) {
            if (notice.getTargetDepartments().contains(student.getDepartment())) {
                for (NotificationChannel channel : student.getChannels()) {
                    channel.send(student, notice);
                }
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        NoticeBoard board = new NoticeBoard();
        board.addStudent(new Student("Asha", "CSE",
                Arrays.asList(new EmailChannel(), new AppChannel())));
        board.addStudent(new Student("Ravi", "ECE",
                Arrays.asList(new SmsChannel())));

        board.postNotice(new Notice("Lab Closed Tomorrow",
                new LinkedHashSet<>(Arrays.asList("CSE"))));
        board.postNotice(new Notice("Fee Deadline Extended",
                new LinkedHashSet<>(Arrays.asList("CSE", "ECE"))));

        try {
            board.postNotice(new Notice("Sports Day", Collections.emptySet()));
        } catch (IllegalArgumentException e) {
            System.out.println("Cannot post notice: " + e.getMessage() + ".");
        }
    }
}