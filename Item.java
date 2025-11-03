public record Item(String name, double price) {

    public Item {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Item name cannot be null or empty");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("Item price must be positive");
        }
    }

    @Override
    public String toString() {
        return String.format("%s - €%.2f", name, price);
    }
}