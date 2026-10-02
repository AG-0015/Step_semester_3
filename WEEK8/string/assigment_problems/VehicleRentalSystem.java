@'
        import java.util.ArrayList;
import java.util.List;

abstract class Vehicle {
    private String id;
    private boolean available;

    public Vehicle(String id) {
        this.id = id;
        this.available = true;
    }

    public String getId() {
        return id;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public abstract double calculateCharge(int days);
}

class Sedan extends Vehicle {
    public Sedan(String id) {
        super(id);
    }

    @Override
    public double calculateCharge(int days) {
        return days * 50.0;
    }
}

class SUV extends Vehicle {
    public SUV(String id) {
        super(id);
    }

    @Override
    public double calculateCharge(int days) {
        return days * 80.0;
    }
}

class Truck extends Vehicle {
    public Truck(String id) {
        super(id);
    }

    @Override
    public double calculateCharge(int days) {
        return days * 100.0;
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

class Rental {
    private Vehicle vehicle;
    private Customer customer;
    private int days;
    private double amount;
    private boolean active;

    public Rental(Vehicle vehicle, Customer customer, int days) {
        this.vehicle = vehicle;
        this.customer = customer;
        this.days = days;
        this.amount = vehicle.calculateCharge(days);
        this.active = true;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isActive() {
        return active;
    }

    public void closeRental() {
        active = false;
        vehicle.setAvailable(true);
    }
}

class RentalService {
    private List<Rental> rentals = new ArrayList<>();

    public Rental rentVehicle(Vehicle vehicle, Customer customer, int days) {
        if (!vehicle.isAvailable()) {
            System.out.println(vehicle.getId() + " is currently unavailable.");
            return null;
        }

        Rental rental = new Rental(vehicle, customer, days);
        rentals.add(rental);
        vehicle.setAvailable(false);

        System.out.println(
                vehicle.getId() + " rented successfully by "
                        + customer.getName() + "."
        );

        System.out.printf("Rental charge: $%.2f%n", rental.getAmount());

        return rental;
    }

    public void returnVehicle(Rental rental) {
        if (rental != null && rental.isActive()) {
            rental.closeRental();

            System.out.println(
                    rental.getVehicle().getId()
                            + " returned by "
                            + rental.getCustomer().getName() + "."
            );
        }
    }
}

public class VehicleRentalSystem {

    public static void main(String[] args) {
        RentalService service = new RentalService();

        Vehicle sedanA = new Sedan("Sedan A");
        Vehicle suvB = new SUV("SUV B");

        Customer customer1 = new Customer("Customer 1");
        Customer customer2 = new Customer("Customer 2");
        Customer customer3 = new Customer("Customer 3");

        Rental rental1 = service.rentVehicle(
                sedanA,
                customer1,
                3
        );

        service.rentVehicle(
                sedanA,
                customer2,
                2
        );

        service.returnVehicle(rental1);

        service.rentVehicle(
                suvB,
                customer3,
                5
        );
    }
}
'@ | Set-Content "WEEK8\string\assigment_problems\VehicleRentalSystem.java"