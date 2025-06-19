public abstract class Product {
    private int id;
    private String name;

    public Product(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public abstract void displayDetails();

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
