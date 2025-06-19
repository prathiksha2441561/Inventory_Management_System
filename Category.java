public final class Category {
    private String name;

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void showCategory() {
        System.out.println("Category: " + name);
    }
}
