@'
        import java.util.ArrayList;
import java.util.List;

class Customer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Product {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class OrderItem {
    private Product product;
    private int quantity;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public double getTotal() {
        return product.getPrice() * quantity;
    }
}

enum OrderStatus {
    PENDING,
    PAID
}

interface PaymentMethod {
    boolean processPayment(
            double amount,
            String orderId
    );
}

class CreditCardPayment implements PaymentMethod {

    @Override
    public boolean processPayment(
            double amount,
            String orderId
    ) {
        System.out.println(
                "Payment initiated via Credit Card for Order "
                        + orderId + "."
        );

        return true;
    }
}

class PayPalPayment implements PaymentMethod {

    @Override
    public boolean processPayment(
            double amount,
            String orderId
    ) {
        System.out.println(
                "Payment initiated via PayPal for Order "
                        + orderId + "."
        );

        return false;
    }
}

class BankTransferPayment implements PaymentMethod {

    @Override
    public boolean processPayment(
            double amount,
            String orderId
    ) {
        System.out.println(
                "Payment initiated via Bank Transfer for Order "
                        + orderId + "."
        );

        return true;
    }
}

class Order {
    private String orderId;
    private Customer customer;
    private List<OrderItem> items;
    private OrderStatus status;

    public Order(
            String orderId,
            Customer customer
    ) {
        this.orderId = orderId;
        this.customer = customer;
        this.items = new ArrayList<>();
        this.status = OrderStatus.PENDING;
    }

    public String getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void addProduct(
            Product product,
            int quantity
    ) {
        if (product == null || quantity <= 0) {
            throw new IllegalArgumentException(
                    "Invalid product or quantity."
            );
        }

        items.add(
                new OrderItem(product, quantity)
        );
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public double calculateTotal() {
        double total = 0;

        for (OrderItem item : items) {
            total += item.getTotal();
        }

        return total;
    }

    public boolean processPayment(
            PaymentMethod paymentMethod
    ) {
        if (isEmpty()) {
            System.out.println(
                    "Cannot process payment for an empty order."
            );

            return false;
        }

        boolean successful =
                paymentMethod.processPayment(
                        calculateTotal(),
                        orderId
                );

        if (successful) {
            status = OrderStatus.PAID;

            System.out.println(
                    "Payment for Order "
                            + orderId
                            + " successful."
            );
        } else {
            System.out.println(
                    "Payment for Order "
                            + orderId
                            + " failed."
            );
        }

        System.out.println(
                "Order status: " + status
        );

        return successful;
    }
}

public class PaymentProcessingShoppingSystem {

    public static void main(String[] args) {

        Customer customerX =
                new Customer("Customer X");

        Customer customerY =
                new Customer("Customer Y");

        Customer customerZ =
                new Customer("Customer Z");

        Product productA =
                new Product("Product A", 50.0);

        Product productB =
                new Product("Product B", 30.0);

        Product productC =
                new Product("Product C", 75.0);

        Order orderX =
                new Order("X", customerX);

        System.out.println(
                "Order created for "
                        + customerX.getName()
                        + "."
        );

        orderX.addProduct(productA, 2);
        orderX.addProduct(productB, 1);

        orderX.processPayment(
                new CreditCardPayment()
        );

        Order orderY =
                new Order("Y", customerY);

        System.out.println(
                "Order created for "
                        + customerY.getName()
                        + "."
        );

        orderY.processPayment(
                new CreditCardPayment()
        );

        Order orderZ =
                new Order("Z", customerZ);

        System.out.println(
                "Order created for "
                        + customerZ.getName()
                        + "."
        );

        orderZ.addProduct(productC, 1);

        orderZ.processPayment(
                new PayPalPayment()
        );
    }
}
'@ | Set-Content "WEEK8\string\assigment_problems\PaymentProcessingShoppingSystem.java"