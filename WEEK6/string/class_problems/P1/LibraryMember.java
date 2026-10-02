public class LibraryMember {

    protected String memberId;
    protected int borrowLimit;
    protected int booksBorrowed;

    public LibraryMember(String memberId, int borrowLimit) {

        if (memberId == null ||
                memberId.trim().isEmpty() ||
                memberId.trim().length() < 4) {
            throw new IllegalArgumentException("Invalid member ID");
        }

        if (borrowLimit <= 0) {
            throw new IllegalArgumentException("Invalid borrow limit");
        }

        this.memberId = memberId;
        this.borrowLimit = borrowLimit;
        this.booksBorrowed = 0;
    }

    public void borrowBook() {
        if (booksBorrowed < borrowLimit) {
            booksBorrowed++;
        }
    }

    public int getBooksBorrowed() {
        return booksBorrowed;
    }

    public String displayInfo() {
        return "General Member | Books Borrowed: " + booksBorrowed;
    }

    public static String enrollBatch(
            String[] memberIds,
            int borrowLimit) {

        int enrolled = 0;
        int rejected = 0;

        for (String id : memberIds) {
            try {
                new LibraryMember(id, borrowLimit);
                enrolled++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        return "Enrolled: " + enrolled +
                " | Rejected: " + rejected;
    }

    public static void main(String[] args) {

        LibraryMember member =
                new LibraryMember("STU10", 3);

        member.borrowBook();
        member.borrowBook();

        System.out.println(member.getBooksBorrowed());

        System.out.println(
                LibraryMember.enrollBatch(
                        new String[]{
                                "STU1",
                                "LB1",
                                "STU2",
                                " ",
                                "STU3"
                        },
                        3
                )
        );
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
        return "Student Member | Course: "
                + course
                + " | Books Borrowed: "
                + booksBorrowed;
    }
}