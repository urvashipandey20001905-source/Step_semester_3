import java.util.*;

class Customer {
    private final String name;
    public Customer(String name) { this.name = name; }
    public String getName() { return name; }
}

class Product {
    private final String name;
    private final double price;

    public Product(String name, double price) {
        if (price < 0) throw new IllegalArgumentException("Price cannot be negative");
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
}

class OrderItem {
    private final Product product;
    private final int quantity;

    public OrderItem(Product product, int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
        this.product = product;
        this.quantity = quantity;
    }

    public double subtotal() { return product.getPrice() * quantity; }
}

interface PaymentMethod {
    String getName();
    boolean processPayment(double amount);
}

class CreditCardPayment implements PaymentMethod {
    @Override public String getName() { return "Credit Card"; }
    @Override public boolean processPayment(double amount) { return true; }
}

class PayPalPayment implements PaymentMethod {
    private final boolean succeeds;

    public PayPalPayment(boolean succeeds) { this.succeeds = succeeds; }
    @Override public String getName() { return "PayPal"; }
    @Override public boolean processPayment(double amount) { return succeeds; }
}

class BankTransferPayment implements PaymentMethod {
    @Override public String getName() { return "Bank Transfer"; }
    @Override public boolean processPayment(double amount) { return true; }
}

class Order {
    private final String id;
    private final Customer customer;
    private final List<OrderItem> items = new ArrayList<>();
    private String status = "Pending";

    public Order(String id, Customer customer) {
        this.id = id;
        this.customer = customer;
        System.out.println("Order created for " + customer.getName() + ".");
    }

    public void addProduct(Product product, int quantity) {
        if (!"Pending".equals(status)) {
            throw new IllegalStateException("Cannot modify a paid order");
        }
        items.add(new OrderItem(product, quantity));
    }

    private double total() {
        return items.stream().mapToDouble(OrderItem::subtotal).sum();
    }

    public boolean pay(PaymentMethod method) {
        if (items.isEmpty()) {
            System.out.println("Cannot process payment for an empty order.");
            return false;
        }
        if ("Paid".equals(status)) {
            System.out.println("Order is already paid.");
            return false;
        }

        System.out.println("Payment initiated via " + method.getName()
                + " for Order " + id + ".");

        if (method.processPayment(total())) {
            status = "Paid";
            System.out.println("Payment for Order " + id + " successful.");
        } else {
            System.out.println("Payment for Order " + id + " failed.");
        }
        System.out.println("Order status: " + status + ".");
        return "Paid".equals(status);
    }
}

public class Main {
    public static void main(String[] args) {
        Customer customerX = new Customer("Customer X");
        Order orderX = new Order("X", customerX);
        orderX.addProduct(new Product("Product A", 20.0), 2);
        orderX.addProduct(new Product("Product B", 15.0), 1);
        orderX.pay(new CreditCardPayment());

        Order emptyOrder = new Order("Y", new Customer("Customer Y"));
        emptyOrder.pay(new CreditCardPayment());

        Order orderZ = new Order("Z", new Customer("Customer Z"));
        orderZ.addProduct(new Product("Product C", 30.0), 1);
        orderZ.pay(new PayPalPayment(false));
    }
}