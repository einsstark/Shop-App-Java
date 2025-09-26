package com.sam.shop;

import java.nio.file.Path;
import java.util.Scanner;

public class ShopApp {
    public static void main(String[] args) throws Exception {
        Inventory inv = new Inventory();
        inv.loadFromCSV(Path.of("data", "products.csv"));
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Shop App");
            while (true) {
                System.out.println("\n1) List  2) Buy  3) Sell  4) Reprice  5) Total value  0) Exit");
                System.out.print("> ");
                String raw = sc.nextLine().trim();
                if (raw.isEmpty()) continue;

                int choice;
                try {
                    choice = Integer.parseInt(raw);
                } catch (NumberFormatException e) {
                    System.out.println("Enter a number 0–5.");
                    continue;
                }

                try {
                    switch (choice) {
                        case 1 -> list(inv);
                        case 2 -> buy(inv, sc);
                        case 3 -> sell(inv, sc);
                        case 4 -> reprice(inv, sc);
                        case 5 -> System.out.printf("Total: £%.2f%n", inv.totalValue());
                        case 0 -> { System.out.println("Bye."); return; }
                        default -> System.out.println("Unknown option.");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }

    static void list(Inventory inv) {
        for (Product p : inv.all()) System.out.println(p);
    }

    static void buy(Inventory inv, Scanner sc) {
        int id = readInt(sc, "Product id: ");
        int q  = readInt(sc, "Quantity: ");
        Product p = inv.findById(id);
        if (p == null) { System.out.println("No such product."); return; }
        double cost = p.buyStock(q);
        System.out.printf("Bought %d of %s for £%.2f%n", q, p.getName(), cost);
    }

    static void sell(Inventory inv, Scanner sc) {
        int id = readInt(sc, "Product id: ");
        int q  = readInt(sc, "Quantity: ");
        Product p = inv.findById(id);
        if (p == null) { System.out.println("No such product."); return; }
        double rev = p.sellStock(q);
        System.out.printf("Sold %d of %s for £%.2f%n", q, p.getName(), rev);
    }

    static void reprice(Inventory inv, Scanner sc) {
        int id = readInt(sc, "Product id: ");
        double price = readDouble(sc, "New price: ");
        Product p = inv.findById(id);
        if (p == null) { System.out.println("No such product."); return; }
        p.reprice(price);
        System.out.printf("New price for %s is £%.2f%n", p.getName(), p.getPrice());
    }

    private static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try { return Integer.parseInt(s); }
            catch (NumberFormatException e) { System.out.println("Enter a valid integer."); }
        }
    }

    private static double readDouble(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try { return Double.parseDouble(s); }
            catch (NumberFormatException e) { System.out.println("Enter a valid number."); }
        }
    }
}
