public class InventoryItem extends Product implements Manageable {
    private int quantity;
    private String category;

    public InventoryItem(int id, String name, int quantity, String category) {
        super(id, name);
        this.quantity = quantity;
        this.category = category;
    }

    @Override
    public void displayDetails() {
        System.out.println("ID: " + getId() +
                " | Name: " + getName() +
                " | Qty: " + quantity +
                " | Category: " + category);
    }

    @Override
    public void updateQuantity(int delta) {
        synchronized (this) {
            this.quantity += delta;
            System.out.println("Quantity updated to: " + this.quantity);
        }
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }
}
