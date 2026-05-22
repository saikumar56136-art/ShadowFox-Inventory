package org.example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public class InventoryManager {
    private HashMap<String, Product> inventory = new HashMap<>();

    // Add product
    public boolean addProduct(String id, String name,
                              int quantity, BigDecimal price) {
        if (inventory.containsKey(id)) {
            System.out.println("❌ Product ID already exists!");
            return false;
        }
        if (quantity < 0) {
            System.out.println("❌ Quantity cannot be negative!");
            return false;
        }
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            System.out.println("❌ Price cannot be negative!");
            return false;
        }
        inventory.put(id, new Product(id, name, quantity, price));
        System.out.println("✅ Product added!");
        return true;
    }

    // View all products
    public void viewProducts() {
        if (inventory.isEmpty()) {
            System.out.println("❌ No products found!");
            return;
        }
        System.out.println("\n===== Inventory =====");
        for (Product p : inventory.values()) {
            System.out.println(p);
            if (p.getQuantity() < 5) {
                System.out.println("   ⚠️ LOW STOCK ALERT!");
            }
        }
        System.out.println("Total Value: ₹" + getTotalValue());
    }

    // Update product
    public boolean updateProduct(String id, String name,
                                 int quantity, BigDecimal price) {
        Product p = inventory.get(id);
        if (p == null) {
            System.out.println("❌ Product not found!");
            return false;
        }
        if (quantity < 0) {
            System.out.println("❌ Quantity cannot be negative!");
            return false;
        }
        p.setName(name);
        p.setQuantity(quantity);
        p.setPrice(price);
        System.out.println("✅ Product updated!");
        return true;
    }

    // Delete product
    public boolean deleteProduct(String id) {
        if (!inventory.containsKey(id)) {
            System.out.println("❌ Product not found!");
            return false;
        }
        inventory.remove(id);
        System.out.println("✅ Product deleted!");
        return true;
    }

    // Search product by barcode/id
    public Product searchById(String id) {
        return inventory.get(id);
    }

    // Search by name
    public ArrayList<Product> searchByName(String keyword) {
        ArrayList<Product> results = new ArrayList<>();
        for (Product p : inventory.values()) {
            if (p.getName().toLowerCase()
                    .contains(keyword.toLowerCase())) {
                results.add(p);
            }
        }
        return results;
    }

    // Get total inventory value
    public BigDecimal getTotalValue() {
        BigDecimal total = BigDecimal.ZERO;
        for (Product p : inventory.values()) {
            total = total.add(p.getTotalValue());
        }
        return total;
    }

    // Get all products
    public HashMap<String, Product> getInventory() {
        return inventory;
    }
}