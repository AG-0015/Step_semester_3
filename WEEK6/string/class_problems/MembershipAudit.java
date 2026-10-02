class LibraryMember {

    private static int membersEnrolled = 0;

    private final String memberNumber;

    private int borrowLimit;
    private int booksBorrowed;

    public LibraryMember(int borrowLimit) {

        if (borrowLimit <= 0) {
            throw new IllegalArgumentException();
        }

        membersEnrolled++;

        memberNumber =
                "LIB-" + (100 + membersEnrolled);

        this.borrowLimit = borrowLimit;
        this.booksBorrowed = 0;
    }

    public void borrowBook() {

        if (booksBorrowed < borrowLimit) {
            booksBorrowed++;
        }
    }

    public void borrowBook(String genre) {

        // Genre is recorded/accepted,
        // then normal borrowing logic is reused.
        borrowBook();
    }

    public int getBooksBorrowed() {
        return booksBorrowed;
    }

    public String getMemberNumber() {
        return memberNumber;
    }

    public static boolean isValidRenewalCode(
            String code) {

        if (code == null ||
                code.length() != 4) {
            return false;
        }

        return code.charAt(0) == 'R'
                && Character.isDigit(code.charAt(1))
                && Character.isDigit(code.charAt(2))
                && Character.isUpperCase(code.charAt(3));
    }

    public static int getMembersEnrolled() {
        return membersEnrolled;
    }
}

class FacultyMember extends LibraryMember {

    private String department;

    public FacultyMember(
            int borrowLimit,
            String department) {

        super(borrowLimit);
        this.department = department;
    }
}

public class MembershipAudit {

    static String processNightlyAudit(
            LibraryMember[] members) {

        int processed = 0;
        int nullSkipped = 0;
        int faculty = 0;
        int regular = 0;

        for (LibraryMember member : members) {

            if (member == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (member instanceof FacultyMember) {
                faculty++;
            } else {
                regular++;
            }
        }

        return processed
                + " processed | "
                + nullSkipped
                + " null skipped | "
                + faculty
                + " faculty | "
                + regular
                + " regular";
    }

    public static void main(String[] args) {

        LibraryMember m1 =
                new LibraryMember(3);

        System.out.println(
                m1.getMemberNumber());

        System.out.println(
                LibraryMember.getMembersEnrolled());

        System.out.println(
                LibraryMember.isValidRenewalCode(
                        "R12A"));

        System.out.println(
                LibraryMember.isValidRenewalCode(
                        "R1A"));

        System.out.println(
                LibraryMember.isValidRenewalCode(
                        "X12A"));

        m1.borrowBook();
        m1.borrowBook("Fiction");

        System.out.println(
                m1.getBooksBorrowed());

        System.out.println(
                processNightlyAudit(
                        new LibraryMember[]{
                                new FacultyMember(
                                        5,
                                        "Physics"),
                                null,
                                new LibraryMember(3)
                        }
                )
        );
    }
}