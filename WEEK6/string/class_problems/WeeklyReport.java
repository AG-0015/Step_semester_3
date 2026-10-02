class LibraryMember {

    protected String memberId;
    protected int borrowLimit;
    protected int booksBorrowed;

    public LibraryMember(
            String memberId,
            int borrowLimit) {

        if (memberId == null ||
                memberId.trim().isEmpty() ||
                memberId.trim().length() < 4) {
            throw new IllegalArgumentException();
        }

        if (borrowLimit <= 0) {
            throw new IllegalArgumentException();
        }

        this.memberId = memberId;
        this.borrowLimit = borrowLimit;
    }

    public void borrowBook() {
        if (booksBorrowed < borrowLimit) {
            booksBorrowed++;
        }
    }

    public String displayInfo() {
        return "General | Books: "
                + booksBorrowed;
    }
}

class StudentMember extends LibraryMember {

    private String course;

    public StudentMember(
            String memberId,
            int borrowLimit,
            String course) {

        super(memberId, borrowLimit);
        this.course = course;
    }

    public String getCourse() {
        return course;
    }

    @Override
    public String displayInfo() {
        return "Student | Course: "
                + course
                + " | Books: "
                + booksBorrowed;
    }
}

public class WeeklyReport {

    static String batchPrint(
            LibraryMember[] members) {

        StringBuilder report =
                new StringBuilder();

        for (LibraryMember member : members) {

            report.append(
                    member.displayInfo()
            );

            if (member instanceof StudentMember) {

                StudentMember student =
                        (StudentMember) member;

                report.append(
                        " [Course via downcast: "
                                + student.getCourse()
                                + "]"
                );
            }

            report.append(" | ");
        }

        return report.toString();
    }

    public static void main(String[] args) {

        LibraryMember general =
                new LibraryMember(
                        "LB5", 3);

        StudentMember student =
                new StudentMember(
                        "STU6", 3, "ECE");

        System.out.println(
                batchPrint(
                        new LibraryMember[]{
                                general,
                                student
                        }
                )
        );
    }
}