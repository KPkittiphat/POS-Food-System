package ui;

import model.Order;
import service.PaymentService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

public class ReceiptDialog extends JDialog {
    public ReceiptDialog(JFrame parent, Order order) {
        super(parent, "ใบเสร็จ", true);
        setSize(450, 650);
        setLocationRelativeTo(null); 
        setResizable(false);

        PaymentService paymentService = new PaymentService();
        String receiptText = paymentService.getReceiptText(order);

        JTextArea receiptArea = new JTextArea(receiptText);
        receiptArea.setEditable(false);

        receiptArea.setFont(new Font("Tahoma", Font.PLAIN, 13));

        receiptArea.setBackground(Color.WHITE);
        receiptArea.setForeground(Color.BLACK);

        receiptArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        receiptArea.setAlignmentX(Component.CENTER_ALIGNMENT);

        JScrollPane scrollPane = new JScrollPane(receiptArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("ใบเสร็จการสั่งซื้อ"));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton printButton = new JButton("พิมพ์ใบเสร็จ");
        printButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
        printButton.setPreferredSize(new Dimension(120, 35));
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    receiptArea.print(null, null, true, null, null, true);
                } catch (PrinterException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(ReceiptDialog.this,
                            "ไม่สามารถพิมพ์ได้: " + ex.getMessage(),
                            "พิมพ์ผิดพลาด", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton closeButton = new JButton("ปิด");
        closeButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
        closeButton.setPreferredSize(new Dimension(80, 35));
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        buttonPanel.add(printButton);
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
