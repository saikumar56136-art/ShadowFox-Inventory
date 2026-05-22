# ShadowFox Inventory Management System

Inventory Management System built with Java as part
of ShadowFox Java Internship - Part 2 Task 2

## Features
- Add products with ID, Name, Quantity, Price
- View all products in table
- Update product details
- Delete product with confirmation
- Search by product name
- Find by product ID
- Low stock alert (red row when quantity < 5)
- Total inventory value auto-updating
- Double-click row to edit

## How to Run
1. Open in IntelliJ IDEA
2. Run `InventoryGUI.java`

## Technologies Used
- Java 25
- Swing GUI
- JTable with DefaultTableModel
- BigDecimal for price calculations
- HashMap for O(1) lookups
- ArrayList for search results

## Project Structure
src/
├── Product.java           → Data model
├── InventoryManager.java  → Business logic
└── InventoryGUI.java      → Swing GUI

## Author
Sai Kumar - ShadowFox Java Internship 2026