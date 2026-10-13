import java.util.*;

abstract class Question {
    private final String id;
    private final int points;
    private final String prompt;

    protected Question(String id, String prompt, int points) {
        this.id = id;
        this.prompt = prompt;
        this.points = points;
    }

    public String getId() { return id; }
    public String getPrompt() { return prompt; }
    public int getPoints() { return points; }
    public abstract boolean isCorrect(String answer);
}

class MultipleChoiceQuestion extends Question {
    private final String correctOption;

    public MultipleChoiceQuestion(String id, String prompt, int points, String correctOption) {
        super(id, prompt, points);
        this.correctOption = correctOption;
    }

    @Override public boolean isCorrect(String answer) {
        return correctOption.equalsIgnoreCase(answer.trim());
    }
}

class TrueFalseQuestion extends Question {
    private final boolean correctAnswer;

    public TrueFalseQuestion(String id, String prompt, int points, boolean correctAnswer) {
        super(id, prompt, points);
        this.correctAnswer = correctAnswer;
    }

    @Override public boolean isCorrect(String answer) {
        return Boolean.toString(correctAnswer).equalsIgnoreCase(answer.trim());
    }
}

class ShortAnswerQuestion extends Question {
    private final String expectedAnswer;

    public ShortAnswerQuestion(String id, String prompt, int points, String expectedAnswer) {
        super(id, prompt, points);
        this.expectedAnswer = expectedAnswer;
    }

    @Override public boolean isCorrect(String answer) {
        return expectedAnswer.equalsIgnoreCase(answer.trim());
    }
}

class Examination {
    private final String name;
    private final List<Question> questions;

    public Examination(String name, List<Question> questions) {
        this.name = name;
        this.questions = new ArrayList<>(questions);
    }

    public String getName() { return name; }
    public List<Question> getQuestions() { return Collections.unmodifiableList(questions); }
}

class Attempt {
    private final String student;
    private final Examination examination;
    private final Map<String, String> answers = new HashMap<>();
    private boolean submitted;

    public Attempt(String student, Examination examination) {
        this.student = student;
        this.examination = examination;
    }

    public void answer(String questionId, String answer) {
        if (submitted) {
            System.out.println("Cannot change answers for a submitted examination.");
            return;
        }
        boolean exists = examination.getQuestions().stream()
                .anyMatch(q -> q.getId().equals(questionId));
        if (!exists) throw new IllegalArgumentException("Question not in examination");

        answers.put(questionId, answer);
        System.out.println("Answer recorded for " + questionId + ".");
    }

    public void submit() {
        if (submitted) return;
        submitted = true;
        System.out.println(examination.getName() + " submitted by " + student + ".");

        int score = 0;
        int total = 0;
        for (Question question : examination.getQuestions()) {
            total += question.getPoints();
            boolean correct = question.isCorrect(answers.getOrDefault(question.getId(), ""));
            int earned = correct ? question.getPoints() : 0;
            score += earned;
            System.out.println("Question " + question.getId() + ": "
                    + (correct ? "Correct" : "Incorrect") + " (" + earned
                    + " points)");
        }
        System.out.println("Total score: " + score + "/" + total);
    }
}

public class Main {
    public static void main(String[] args) {
        Examination exam = new Examination("Exam A", Arrays.asList(
                new MultipleChoiceQuestion("1", "Choose an option", 5, "C"),
                new TrueFalseQuestion("2", "Java is an OOP language", 5, false)
        ));

        Attempt attempt = new Attempt("Student 1", exam);
        System.out.println("Exam A started by Student 1.");
        attempt.answer("1", "C");
        attempt.answer("2", "True");
        attempt.submit();
        attempt.answer("1", "A");
    }
}