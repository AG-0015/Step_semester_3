@'
        import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

abstract class Room {
    private String roomNumber;

    public Room(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public abstract double calculatePrice(long nights);
}

class StandardRoom extends Room {

    public StandardRoom(String roomNumber) {
        super(roomNumber);
    }

    @Override
    public double calculatePrice(long nights) {
        return nights * 100.0;
    }
}

class DeluxeRoom extends Room {

    public DeluxeRoom(String roomNumber) {
        super(roomNumber);
    }

    @Override
    public double calculatePrice(long nights) {
        return nights * 180.0;
    }
}

class Suite extends Room {

    public Suite(String roomNumber) {
        super(roomNumber);
    }

    @Override
    public double calculatePrice(long nights) {
        return nights * 300.0;
    }
}

class Customer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

enum ReservationStatus {
    ACTIVE,
    CANCELLED
}

class Reservation {
    private Customer customer;
    private Room room;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate cancellationDeadline;
    private ReservationStatus status;

    public Reservation(
            Customer customer,
            Room room,
            LocalDate startDate,
            LocalDate endDate,
            LocalDate cancellationDeadline
    ) {
        this.customer = customer;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cancellationDeadline = cancellationDeadline;
        this.status = ReservationStatus.ACTIVE;
    }

    public Room getRoom() {
        return room;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public boolean overlaps(
            LocalDate requestedStart,
            LocalDate requestedEnd
    ) {
        if (status != ReservationStatus.ACTIVE) {
            return false;
        }

        return requestedStart.isBefore(endDate)
                && requestedEnd.isAfter(startDate);
    }

    public double calculatePrice() {
        long nights =
                endDate.toEpochDay()
                        - startDate.toEpochDay();

        return room.calculatePrice(nights);
    }

    public boolean cancel(LocalDate cancellationDate) {
        if (status != ReservationStatus.ACTIVE) {
            return false;
        }

        if (cancellationDate.isAfter(cancellationDeadline)) {
            return false;
        }

        status = ReservationStatus.CANCELLED;
        return true;
    }

    public Customer getCustomer() {
        return customer;
    }
}

class HotelBookingService {
    private List<Reservation> reservations =
            new ArrayList<>();

    public boolean isAvailable(
            Room room,
            LocalDate startDate,
            LocalDate endDate
    ) {
        for (Reservation reservation : reservations) {
            if (reservation.getRoom() == room
                    && reservation.overlaps(
                    startDate,
                    endDate
            )) {
                return false;
            }
        }

        return true;
    }

    public Reservation reserveRoom(
            Customer customer,
            Room room,
            LocalDate startDate,
            LocalDate endDate,
            LocalDate cancellationDeadline
    ) {
        if (!isAvailable(room, startDate, endDate)) {
            System.out.println(
                    room.getClass().getSimpleName()
                            + " "
                            + room.getRoomNumber()
                            + " is not available from "
                            + startDate
                            + " to "
                            + endDate
                            + "."
            );

            return null;
        }

        Reservation reservation =
                new Reservation(
                        customer,
                        room,
                        startDate,
                        endDate,
                        cancellationDeadline
                );

        reservations.add(reservation);

        System.out.println(
                "Reservation confirmed for "
                        + customer.getName()
                        + ", "
                        + room.getClass().getSimpleName()
                        + " "
                        + room.getRoomNumber()
                        + " ("
                        + startDate
                        + " to "
                        + endDate
                        + ")."
        );

        System.out.printf(
                "Price: $%.2f%n",
                reservation.calculatePrice()
        );

        return reservation;
    }

    public void cancelReservation(
            Reservation reservation,
            LocalDate cancellationDate
    ) {
        if (reservation.cancel(cancellationDate)) {
            System.out.println(
                    "Reservation for "
                            + reservation.getCustomer().getName()
                            + ", "
                            + reservation.getRoom().getClass().getSimpleName()
                            + " "
                            + reservation.getRoom().getRoomNumber()
                            + " ("
                            + reservation.getStartDate()
                            + " to "
                            + reservation.getEndDate()
                            + ") cancelled successfully."
            );
        } else {
            System.out.println(
                    "Reservation cancellation failed."
            );
        }
    }
}

public class HotelBookingSystem {

    public static void main(String[] args) {

        HotelBookingService service =
                new HotelBookingService();

        Room standard101 =
                new StandardRoom("101");

        Room deluxe201 =
                new DeluxeRoom("201");

        Customer customerA =
                new Customer("Customer A");

        Customer customerB =
                new Customer("Customer B");

        Customer customerC =
                new Customer("Customer C");

        LocalDate jan1 =
                LocalDate.of(2026, 1, 1);

        LocalDate jan5 =
                LocalDate.of(2026, 1, 5);

        if (service.isAvailable(
                standard101,
                jan1,
                jan5
        )) {
            System.out.println(
                    "Standard Room 101 is available from "
                            + jan1
                            + " to "
                            + jan5
                            + "."
            );
        }

        Reservation reservationA =
                service.reserveRoom(
                        customerA,
                        standard101,
                        jan1,
                        jan5,
                        LocalDate.of(2025, 12, 30)
                );

        service.reserveRoom(
                customerB,
                standard101,
                LocalDate.of(2026, 1, 3),
                LocalDate.of(2026, 1, 7),
                LocalDate.of(2025, 12, 31)
        );

        service.cancelReservation(
                reservationA,
                LocalDate.of(2025, 12, 29)
        );

        service.reserveRoom(
                customerC,
                deluxe201,
                LocalDate.of(2026, 2, 10),
                LocalDate.of(2026, 2, 12),
                LocalDate.of(2026, 2, 5)
        );
    }
}
'@ | Set-Content "WEEK8\string\assigment_problems\HotelBookingSystem.java"