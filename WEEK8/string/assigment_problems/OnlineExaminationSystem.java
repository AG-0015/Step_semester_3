@'
        import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class Student {
    private String name;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

abstract class Question {
    private String questionId;
    private int points;

    public Question(String questionId, int points) {
        this.questionId = questionId;
        this.points = points;
    }

    public String getQuestionId() {
        return questionId;
    }

    public int getPoints() {
        return points;
    }

    public abstract boolean evaluate(String answer);
}

class MultipleChoiceQuestion extends Question {
    private String correctOption;

    public MultipleChoiceQuestion(
            String questionId,
            int points,
            String correctOption
    ) {
        super(questionId, points);
        this.correctOption = correctOption;
    }

    @Override
    public boolean evaluate(String answer) {
        return correctOption.equalsIgnoreCase(answer);
    }
}

class TrueFalseQuestion extends Question {
    private boolean correctAnswer;

    public TrueFalseQuestion(
            String questionId,
            int points,
            boolean correctAnswer
    ) {
        super(questionId, points);
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean evaluate(String answer) {
        return Boolean.parseBoolean(answer) == correctAnswer;
    }
}

class ShortAnswerQuestion extends Question {
    private String correctAnswer;

    public ShortAnswerQuestion(
            String questionId,
            int points,
            String correctAnswer
    ) {
        super(questionId, points);
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean evaluate(String answer) {
        return correctAnswer.equalsIgnoreCase(answer.trim());
    }
}

class Examination {
    private String name;
    private List<Question> questions;

    public Examination(
            String name,
            List<Question> questions
    ) {
        this.name = name;
        this.questions = questions;
    }

    public String getName() {
        return name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public int getTotalPoints() {
        int total = 0;

        for (Question question : questions) {
            total += question.getPoints();
        }

        return total;
    }
}

enum AttemptStatus {
    IN_PROGRESS,
    SUBMITTED
}

class Attempt {
    private Student student;
    private Examination examination;
    private Map<String, String> answers;
    private AttemptStatus status;

    public Attempt(
            Student student,
            Examination examination
    ) {
        this.student = student;
        this.examination = examination;
        this.answers = new LinkedHashMap<>();
        this.status = AttemptStatus.IN_PROGRESS;
    }

    public void recordAnswer(
            String questionId,
            String answer
    ) {
        if (status == AttemptStatus.SUBMITTED) {
            System.out.println(
                    "Cannot change answers for a submitted examination."
            );
            return;
        }

        answers.put(questionId, answer);

        System.out.println(
                "Answer recorded for " + questionId + "."
        );
    }

    public void submit() {
        if (status == AttemptStatus.SUBMITTED) {
            return;
        }

        status = AttemptStatus.SUBMITTED;

        System.out.println(
                examination.getName()
                        + " submitted by "
                        + student.getName()
                        + "."
        );

        calculateResult();
    }

    private void calculateResult() {
        int totalScore = 0;
        int totalPoints = examination.getTotalPoints();

        for (Question question : examination.getQuestions()) {
            String answer = answers.get(question.getQuestionId());

            boolean correct =
                    answer != null && question.evaluate(answer);

            int earnedPoints =
                    correct ? question.getPoints() : 0;

            totalScore += earnedPoints;

            System.out.println(
                    "Result: "
                            + "Question "
                            + question.getQuestionId()
                            + ": "
                            + (correct ? "Correct" : "Incorrect")
                            + " ("
                            + earnedPoints
                            + " points)"
            );
        }

        System.out.println(
                "Total score: "
                        + totalScore
                        + "/"
                        + totalPoints
        );
    }
}

class ExaminationService {

    public Attempt startExamination(
            Student student,
            Examination examination
    ) {
        System.out.println(
                examination.getName()
                        + " started by "
                        + student.getName()
                        + "."
        );

        return new Attempt(student, examination);
    }
}

public class OnlineExaminationSystem {

    public static void main(String[] args) {

        Student student =
                new Student("Student 1");

        Question q1 =
                new MultipleChoiceQuestion(
                        "1",
                        5,
                        "C"
                );

        Question q2 =
                new TrueFalseQuestion(
                        "2",
                        5,
                        false
                );

        Examination exam =
                new Examination(
                        "Exam A",
                        List.of(q1, q2)
                );

        ExaminationService service =
                new ExaminationService();

        Attempt attempt =
                service.startExamination(
                        student,
                        exam
                );

        attempt.recordAnswer("1", "C");
        attempt.recordAnswer("2", "True");

        attempt.submit();

        attempt.recordAnswer("1", "A");
    }
}
'@ | Set-Content "WEEK8\string\assigment_problems\OnlineExaminationSystem.java"