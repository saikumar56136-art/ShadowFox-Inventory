# ShadowFox Inventory Management System

Inventory Management System built with Java as part
of ShadowFox Java Internship - Part 2 Task 2
(All Tiers Completed)

## Features

### Baseline
- Add products with ID, Name, Quantity, Price
- Update product details
- Delete product with confirmation
- Search products by name
- Low stock alert (quantity < 5)

### Tier 1 - GUI with JTable
- Swing GUI with JTable
- Color coded rows (red = low stock)
- Total inventory value auto-updating
- Double-click row to edit
- Find product by ID (barcode simulation)
- Search and filter products

### Tier 2 - CSV Export/Import
- Export all products to CSV file
- Import products from CSV file
- JFileChooser for file selection
- Data backup and restore

## How to Run
1. Open in IntelliJ IDEA
2. Run `InventoryGUI.java`

## How to Export
1. Add some products
2. Click 📤 Export CSV button
3. Choose save location
4. Click Save

## How to Import
1. Click 📥 Import CSV button
2. Select your CSV file
3. Products will be loaded automatically

## Technologies Used
- Java 25
- Swing GUI
- JTable with DefaultTableModel
- DefaultTableCellRenderer for highlighting
- BigDecimal for price calculations
- HashMap for O(1) lookups
- FileWriter for CSV export
- BufferedReader for CSV import
- JFileChooser for file dialogs

## Project Structure
src/
├── Product.java            → Data model
├── InventoryManager.java   → Business logic
└── InventoryGUI.java       → Swing GUI
## Author
Sai Kumar - ShadowFox Java Internship 2026