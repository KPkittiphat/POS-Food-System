package ui.components;

import model.Product;
import ui.MainPanel;
import util.IconLoader;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProductCard extends JPanel {
    private final Product product;
    private final MainPanel mainPanel;

    public ProductCard(Product product, MainPanel mainPanel) {
        this.product = product;
        this.mainPanel = mainPanel;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
        setBackground(Color.WHITE);

        setPreferredSize(new Dimension(120, 100));
        setMaximumSize(new Dimension(120, 100));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setOpaque(false);
        imagePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 2, 5));

        JLabel imageLabel = new JLabel();
        ImageIcon icon = IconLoader.loadImage(product.getImagePath());
        if (icon != null) {
            Image scaledImage = icon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImage));
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }
        imagePanel.add(imageLabel, BorderLayout.CENTER);

        JLabel nameLabel = new JLabel(product.getName());
        nameLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel priceLabel = new JLabel(String.format("%.2f บาท", product.getPrice()));
        priceLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        priceLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel infoPanel = new JPanel(new GridLayout(2, 1, 0, 2));
        infoPanel.setOpaque(false);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(2, 5, 5, 5));
        infoPanel.add(nameLabel);
        infoPanel.add(priceLabel);

        add(imagePanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.SOUTH);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainPanel.getCartService().addItem(product, 1);
                mainPanel.updateCartView();
            }
        });
    }
}