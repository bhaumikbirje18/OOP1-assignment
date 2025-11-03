public final class DeliveryAgent extends User {
    private final String vehicleNo;
    private boolean available;

    public DeliveryAgent(String name, String phone, String vehicleNo) {
        super(name, phone);
        if (vehicleNo == null || vehicleNo.isBlank()) {
            throw new IllegalArgumentException("Vehicle number cannot be null or empty");
        }
        this.vehicleNo = vehicleNo;
        this.available = true; // New agents are available by default
    }

    public String vehicleNo() {
        return vehicleNo;
    }

    public boolean available() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void acceptOrder(Order order) {
        if (!available) {
            throw new IllegalStateException("Agent is not available");
        }
        System.out.println("Agent " + name() + " accepted order #" + order.id());
        this.available = false;
    }

    public void deliver(Order order) {
        if (order.status() != OrderStatus.DISPATCHED) {
            throw new IllegalStateException("Order must be dispatched before delivery");
        }
        System.out.println("Agent " + name() + " delivered order #" + order.id());
        this.available = true; // Agent becomes available after delivery
    }

    @Override
    public String getDetails() {
        return super.getDetails() + ", Vehicle: " + vehicleNo +
                ", Available: " + (available ? "Yes" : "No");
    }
}