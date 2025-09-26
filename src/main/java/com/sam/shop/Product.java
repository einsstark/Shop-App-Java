package com.sam.shop;

public class Product {
    private final int id;
    private String name;
    private int stock;
    private double price;

    public Product(int id, String name, int stock, double price) {
        if (id <= 0) throw new IllegalArgumentException("id must be positive");
        if (stock < 0) throw new IllegalArgumentException("stock cannot be negative");
        if (price < 0) throw new IllegalArgumentException("price cannot be negative");
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getStock() { return stock; }
    public double getPrice() { return price; }

    public double buyStock(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("quantity must be > 0");
        stock += quantity;
        return quantity * price;
    }

    public double sellStock(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("quantity must be > 0");
        if (quantity > stock) throw new IllegalArgumentException("not enough stock");
        stock -= quantity;
        return quantity * price;
    }

    public void reprice(double newPrice) {
        if (newPrice < 0) throw new IllegalArgumentException("price cannot be negative");
        this.price = newPrice;
    }

    @Override
    public String toString() {
        return String.format("%d | %-12s | stock=%d | £%.2f", id, name, stock, price);
    }
}
