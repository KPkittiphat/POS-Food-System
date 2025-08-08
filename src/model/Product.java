package model;

public class Product {
    private String id;
    private String name;
    private double price;
    private String category;
    private String imagePath;

    public Product(String id, String name, double price, String category, String imagePath) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.imagePath = imagePath;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getImagePath() {
        return imagePath;
    }
}