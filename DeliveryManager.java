import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class DeliveryManager {
    private final List<Order> activeOrders;
    private final List<Order> completedOrders;
    private final List<DeliveryAgent> agents;
    private int nextOrderId;

    public DeliveryManager() {
        this.activeOrders = new ArrayList<>();
        this.completedOrders = new ArrayList<>();
        this.agents = new ArrayList<>();
        this.nextOrderId = 1000;
    }

    public void addAgent(DeliveryAgent agent) {
        if (agent == null) {
            throw new IllegalArgumentException("Agent cannot be null");
        }
        agents.add(agent);
    }

    public Order createOrder(Customer customer, List<Item> items) {
        var order = customer.placeOrder(items, customer.address());

        var newOrder = new Order(
                nextOrderId++,
                order.customer(),
                order.items(),
                order.totalPrice(),
                OrderStatus.ORDER_PLACED,
                order.estimatedTimeMinutes(),
                order.orderTime()
        );

        activeOrders.add(newOrder);
        System.out.println("Order created: " + newOrder);
        return newOrder;
    }

    public Order updateStatus(Order order, OrderStatus newStatus) {
        var updatedOrder = order.withStatus(newStatus);

        int index = activeOrders.indexOf(order);
        if (index != -1) {
            activeOrders.set(index, updatedOrder);
        }

        System.out.println("Order #" + order.id() + " status updated to: " + newStatus);
        System.out.println(newStatus.getDisplayMessage());

        return updatedOrder;
    }

    public Order updateStatus(Order order) {
        OrderStatus nextStatus = switch(order.status()) {
            case ORDER_PLACED -> OrderStatus.PREPARING;
            case PREPARING -> OrderStatus.DISPATCHED;
            case DISPATCHED -> OrderStatus.DELIVERED;
            case DELIVERED -> OrderStatus.DELIVERED; // Already delivered
        };
        return updateStatus(order, nextStatus);
    }

    public void assignAgent(Order order) {
        Optional<DeliveryAgent> availableAgent = agents.stream()
                .filter(DeliveryAgent::available)
                .findFirst();

        if (availableAgent.isPresent()) {
            var agent = availableAgent.get();
            agent.acceptOrder(order);
            var updatedOrder = updateStatus(order, OrderStatus.PREPARING);
            System.out.println("Agent " + agent.name() + " assigned to order #" + order.id());
        } else {
            System.out.println("No available agents at the moment");
        }
    }

    public void completeDelivery(Order order, DeliveryAgent agent) {
        var deliveredOrder = updateStatus(order, OrderStatus.DELIVERED);
        agent.deliver(deliveredOrder);

        activeOrders.remove(order);
        completedOrders.add(deliveredOrder);

        sendBill(deliveredOrder);
    }

    public void sendBill(Order order) {
        StringBuilder bill = new StringBuilder();
        bill.append("\n========== BILL ==========\n");
        bill.append("Order #").append(order.id()).append("\n");
        bill.append("Customer: ").append(order.customer().name()).append("\n");
        bill.append("Phone: ").append(order.customer().phone()).append("\n");
        bill.append("Delivery Address: ").append(order.customer().address()).append("\n");
        bill.append("Order Time: ").append(order.getFormattedOrderTime()).append("\n");
        bill.append("\nItems:\n");

        for (Item item : order.items()) {
            bill.append(String.format("  %-20s €%.2f%n", item.name(), item.price()));
        }

        bill.append("-------------------------\n");
        bill.append(String.format("Total: €%.2f%n", order.totalPrice()));
        bill.append("==========================\n");

        System.out.println(bill.toString());
    }

    public List<Order> viewActiveOrders() {
        return new ArrayList<>(activeOrders);
    }

    public List<Order> viewCompletedOrders() {
        return new ArrayList<>(completedOrders);
    }

    public List<Order> filterOrders(Predicate<Order> condition) {
        return activeOrders.stream()
                .filter(condition)
                .toList();
    }

    public void displayOrdersByStatus(OrderStatus status) {
        // status parameter is effectively final - can be used in lambda
        var filteredOrders = activeOrders.stream()
                .filter(order -> order.status() == status)
                .toList();

        System.out.println("\n=== Orders with status: " + status + " ===");
        filteredOrders.forEach(System.out::println); // Method reference
    }

    public List<DeliveryAgent> viewAgents() {
        return new ArrayList<>(agents);
    }
}
