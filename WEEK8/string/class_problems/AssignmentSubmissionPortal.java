interface AssignmentType {
    String getTypeName();
    double getPenaltyPercent();
}

class CodingAssignment implements AssignmentType {
    @Override
    public String getTypeName() {
        return "Coding";
    }

    @Override
    public double getPenaltyPercent() {
        return 10.0;
    }
}

class WrittenAssignment implements AssignmentType {
    @Override
    public String getTypeName() {
        return "Written";
    }

    @Override
    public double getPenaltyPercent() {
        return 20.0;
    }
}

class Assignment {
    private String title;
    private int awardedMarks;
    private int daysLate;
    private AssignmentType type;

    public Assignment(String title, int awardedMarks,
                      int daysLate, AssignmentType type) {
        this.title = title;
        this.awardedMarks = awardedMarks;
        this.daysLate = daysLate;
        this.type = type;
    }

    public double calculateFinalMarks() {
        double penalty = daysLate * type.getPenaltyPercent();
        return awardedMarks - (awardedMarks * penalty / 100);
    }

    public void displayResult() {
        System.out.println("Assignment: " + title);
        System.out.println("Type: " + type.getTypeName());
        System.out.println("Awarded Marks: " + awardedMarks);
        System.out.println("Days Late: " + daysLate);
        System.out.println("Final Marks: " + calculateFinalMarks());
    }
}

public class AssignmentSubmissionPortal {
    public static void main(String[] args) {
        Assignment coding = new Assignment(
                "Java Coding",
                40,
                2,
                new CodingAssignment()
        );

        Assignment written = new Assignment(
                "Java Theory",
                50,
                1,
                new WrittenAssignment()
        );

        coding.displayResult();
        System.out.println();

        written.displayResult();
    }
}