import java.util.ArrayList;
import java.util.List;

public class Menu {
    private final List<Item> items;

    public Menu() {
        this.items = new ArrayList<>();
        initializeDefaultMenu();
    }

    private void initializeDefaultMenu() {
        addItem(new Item("Margherita Pizza", 12.99));
        addItem(new Item("Pepperoni Pizza", 14.99));
        addItem(new Item("Burger", 9.99));
        addItem(new Item("Pasta Carbonara", 11.99));
        addItem(new Item("Caesar Salad", 8.99));
        addItem(new Item("Coca Cola", 2.50));
        addItem(new Item("Water", 1.50));
    }

    public void addItems(Item... newItems) {
        if (newItems == null) {
            throw new IllegalArgumentException("Items cannot be null");
        }
        for (Item item : newItems) {
            addItem(item);
        }
    }

    public void addItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public Item findItemByName(String name) {
        return items.stream()
                .filter(item -> item.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public List<Item> findItemsByPriceRange(double minPrice, double maxPrice) {
        return items.stream()
                .filter(item -> item.price() >= minPrice && item.price() <= maxPrice)
                .toList();
    }

    public List<Item> viewAllItems() {
        return List.copyOf(items);
    }

    public void displayMenu() {
        System.out.println("\n=== MENU ===");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i));
        }
        System.out.println();
    }
}