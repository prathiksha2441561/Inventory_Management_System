public class ItemThread extends Thread {
    private InventoryItem item;

    public ItemThread(InventoryItem item) {
        this.item = item;
    }

    @Override
    public void run() {
        System.out.println("Thread " + Thread.currentThread().getName() + " is updating item...");
        item.updateQuantity(1); // Simulate an increment
        item.displayDetails();
    }
}
