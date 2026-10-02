import java.util.Arrays;

class LibraryMember {

    protected String memberId;
    protected int borrowLimit;
    protected int booksBorrowed;

    private int[] fineHistory;
    private int fineCount;

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

        fineHistory = new int[10];
        fineCount = 0;
    }

    protected void chargeFine(int amount) {

        if (fineCount < fineHistory.length) {
            fineHistory[fineCount] = amount;
            fineCount++;
        }
    }

    public int[] getFineHistory() {
        return Arrays.copyOf(fineHistory, fineCount);
    }

    public int getTotalFine() {

        int total = 0;

        for (int i = 0; i < fineCount; i++) {
            total += fineHistory[i];
        }

        return total;
    }
}

class StudentMember extends LibraryMember {

    public StudentMember(
            String memberId,
            int borrowLimit,
            String course) {

        super(memberId, borrowLimit);
    }

    @Override
    protected void chargeFine(int amount) {
        super.chargeFine(amount / 2);
    }
}

public class FineLedger {

    public static void main(String[] args) {

        StudentMember student =
                new StudentMember(
                        "STU5",
                        3,
                        "CSE");

        student.chargeFine(100);

        System.out.println(
                student.getTotalFine());

        int[] history =
                student.getFineHistory();

        history[0] = 999;

        System.out.println(
                Arrays.toString(
                        student.getFineHistory()));
    }
}