package org.example;

import java.math.BigDecimal;

public class Product {
    private String id;
    private String name;
    private int quantity;
    private BigDecimal price;

    public Product(String id, String name,
                   int quantity, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public BigDecimal getPrice() { return price; }

    public void setName(String name) { this.name = name; }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotalValue() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public String toString() {
        return "ID: " + id +
                " | Name: " + name +
                " | Qty: " + quantity +
                " | Price: ₹" + price +
                " | Total: ₹" + getTotalValue();
    }
}