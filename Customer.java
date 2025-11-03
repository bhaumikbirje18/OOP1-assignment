import java.util.List;

public final class Customer extends User {
    private final String address;

    public Customer(String name, String phone, String address) {
        super(name, phone);
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Address cannot be null or empty");
        }
        this.address = address;
    }

    public Customer(String name, String phone) {
        this(name, phone, "Address not provided");
    }

    public String address() {
        return address;
    }

    public Order placeOrder(List<Item> items, String deliveryAddress) {

        if (deliveryAddress == null || deliveryAddress.isBlank()) {
            deliveryAddress = this.address;
        }

        double total = items.stream()
                .mapToDouble(Item::price)
                .sum();

        int estimatedTime = items.size() > 10 ? 60 : items.size() > 5 ? 45 : 30;

        return new Order(
                (int)(Math.random() * 10000),
                this,
                items,
                total,
                OrderStatus.ORDER_PLACED,
                estimatedTime,
                java.time.LocalDateTime.now()
        );
    }

    @Override
    public String getDetails() {
        return super.getDetails() + ", Address: " + address;
    }
}