import java.util.*;

interface NotificationChannel {
    String name();
    void send(Student student, Notice notice);
}

class EmailChannel implements NotificationChannel {
    public String name() { return "Email"; }

    public void send(Student student, Notice notice) {
        System.out.println("[Email → " + student.getName() + "] "
                + notice.getTitle());
    }
}

class SmsChannel implements NotificationChannel {
    public String name() { return "SMS"; }

    public void send(Student student, Notice notice) {
        System.out.println("[SMS → " + student.getName() + "] "
                + notice.getTitle());
    }
}

class AppChannel implements NotificationChannel {
    public String name() { return "App"; }

    public void send(Student student, Notice notice) {
        System.out.println("[App → " + student.getName() + "] "
                + notice.getTitle());
    }
}

class Student {
    private final String name;
    private final String department;
    private final List<NotificationChannel> channels;

    Student(String name, String department, List<NotificationChannel> channels) {
        this.name = name;
        this.department = department;
        this.channels = new ArrayList<>(channels);
    }

    String getName() { return name; }
    String getDepartment() { return department; }

    List<NotificationChannel> getChannels() {
        return Collections.unmodifiableList(channels);
    }
}

class Notice {
    private final String title;
    private final Set<String> targetDepartments;

    Notice(String title, Set<String> targetDepartments) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Notice title is required");
        }
        if (targetDepartments == null || targetDepartments.isEmpty()) {
            throw new IllegalArgumentException(
                    "At least one target department is required");
        }

        this.title = title;
        this.targetDepartments = new LinkedHashSet<>(targetDepartments);
    }

    String getTitle() { return title; }

    Set<String> getTargetDepartments() {
        return Collections.unmodifiableSet(targetDepartments);
    }
}

class NoticeBoard {
    private final List<Student> students = new ArrayList<>();

    void addStudent(Student student) {
        students.add(student);
    }

    void postNotice(Notice notice) {
        System.out.println("Notice '" + notice.getTitle() + "' posted to "
                + String.join(", ", notice.getTargetDepartments()) + ".");

        for (Student student : students) {
            if (notice.getTargetDepartments().contains(student.getDepartment())) {
                for (NotificationChannel channel : student.getChannels()) {
                    channel.send(student, notice);
                }
            }
        }
    }
}

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

    public double subtotal() {
        return product.getPrice() * quantity;
    }
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
    private final List<OrderItem> items = new ArrayList<>();
    private String status = "Pending";

    public Order(String id, Customer customer) {
        this.id = id;
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
        // Notice example
        NoticeBoard board = new NoticeBoard();
        board.addStudent(new Student("Asha", "CSE",
                Arrays.asList(new EmailChannel(), new AppChannel())));
        board.addStudent(new Student("Ravi", "ECE",
                Arrays.asList(new SmsChannel())));

        board.postNotice(new Notice("Lab Closed Tomorrow",
                new LinkedHashSet<>(Arrays.asList("CSE"))));
        board.postNotice(new Notice("Fee Deadline Extended",
                new LinkedHashSet<>(Arrays.asList("CSE", "ECE"))));

        try {
            board.postNotice(new Notice("Sports Day", Collections.emptySet()));
        } catch (IllegalArgumentException e) {
            System.out.println("Cannot post notice: " + e.getMessage() + ".");
        }

        System.out.println();

        // Payment example
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