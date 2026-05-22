package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.math.BigDecimal;

public class InventoryGUI extends JFrame {

    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField idField, nameField,
            quantityField, priceField, searchField;
    private InventoryManager manager = new InventoryManager();
    private JLabel totalValueLabel;

    public InventoryGUI() {
        setTitle("ShadowFox Inventory Manager");
        setSize(750, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table
        String[] columns = {"ID", "Name", "Quantity",
                "Price", "Total Value"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setBackground(
                Color.decode("#2196F3"));
        table.getTableHeader().setForeground(Color.WHITE);

        // Low stock highlighting
        table.setDefaultRenderer(Object.class,
                new DefaultTableCellRenderer() {
                    public Component getTableCellRendererComponent(
                            JTable table, Object value, boolean isSelected,
                            boolean hasFocus, int row, int column) {
                        Component c = super.getTableCellRendererComponent(
                                table, value, isSelected, hasFocus, row, column);
                        String qty = tableModel.getValueAt(
                                row, 2).toString();
                        if (Integer.parseInt(qty) < 5) {
                            c.setBackground(Color.decode("#ffcccc"));
                            c.setForeground(Color.RED);
                        } else {
                            c.setBackground(isSelected ?
                                    Color.decode("#2196F3") : Color.WHITE);
                            c.setForeground(isSelected ?
                                    Color.WHITE : Color.BLACK);
                        }
                        return c;
                    }
                });

        JScrollPane scrollPane = new JScrollPane(table);

        // Double click to load
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    if (row != -1) {
                        idField.setText(
                                tableModel.getValueAt(row, 0).toString());
                        nameField.setText(
                                tableModel.getValueAt(row, 1).toString());
                        quantityField.setText(
                                tableModel.getValueAt(row, 2).toString());
                        priceField.setText(
                                tableModel.getValueAt(row, 3).toString());
                    }
                }
            }
        });

        // Search bar
        searchField = new JTextField(15);
        JButton searchBtn = new JButton("🔍 Search");
        JButton barcodeBtn = new JButton("📦 Find by ID");
        searchBtn.setBackground(Color.decode("#FF9800"));
        searchBtn.setForeground(Color.WHITE);
        barcodeBtn.setBackground(Color.decode("#9C27B0"));
        barcodeBtn.setForeground(Color.WHITE);
        searchBtn.addActionListener(e -> searchByName());
        barcodeBtn.addActionListener(e -> searchById());

        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(Color.decode("#f5f5f5"));
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        searchPanel.add(barcodeBtn);

        // Form
        idField = new JTextField(10);
        nameField = new JTextField(10);
        quantityField = new JTextField(10);
        priceField = new JTextField(10);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder(
                "Product Details"));
        formPanel.add(new JLabel("Product ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Quantity:"));
        formPanel.add(quantityField);
        formPanel.add(new JLabel("Price (₹):"));
        formPanel.add(priceField);

        // Total value label
        totalValueLabel = new JLabel("Total Inventory Value: ₹0");
        totalValueLabel.setFont(new Font("Arial", Font.BOLD, 14));
        totalValueLabel.setForeground(Color.decode("#4CAF50"));

        // Buttons
        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        JButton clearBtn = new JButton("Clear");
        JButton exportBtn = new JButton("📤 Export CSV");
        JButton importBtn = new JButton("📥 Import CSV");

        addBtn.setBackground(Color.decode("#4CAF50"));
        addBtn.setForeground(Color.WHITE);
        updateBtn.setBackground(Color.decode("#2196F3"));
        updateBtn.setForeground(Color.WHITE);
        deleteBtn.setBackground(Color.decode("#f44336"));
        deleteBtn.setForeground(Color.WHITE);
        clearBtn.setBackground(Color.decode("#9E9E9E"));
        clearBtn.setForeground(Color.WHITE);
        exportBtn.setBackground(Color.decode("#009688"));
        exportBtn.setForeground(Color.WHITE);
        importBtn.setBackground(Color.decode("#FF5722"));
        importBtn.setForeground(Color.WHITE);

        addBtn.addActionListener(e -> addProduct());
        updateBtn.addActionListener(e -> updateProduct());
        deleteBtn.addActionListener(e -> deleteProduct());
        clearBtn.addActionListener(e -> clearFields());
        exportBtn.addActionListener(e -> exportCSV());
        importBtn.addActionListener(e -> importCSV());

        JPanel btnPanel = new JPanel();
        btnPanel.add(addBtn);
        btnPanel.add(updateBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(clearBtn);
        btnPanel.add(exportBtn);
        btnPanel.add(importBtn);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(formPanel, BorderLayout.CENTER);
        bottomPanel.add(btnPanel, BorderLayout.SOUTH);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(searchPanel, BorderLayout.CENTER);
        topPanel.add(totalValueLabel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void addProduct() {
        try {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            int qty = Integer.parseInt(
                    quantityField.getText().trim());
            BigDecimal price = new BigDecimal(
                    priceField.getText().trim());

            boolean added = manager.addProduct(
                    id, name, qty, price);
            if (added) {
                tableModel.addRow(new Object[]{
                        id, name, qty, price,
                        price.multiply(BigDecimal.valueOf(qty))});
                updateTotalValue();
                clearFields();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "❌ Invalid quantity or price!");
        }
    }

    private void updateProduct() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a product to update!");
            return;
        }
        try {
            String id = tableModel.getValueAt(row, 0).toString();
            String name = nameField.getText().trim();
            int qty = Integer.parseInt(
                    quantityField.getText().trim());
            BigDecimal price = new BigDecimal(
                    priceField.getText().trim());

            boolean updated = manager.updateProduct(
                    id, name, qty, price);
            if (updated) {
                tableModel.setValueAt(name, row, 1);
                tableModel.setValueAt(qty, row, 2);
                tableModel.setValueAt(price, row, 3);
                tableModel.setValueAt(
                        price.multiply(BigDecimal.valueOf(qty)),
                        row, 4);
                updateTotalValue();
                clearFields();
                JOptionPane.showMessageDialog(this,
                        "✅ Product updated!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "❌ Invalid quantity or price!");
        }
    }

    private void deleteProduct() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a product to delete!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this product?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String id = tableModel.getValueAt(row, 0).toString();
            manager.deleteProduct(id);
            tableModel.removeRow(row);
            updateTotalValue();
            clearFields();
        }
    }

    private void exportCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(
                new java.io.File("inventory.csv"));
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser
                    .getSelectedFile().getAbsolutePath();
            boolean exported = manager.exportToCSV(filename);
            if (exported) {
                JOptionPane.showMessageDialog(this,
                        "✅ Inventory exported to:\n" + filename);
            } else {
                JOptionPane.showMessageDialog(this,
                        "❌ Export failed!");
            }
        }
    }

    private void importCSV() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser
                    .getSelectedFile().getAbsolutePath();
            int count = manager.importFromCSV(filename);
            if (count > 0) {
                refreshTable();
                JOptionPane.showMessageDialog(this,
                        "✅ Imported " + count + " products!");
            } else {
                JOptionPane.showMessageDialog(this,
                        "❌ No products imported!");
            }
        }
    }

    private void searchByName() {
        String keyword = searchField.getText().trim();
        tableModel.setRowCount(0);
        for (Product p : manager.searchByName(keyword)) {
            tableModel.addRow(new Object[]{
                    p.getId(), p.getName(), p.getQuantity(),
                    p.getPrice(), p.getTotalValue()});
        }
        if (keyword.isEmpty()) refreshTable();
    }

    private void searchById() {
        String id = searchField.getText().trim();
        Product p = manager.searchById(id);
        if (p != null) {
            tableModel.setRowCount(0);
            tableModel.addRow(new Object[]{
                    p.getId(), p.getName(), p.getQuantity(),
                    p.getPrice(), p.getTotalValue()});
        } else {
            JOptionPane.showMessageDialog(this,
                    "❌ Product not found!");
        }
    }

    private void updateTotalValue() {
        totalValueLabel.setText(
                "Total Inventory Value: ₹" + manager.getTotalValue());
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Product p : manager.getInventory().values()) {
            tableModel.addRow(new Object[]{
                    p.getId(), p.getName(), p.getQuantity(),
                    p.getPrice(), p.getTotalValue()});
        }
        updateTotalValue();
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        quantityField.setText("");
        priceField.setText("");
        searchField.setText("");
        refreshTable();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InventoryGUI::new);
    }
}