package com.sam.shop;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Inventory {
    private final List<Product> products = new ArrayList<>();
    // O(1) lookups by id
    private final Map<Integer, Product> indexById = new HashMap<>();

    public void add(Product p) {
        products.add(p);
        indexById.put(p.getId(), p);
    }

    public Product findById(int id) {
        Product p = indexById.get(id);
        if (p != null) return p;
        for (Product prod : products) {
            if (prod.getId() == id) return prod;
        }
        return null;
    }

    public List<Product> all() {
        return Collections.unmodifiableList(products);
    }

    public double totalValue() {
        double total = 0.0;
        for (Product p : products) {
            total += p.getStock() * p.getPrice();
        }
        return total;
    }

    // CSV columns: id,name,stock,price
    public void loadFromCSV(Path path) throws IOException {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] parts = line.split(",");
                if (parts.length < 4) continue; // skip bad rows
                try {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    int stock = Integer.parseInt(parts[2].trim());
                    double price = Double.parseDouble(parts[3].trim());
                    add(new Product(id, name, stock, price));
                } catch (NumberFormatException ignore) {
                    // skip malformed numeric fields
                }
            }
        }
    }
}
