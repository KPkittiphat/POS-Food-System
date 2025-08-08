package ui;

import service.CartService;
import service.InventoryService;

import javax.swing.*;

import model.Product;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class MainFrame extends JFrame {
    private final InventoryService inventoryService;
    private final CartService cartService;
    private MainPanel mainPanel;
    private NavbarPanel navbarPanel;

    public MainFrame() {
        this.inventoryService = new InventoryService();
        this.cartService = new CartService();

        setTitle("POS Food System");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        List<String> categories = inventoryService.getAllProducts().stream()
                .map(Product::getCategory)
                .distinct()
                .collect(Collectors.toList());

        navbarPanel = new NavbarPanel(categories, this);
        mainPanel = new MainPanel(inventoryService, cartService, this);

        add(navbarPanel, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);

        if (!categories.isEmpty()) {
            mainPanel.displayProductsByCategory(categories.get(0));
        }
    }

    public void displayProducts(String category) {
        mainPanel.displayProductsByCategory(category);
    }

    public CartService getCartService() {
        return cartService;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame ex = new MainFrame();
            ex.setVisible(true);
        });
    }
}