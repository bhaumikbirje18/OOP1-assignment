public sealed class User permits Customer, DeliveryAgent {
    private final String name;
    private final String phone;

    public User(String name, String phone) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Phone cannot be null or empty");
        }
        this.name = name;
        this.phone = phone;
    }

    public String name() {
        return name;
    }

    public String phone() {
        return phone;
    }

    public String getDetails() {
        return "User: " + name + ", Phone: " + phone;
    }

    @Override
    public String toString() {
        return getDetails();
    }
}