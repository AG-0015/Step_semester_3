import java.util.Arrays;

public class BookingReceipt {

    private final String bookingId;
    private final String[] seatNumbers;

    public BookingReceipt(
            String bookingId,
            String[] seatNumbers) {

        this.bookingId = bookingId;

        this.seatNumbers =
                Arrays.copyOf(
                        seatNumbers,
                        seatNumbers.length
                );
    }

    public String[] getSeatNumbers() {

        return Arrays.copyOf(
                seatNumbers,
                seatNumbers.length
        );
    }

    public BookingReceipt withUpdatedSeat(
            int index,
            String newSeat) {

        String[] updatedSeats = getSeatNumbers();

        if (index >= 0 && index < updatedSeats.length) {
            updatedSeats[index] = newSeat;
        }

        return new BookingReceipt(
                bookingId,
                updatedSeats
        );
    }

    public static String processNightlySettlement(
            BookingReceipt[] receipts) {

        int processed = 0;
        int nullCount = 0;
        int groupCount = 0;
        int individualCount = 0;

        for (BookingReceipt receipt : receipts) {

            if (receipt == null) {
                nullCount++;
                continue;
            }

            processed++;

            if (receipt instanceof GroupBookingReceipt) {
                groupCount++;
            } else {
                individualCount++;
            }
        }

        return processed + " processed | "
                + nullCount + " null skipped | "
                + groupCount + " group | "
                + individualCount + " individual";
    }

    public static void main(String[] args) {

        BookingReceipt b =
                new BookingReceipt(
                        "CH-1001",
                        new String[]{"A1", "A2"}
                );

        String[] seats = b.getSeatNumbers();

        seats[0] = "X";

        System.out.println(
                b.getSeatNumbers()[0]
        );

        BookingReceipt updated =
                b.withUpdatedSeat(1, "A3");

        System.out.println(
                Arrays.toString(
                        b.getSeatNumbers()
                )
        );

        System.out.println(
                Arrays.toString(
                        updated.getSeatNumbers()
                )
        );

        BookingReceipt[] receipts = {

                new GroupBookingReceipt(
                        "CH-2002",
                        new String[]{"B1", "B2"},
                        2
                ),

                null,

                new BookingReceipt(
                        "CH-3003",
                        new String[]{"C1"}
                )
        };

        System.out.println(
                processNightlySettlement(receipts)
        );
    }
}

class GroupBookingReceipt extends BookingReceipt {

    private final int groupSize;

    public GroupBookingReceipt(
            String bookingId,
            String[] seatNumbers,
            int groupSize) {

        super(bookingId, seatNumbers);

        this.groupSize = groupSize;
    }

    public int getGroupSize() {
        return groupSize;
    }
}
