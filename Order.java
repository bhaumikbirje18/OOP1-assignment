import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public record Order(
        int id,
        Customer customer,
        List<Item> items,
        double totalPrice,
        OrderStatus status,
        int estimatedTimeMinutes,
        LocalDateTime orderTime
) {

    public Order {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item");
        }
        if (totalPrice <= 0) {
            throw new IllegalArgumentException("Total price must be positive");
        }
        if (estimatedTimeMinutes <= 0) {
            throw new IllegalArgumentException("Estimated time must be positive");
        }

        items = List.copyOf(items);

        if (orderTime == null) {
            orderTime = LocalDateTime.now();
        }
    }

    public Order(int id, Customer customer, List<Item> items, double totalPrice, OrderStatus status) {
        this(id, customer, items, totalPrice, status, 30, LocalDateTime.now());
    }

    public Order withStatus(OrderStatus newStatus) {
        return new Order(this.id, this.customer, this.items, this.totalPrice,
                newStatus, this.estimatedTimeMinutes, this.orderTime);
    }

    public Order withEstimatedTime(int minutes) {
        return new Order(this.id, this.customer, this.items, this.totalPrice,
                this.status, minutes, this.orderTime);
    }

    public String getFormattedOrderTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return orderTime.format(formatter);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=== Order #").append(id).append(" ===\n");
        sb.append("Customer: ").append(customer.name()).append("\n");
        sb.append("Address: ").append(customer.address()).append("\n");
        sb.append("Status: ").append(status).append("\n");
        sb.append("Order Time: ").append(getFormattedOrderTime()).append("\n");
        sb.append("Estimated Delivery: ").append(estimatedTimeMinutes).append(" minutes\n");
        sb.append("Items:\n");
        for (Item item : items) {
            sb.append("  - ").append(item).append("\n");
        }
        sb.append("Total: €").append(String.format("%.2f", totalPrice)).append("\n");
        return sb.toString();
    }
}