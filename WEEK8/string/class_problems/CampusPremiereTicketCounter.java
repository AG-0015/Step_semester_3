import java.util.ArrayList;
import java.util.List;

class Seat {
    private String seatNumber;
    private boolean booked;

    public Seat(String seatNumber) {
        this.seatNumber = seatNumber;
        this.booked = false;
    }

    public boolean book() {
        if (booked) {
            return false;
        }

        booked = true;
        return true;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public boolean isBooked() {
        return booked;
    }
}

class Booking {
    private List<Seat> seats = new ArrayList<>();

    public boolean addSeat(Seat seat) {
        if (seats.size() >= 6) {
            return false;
        }

        if (!seat.book()) {
            return false;
        }

        seats.add(seat);
        return true;
    }

    public void displayBooking() {
        System.out.println("Booked Seats:");

        for (Seat seat : seats) {
            System.out.println(seat.getSeatNumber());
        }

        System.out.println("Total Seats: " + seats.size());
    }
}

public class CampusPremiereTicketCounter {
    public static void main(String[] args) {
        Seat seat1 = new Seat("A1");
        Seat seat2 = new Seat("A2");
        Seat seat3 = new Seat("A3");

        Booking booking = new Booking();

        System.out.println("A1 booked: " + booking.addSeat(seat1));
        System.out.println("A2 booked: " + booking.addSeat(seat2));
        System.out.println("A3 booked: " + booking.addSeat(seat3));

        booking.displayBooking();

        System.out.println("A1 booked again: " + booking.addSeat(seat1));
    }
}