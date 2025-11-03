import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== Smart Food Delivery Tracker ===\n");
        demonstrateBasicFeatures();
        demonstrateAdvancedFeatures();
    }

    private static void demonstrateBasicFeatures() {
        System.out.println("\n--- DEMONSTRATING BASIC FEATURES ---\n");

        // 1. Classes and Objects
        System.out.println("1. Creating objects from classes:");
        var manager = new DeliveryManager();
        var menu = new Menu();

        // 2. Encapsulation - using getters
        menu.displayMenu();

        // 3. this() and this. - demonstrated in Customer class
        System.out.println("\n2. Creating Customer (demonstrates this() and this.):");
        var customer1 = new Customer("John Doe", "0871234567", "123 Main St, Dublin");
        var customer2 = new Customer("Jane Smith", "0879876543"); // Uses this() constructor chaining
        System.out.println(customer1.getDetails());
        System.out.println(customer2.getDetails());

        // 4. Inheritance and super() - User -> Customer, DeliveryAgent
        System.out.println("\n3. Creating DeliveryAgent (demonstrates inheritance and super()):");
        var agent1 = new DeliveryAgent("Mike Driver", "0861234567", "D-123-XY");
        var agent2 = new DeliveryAgent("Sarah Rider", "0869876543", "D-456-ZW");
        manager.addAgent(agent1);
        manager.addAgent(agent2);
        System.out.println(agent1.getDetails());
        System.out.println(agent2.getDetails());

        // 5. Polymorphism - User reference to Customer and DeliveryAgent
        System.out.println("\n4. Polymorphism (User type holding Customer and Agent):");
        User user1 = customer1;
        User user2 = agent1;
        System.out.println(user1.getDetails()); // Calls Customer's overridden method
        System.out.println(user2.getDetails()); // Calls DeliveryAgent's overridden method

        // 6. Arrays
        System.out.println("\n5. Arrays demonstration:");
        String[] statusNames = {"PLACED", "PREPARING", "DISPATCHED", "DELIVERED"};
        System.out.println("Order statuses: " + String.join(", ", statusNames));

        // 7. Enums
        System.out.println("\n6. Enums (OrderStatus):");
        for (OrderStatus status : OrderStatus.values()) {
            System.out.println(status + ": " + status.getDisplayMessage());
        }

        // 8. Records (Order and Item)
        System.out.println("\n7. Records (Item and Order):");
        var item1 = new Item("Pizza", 12.99);
        var item2 = new Item("Burger", 9.99);
        System.out.println("Item record: " + item1);
        System.out.println("Auto-generated equals: " + item1.equals(new Item("Pizza", 12.99)));

        // 9. LVTI (var keyword)
        System.out.println("\n8. LVTI (Local Variable Type Inference with 'var'):");
        var items = new ArrayList<Item>();
        items.add(menu.findItemByName("Margherita Pizza"));
        items.add(menu.findItemByName("Coca Cola"));
        var order = manager.createOrder(customer1, items);
        System.out.println("Created order using 'var': " + order.id());

        // 10. Varargs
        System.out.println("\n9. Varargs (adding multiple items at once):");
        var newMenu = new Menu();
        newMenu.addItems(
                new Item("French Fries", 4.99),
                new Item("Ice Cream", 5.99),
                new Item("Coffee", 3.50)
        );
        System.out.println("Added 3 items using varargs");

        // 11. Method Overloading
        System.out.println("\n10. Method Overloading:");
        manager.updateStatus(order); // Overloaded version without explicit status
        manager.updateStatus(order, OrderStatus.PREPARING); // Overloaded with status parameter

        // 12. Exceptions (checked and unchecked)
        System.out.println("\n11. Exception Handling:");
        try {
            var invalidItem = new Item("", -5.0); // Will throw IllegalArgumentException
        } catch (IllegalArgumentException e) {
            System.out.println("Caught exception: " + e.getMessage());
        }

        try {
            var emptyOrder = manager.createOrder(customer1, new ArrayList<>()); // Empty items
        } catch (IllegalArgumentException e) {
            System.out.println("Caught exception: " + e.getMessage());
        }

        // 13. Java Core API - String, StringBuilder, List, ArrayList
        System.out.println("\n12. Java Core API usage:");
        String customerName = customer1.name().toUpperCase();
        System.out.println("String API: " + customerName);

        StringBuilder summary = new StringBuilder();
        summary.append("Order Summary: ")
                .append(order.items().size())
                .append(" items, Total: €")
                .append(String.format("%.2f", order.totalPrice()));
        System.out.println("StringBuilder: " + summary);

        List<String> itemNames = order.items().stream()
                .map(Item::name)
                .toList();
        System.out.println("ArrayList/List: " + itemNames);

        // 14. Date API
        System.out.println("\n13. Date API (java.time):");
        System.out.println("Order time: " + order.getFormattedOrderTime());
        System.out.println("Estimated delivery: " + order.estimatedTimeMinutes() + " minutes");
    }

    private static void demonstrateAdvancedFeatures() {
        System.out.println("\n\n--- DEMONSTRATING ADVANCED FEATURES ---\n");

        var manager = new DeliveryManager();
        var menu = new Menu();
        var customer = new Customer("Alice Brown", "0851234567", "456 Oak Ave, Cork");
        var agent = new DeliveryAgent("Tom Wilson", "0862345678", "D-789-AB");
        manager.addAgent(agent);

        // 1. Records (immutability)
        System.out.println("1. Records (Order and Item are immutable records):");
        var pizza = new Item("Pizza", 12.99);
        System.out.println("Item record: " + pizza);
        System.out.println("Cannot modify: all fields are final");

        var items = List.of(pizza, new Item("Salad", 8.99));
        var order = manager.createOrder(customer, items);

        // 2. Custom Immutable Type (Order record with defensive copying)
        System.out.println("\n2. Custom Immutable Type (Order with defensive copying):");
        System.out.println("Order items list is defensively copied - external changes won't affect order");
        var originalItems = new ArrayList<Item>();
        originalItems.add(menu.findItemByName("Burger"));
        var order2 = manager.createOrder(customer, originalItems);
        originalItems.add(new Item("Extra item", 5.0)); // Doesn't affect order2
        System.out.println("Order2 items count: " + order2.items().size() + " (unchanged)");

        // 3. Sealed Classes
        System.out.println("\n3. Sealed Classes (User permits Customer, DeliveryAgent):");
        User user = customer;
        if (order instanceof Order(var id, var c, var itms, var price, var status, var time, var orderTime)) {
            System.out.println("Record pattern matching: Order #" + id + " for " + c.name() + ": €" + price);
        }

        // 4. Switch Expressions
        System.out.println("\n4. Switch Expressions:");
        String statusMessage = switch(order.status()) {
            case ORDER_PLACED -> "Order received";
            case PREPARING -> "Being prepared";
            case DISPATCHED -> "On the way";
            case DELIVERED -> "Delivered";
        };
        System.out.println("Switch expression result: " + statusMessage);

        // 5. Pattern Matching
        System.out.println("\n5. Pattern Matching:");
        Object obj = order;
        if (obj instanceof Order o) {
            System.out.println("Pattern matched Order with id: " + o.id());
        }

        // Record patterns
        System.out.println("\n6. Record Pattern Matching:");
        if (order instanceof Order(var id, var c, var itms, var price, var status, var time, var orderTime)) {
            System.out.println("Deconstructed order #" + id + " for " + c.name() + ": €" + price);
        }

        // 6. Lambdas and Predicates
        System.out.println("\n7. Lambdas and Predicates:");

        // Simple lambda
        items.forEach(item -> System.out.println("  - " + item.name()));

        // Lambda with Predicate
        var expensiveItems = menu.viewAllItems().stream()
                .filter(item -> item.price() > 10.0)
                .toList();
        System.out.println("Expensive items (lambda with filter): " + expensiveItems.size());

        // Effectively final demonstration
        double priceLimit = 15.0; // effectively final
        var affordableItems = menu.viewAllItems().stream()
                .filter(item -> item.price() < priceLimit) // Can use priceLimit (effectively final)
                .toList();
        System.out.println("Affordable items (< €" + priceLimit + "): " + affordableItems.size());

        // 7. Method References
        System.out.println("\n8. Method References:");
        System.out.println("Using method reference (Item::name):");
        menu.viewAllItems().stream()
                .map(Item::name)
                .limit(3)
                .forEach(System.out::println);

        // 8. Call-by-value and Defensive Copying
        System.out.println("\n9. Call-by-value and Defensive Copying:");
        List<Order> activeOrders = manager.viewActiveOrders();
        System.out.println("Active orders (defensive copy): " + activeOrders.size());
        activeOrders.clear(); // This won't affect manager's internal list
        System.out.println("After clearing returned list, manager still has: " + manager.viewActiveOrders().size() + " orders");

        // 9. Private, Default, and Static Interface Methods
        System.out.println("\n10. Interface Methods:");
        System.out.println("(Demonstrated in service layer design - DeliveryManager uses service pattern)");

        // 10. Final and Effectively Final
        System.out.println("\n11. Final and Effectively Final:");
        final String deliveryNote = "Handle with care";

        var count = 0;
        menu.viewAllItems().forEach(item -> {
            System.out.println("Processing item: " + item.name());
        });
        System.out.println("Count remains: " + count + " (effectively final allows use in lambda)");

        // Complete the order flow
        System.out.println("\n\n--- COMPLETE ORDER FLOW ---");
        manager.assignAgent(order);
        manager.updateStatus(order, OrderStatus.DISPATCHED);
        manager.completeDelivery(order, agent);
        System.out.println("\nActive orders: " + manager.viewActiveOrders().size());
        System.out.println("Completed orders: " + manager.viewCompletedOrders().size());
    }

    private static void runInteractiveDemo() {
        Scanner scanner = new Scanner(System.in);
        var manager = new DeliveryManager();
        var menu = new Menu();

        manager.addAgent(new DeliveryAgent("Mike", "0861234567", "D-123"));
        manager.addAgent(new DeliveryAgent("Sarah", "0869876543", "D-456"));
        System.out.println("\n=== Interactive Demo ===");

        while (true) {
            System.out.println("\n1. View Menu");
            System.out.println("2. Place Order");
            System.out.println("3. View Active Orders");
            System.out.println("4. View Agents");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch(choice) {
                case 1 -> menu.displayMenu();
                case 2 -> placeOrderInteractive(scanner, manager, menu);
                case 3 -> displayActiveOrders(manager);
                case 4 -> displayAgents(manager);
                case 5 -> {
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid option");
            }
        }
    }

    private static void placeOrderInteractive(Scanner scanner, DeliveryManager manager, Menu menu) {
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        var customer = new Customer(name, phone, address);
        var items = new ArrayList<Item>();

        menu.displayMenu();
        System.out.println("Enter item names (type 'done' to finish):");

        while (true) {
            String itemName = scanner.nextLine();
            if (itemName.equalsIgnoreCase("done")) break;

            var item = menu.findItemByName(itemName);
            if (item != null) {
                items.add(item);
                System.out.println("Added: " + item);
            } else {
                System.out.println("Item not found");
            }
        }

        if (!items.isEmpty()) {
            var order = manager.createOrder(customer, items);
            manager.assignAgent(order);
        } else {
            System.out.println("No items selected");
        }
    }

    private static void displayActiveOrders(DeliveryManager manager) {
        var orders = manager.viewActiveOrders();
        if (orders.isEmpty()) {
            System.out.println("No active orders");
        } else {
            System.out.println("\n=== Active Orders ===");
            orders.forEach(System.out::println);
        }
    }

    private static void displayAgents(DeliveryManager manager) {
        var agents = manager.viewAgents();
        if (agents.isEmpty()) {
            System.out.println("No agents available");
        } else {
            System.out.println("\n=== Delivery Agents ===");
            agents.forEach(agent -> System.out.println(agent.getDetails()));
        }
    }
}