package ui;

import model.Product;
import service.CartService;
import service.InventoryService;
import ui.components.ProductCard;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProductPanel extends JPanel {
    private final InventoryService inventoryService;
    private final CartService cartService;
    private final MainPanel mainPanel;
    private JPanel productGridPanel;

    public ProductPanel(InventoryService inventoryService, CartService cartService, MainPanel mainPanel) {
        this.inventoryService = inventoryService;
        this.cartService = cartService;
        this.mainPanel = mainPanel;

        setLayout(new BorderLayout());

        productGridPanel = new JPanel();

        productGridPanel.setLayout(new GridLayout(0, 3, 20, 20));
        productGridPanel.setBackground(Color.WHITE);

        productGridPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(productGridPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void displayProducts(String category) {
        productGridPanel.removeAll();
        List<Product> products = inventoryService.getProductsByCategory(category);

        for (Product product : products) {
            ProductCard card = new ProductCard(product, mainPanel);
            productGridPanel.add(card);
        }

        productGridPanel.revalidate();
        productGridPanel.repaint();
    }
}