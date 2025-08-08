package ui;

import service.CartService;
import service.InventoryService;
import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    private final InventoryService inventoryService;
    private final CartService cartService;
    private final MainFrame mainFrame;
    private ProductPanel productPanel;
    private CartPanel cartPanel;

    public MainPanel(InventoryService inventoryService, CartService cartService, MainFrame mainFrame) {
        this.inventoryService = inventoryService;
        this.cartService = cartService;
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout());

        productPanel = new ProductPanel(inventoryService, cartService, this);
        cartPanel = new CartPanel(cartService, this);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, productPanel, cartPanel);
        splitPane.setDividerLocation(800);
        splitPane.setOneTouchExpandable(true);

        add(splitPane, BorderLayout.CENTER);
    }

    public void displayProductsByCategory(String category) {
        productPanel.displayProducts(category);
    }

    public void updateCartView() {
        cartPanel.updateCartView();
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public CartService getCartService() {
        return cartService;
    }

}