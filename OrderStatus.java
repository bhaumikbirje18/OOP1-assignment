public enum OrderStatus {
    ORDER_PLACED,
    PREPARING,
    DISPATCHED,
    DELIVERED;

    public String getDisplayMessage() {
        return switch(this) {
            case ORDER_PLACED -> "Your order has been received";
            case PREPARING -> "Your order is being prepared";
            case DISPATCHED -> "Your order is on the way";
            case DELIVERED -> "Your order has been delivered";
        };
    }
}