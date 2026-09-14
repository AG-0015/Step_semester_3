import java.util.Arrays;

class LoanReceipt {

    private final String memberId;
    private final String[] bookIds;

    public LoanReceipt(String memberId, String[] bookIds) {
        this.memberId = memberId;
        this.bookIds = Arrays.copyOf(bookIds, bookIds.length);
    }

    public String getMemberId() {
        return memberId;
    }

    public String[] getBookIds() {
        return Arrays.copyOf(bookIds, bookIds.length);
    }

    public LoanReceipt withCorrectedBookId(
            int index,
            String newId) {

        String[] correctedBooks =
                Arrays.copyOf(bookIds, bookIds.length);

        if (index >= 0 && index < correctedBooks.length) {
            correctedBooks[index] = newId;
        }

        return new LoanReceipt(memberId, correctedBooks);
    }
}

class ReferenceOnlyLoanReceipt extends LoanReceipt {

    private final String roomNumber;

    public ReferenceOnlyLoanReceipt(
            String memberId,
            String[] bookIds,
            String roomNumber) {

        super(memberId, bookIds);
        this.roomNumber = roomNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }
}

public class CirculationLedger {

    private static String ledgerStatus;

    static {
        ledgerStatus = "READY";
    }

    static String processNightlyCirculation(
            LoanReceipt[] receipts) {

        int processed = 0;
        int nullSkipped = 0;
        int referenceOnly = 0;
        int regular = 0;

        for (LoanReceipt receipt : receipts) {

            if (receipt == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (receipt instanceof ReferenceOnlyLoanReceipt) {
                referenceOnly++;
            } else {
                regular++;
            }
        }

        return processed
                + " processed | "
                + nullSkipped
                + " null skipped | "
                + referenceOnly
                + " reference-only | "
                + regular
                + " regular";
    }

    public static void main(String[] args) {

        LoanReceipt regular =
                new LoanReceipt(
                        "M001",
                        new String[]{"B101", "B102"}
                );

        ReferenceOnlyLoanReceipt reference =
                new ReferenceOnlyLoanReceipt(
                        "M002",
                        new String[]{"B201"},
                        "ROOM-5"
                );

        LoanReceipt[] receipts = {
                regular,
                reference,
                null
        };

        System.out.println(
                processNightlyCirculation(receipts)
        );
    }
}