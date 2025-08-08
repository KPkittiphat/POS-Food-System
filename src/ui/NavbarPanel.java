package ui;

import util.IconLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class NavbarPanel extends JPanel {
    private MainFrame mainFrame;

    public NavbarPanel(List<String> categories, MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new GridLayout(categories.size() + 1, 1, 0, 10));
        setBackground(new Color(245, 245, 245));
        setPreferredSize(new Dimension(150, 0));

        for (String category : categories) {
            JButton categoryButton = createCategoryButton(category);
            add(categoryButton);
        }
    }

    private JButton createCategoryButton(String category) {
        JButton button = new JButton(category);
        button.setFont(new Font("Tahoma", Font.BOLD, 12));
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.setHorizontalAlignment(SwingConstants.LEFT);

        String iconName = getIconNameForCategory(category);
        ImageIcon icon = IconLoader.loadIcon(iconName);
        if (icon != null) {
            button.setIcon(icon);
        }

        // Add mouse hover effects
        button.addMouseListener(new MouseAdapter() {
            private Color originalBackground = button.getBackground();

            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(220, 220, 220)); 
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(originalBackground); 
            }
        });

        
        button.addActionListener(e -> mainFrame.displayProducts(category));

        return button;
    }

    private String getIconNameForCategory(String category) {
        switch (category) {
            case "อาหารเรียกน้ำย่อย":
                return "appetizer.png";
            case "อาหารจานหลัก":
                return "main_course.png";
            case "จานรอง":
                return "side_dish.png";
            case "ของหวาน":
                return "dessert.png";
            case "เครื่องดื่ม":
                return "beverage.png";
            default:
                return "default.png";
        }
    }
}
