# Learnings - Inventory Management System

## Hardest Bug
Low stock highlighting was not working correctly
because the cell renderer was reading wrong column.

## How I Fixed It
Used `tableModel.getValueAt(row, 2)` to correctly
read the quantity column (index 2) and check
if it is less than 5 for red highlighting.

## What I Learned
- DefaultTableCellRenderer for row highlighting
- Custom cell renderer with color conditions
- BigDecimal multiply for total value calculation
- HashMap for fast product lookups
- Double-click mouse listener on JTable
- Auto-updating labels with inventory total
- Search filtering with ArrayList
- Confirmation dialog before delete
- ## Tier 2 CSV Export/Import Learnings

### What I Learned
- FileWriter to write CSV files
- BufferedReader to read CSV files
- String.split(",") to parse CSV rows
- JFileChooser for file save/open dialog
- Skipping header row with reader.readLine()
- Data backup and restore concept