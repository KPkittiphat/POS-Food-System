package ui;

import model.Order;
import model.OrderItem;
import service.CartService;
import service.PaymentService;
import util.IconLoader;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class CartPanel extends JPanel {
    private final CartService cartService;
    private final MainPanel mainPanel;
    private final PaymentService paymentService;
    private JList<OrderItem> cartList;
    private DefaultListModel<OrderItem> listModel;
    private JLabel totalLabel;
    private JButton checkoutButton;
    private JButton clearCartButton;

    public CartPanel(CartService cartService, MainPanel mainPanel) {
        this.cartService = cartService;
        this.mainPanel = mainPanel;
        this.paymentService = new PaymentService();

        setLayout(new BorderLayout(10, 10));
        
     
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        headerPanel.setBackground(new Color(245, 245, 245));
        
        JLabel cartIconLabel = new JLabel(IconLoader.loadIcon("add_to_cart.png"));
        JLabel cartTitleLabel = new JLabel("ตะกร้าสินค้า");
        cartTitleLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        
        headerPanel.add(cartIconLabel);
        headerPanel.add(cartTitleLabel);
        
        setBorder(BorderFactory.createTitledBorder(""));
        setBackground(new Color(245, 245, 245));
        
       
        add(headerPanel, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        cartList = new JList<>(listModel);
        cartList.setCellRenderer(new OrderItemRenderer());

        JScrollPane scrollPane = new JScrollPane(cartList);
        
        
        JPanel cartContentPanel = new JPanel(new BorderLayout());
        cartContentPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(cartContentPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        bottomPanel.setBackground(new Color(245, 245, 245));

        totalLabel = new JLabel("ยอดรวม: 0.00 บาท", SwingConstants.RIGHT);
        totalLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        bottomPanel.add(totalLabel);

        checkoutButton = new JButton("ชำระเงิน");
        checkoutButton.setIcon(IconLoader.loadIcon("pay.png"));
        checkoutButton.setFont(new Font("Tahoma", Font.BOLD, 16));
        checkoutButton.setBackground(new Color(76, 175, 80));
        checkoutButton.setForeground(Color.WHITE);
        checkoutButton.addActionListener(e -> handleCheckout());
        bottomPanel.add(checkoutButton);

        clearCartButton = new JButton("ล้างตะกร้า");
        clearCartButton.setIcon(IconLoader.loadIcon("delete.png"));
        clearCartButton.setFont(new Font("Tahoma", Font.BOLD, 16));
        clearCartButton.setBackground(new Color(244, 67, 54));
        clearCartButton.setForeground(Color.WHITE);
        clearCartButton.addActionListener(e -> handleClearCart());
        bottomPanel.add(clearCartButton);

        add(bottomPanel, BorderLayout.SOUTH);

        cartList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = cartList.locationToIndex(e.getPoint());
                    if (index != -1) {
                        OrderItem selectedItem = listModel.getElementAt(index);
                        String newQuantityStr = JOptionPane.showInputDialog(CartPanel.this,
                                "ป้อนจำนวนใหม่สำหรับ " + selectedItem.getProduct().getName(),
                                selectedItem.getQuantity());
                        try {
                            if (newQuantityStr != null) {
                                int newQuantity = Integer.parseInt(newQuantityStr);
                                cartService.updateQuantity(selectedItem.getProduct().getId(), newQuantity);
                                
                                updateCartView();
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(CartPanel.this, "กรุณาป้อนตัวเลขที่ถูกต้อง", "ผิดพลาด",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        updateCartView();
    }

    public void updateCartView() {
        listModel.clear();
        List<OrderItem> items = cartService.getItems();
        for (OrderItem item : items) {
            listModel.addElement(item);
        }
        totalLabel.setText(String.format("ยอดรวม: %.2f บาท", cartService.getTotalPrice()));
    }

    private void handleCheckout() {
        if (cartService.getItems().isEmpty()) {
            JOptionPane.showMessageDialog(this, "กรุณาเพิ่มสินค้าลงในตะกร้าก่อนชำระเงิน", "ตะกร้าว่างเปล่า",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String receivedAmountStr = JOptionPane.showInputDialog(this,
                "ยอดรวม: " + cartService.getTotalPrice() + " บาท\nป้อนจำนวนเงินที่ได้รับ:");
        if (receivedAmountStr != null) {
            try {
                double receivedAmount = Double.parseDouble(receivedAmountStr);
                if (receivedAmount >= cartService.getTotalPrice()) {
                    double change = receivedAmount - cartService.getTotalPrice();
                    JOptionPane.showMessageDialog(this, String.format("ชำระเงินเรียบร้อย! เงินทอน: %.2f บาท", change),
                            "สำเร็จ", JOptionPane.INFORMATION_MESSAGE);

                    Order order = paymentService.createOrder(cartService.getItems());
                    new ReceiptDialog(mainPanel.getMainFrame(), order).setVisible(true);

                    cartService.clearCart();
                    updateCartView();
                } else {
                    JOptionPane.showMessageDialog(this, "จำนวนเงินไม่เพียงพอ", "ผิดพลาด", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "กรุณาป้อนตัวเลขที่ถูกต้อง", "ผิดพลาด", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleClearCart() {
        cartService.clearCart();
        updateCartView();
    }

    static {
        
        java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            if (key != null && key.toString().toLowerCase().contains("font")) {
                UIManager.put(key, new Font("Tahoma", Font.PLAIN, 14));
            }
        }
    }

    class OrderItemRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                boolean cellHasFocus) {
            OrderItem item = (OrderItem) value;
            JPanel panel = new JPanel(new BorderLayout());

            JLabel nameLabel = new JLabel(item.getProduct().getName());
            nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
            JLabel quantityLabel = new JLabel("x" + item.getQuantity());
            quantityLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
            JLabel priceLabel = new JLabel(String.format("%.2f บาท", item.getTotalPrice()));
            priceLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));

            panel.add(nameLabel, BorderLayout.WEST);
            panel.add(quantityLabel, BorderLayout.CENTER);
            panel.add(priceLabel, BorderLayout.EAST);

            panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            panel.setBackground(Color.WHITE);
            return panel;
        }
    }
}