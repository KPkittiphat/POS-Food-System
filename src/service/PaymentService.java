package service;

import model.Order;
import model.OrderItem;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PaymentService {

    private static int orderCount = 0;
    private static final int RECEIPT_WIDTH = 60;

    public Order createOrder(List<OrderItem> items) {
        String orderId = generateOrderId();
        return new Order(orderId, items);
    }

    public String getReceiptText(Order order) {
        StringBuilder receipt = new StringBuilder();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String formattedDate = order.getTimestamp().format(dtf);

        // Header
        receipt.append(centerText("MMP-Food", RECEIPT_WIDTH)).append("\n");
        receipt.append(centerText("นนทบุรี, ปัญญาภิวัฒน์, 11000", RECEIPT_WIDTH)).append("\n");
        receipt.append(centerText("โทรศัพท์: 099-999-9999", RECEIPT_WIDTH)).append("\n");
        receipt.append(repeatChar('=', RECEIPT_WIDTH)).append("\n");
        receipt.append(centerText("ใบเสร็จรับเงิน", RECEIPT_WIDTH)).append("\n");
        receipt.append(repeatChar('=', RECEIPT_WIDTH)).append("\n");
        receipt.append(String.format("เลขที่: %s\n", order.getOrderId()));
        receipt.append(String.format("วันที่: %s\n", formattedDate));
        receipt.append(repeatChar('-', RECEIPT_WIDTH)).append("\n");
        receipt.append(String.format("%-20s %-8s %12s\n", "รายการสินค้า", "จำนวน", "ราคารวม"));
        receipt.append(repeatChar('-', RECEIPT_WIDTH)).append("\n");

        // Items
        for (OrderItem item : order.getItems()) {
            String productName = item.getProduct().getName();
         
            receipt.append(String.format("%-20s %-8d %12.2f\n",
                    productName,
                    item.getQuantity(),
                    item.getTotalPrice()));
        }

        // Total
        receipt.append(repeatChar('-', RECEIPT_WIDTH)).append("\n");
        receipt.append(String.format("%-25s %15.2f\n", "ยอดรวมทั้งสิ้น", order.getTotalPrice()));
        receipt.append(repeatChar('=', RECEIPT_WIDTH)).append("\n");

        // Footer
        receipt.append("\n");
        receipt.append(centerText("ขอบคุณที่ใช้บริการ", RECEIPT_WIDTH)).append("\n");
        receipt.append(centerText("MMP-Food", RECEIPT_WIDTH)).append("\n");
        receipt.append(centerText("Thank you for your visit", RECEIPT_WIDTH)).append("\n");
        receipt.append("\n");
        receipt.append(centerText("โปรดเก็บใบเสร็จไว้เป็นหลักฐาน", RECEIPT_WIDTH)).append("\n");
        receipt.append(centerText("Please keep this receipt", RECEIPT_WIDTH)).append("\n");

        return receipt.toString();
    }

    private String repeatChar(char c, int count) {
        return String.valueOf(c).repeat(count);
    }

    private String centerText(String text, int width) {
        if (text.length() >= width) {
            return text;
        }
        int padding = (width - text.length()) / 2;
        return " ".repeat(padding) + text;
    }

    private String generateOrderId() {
        orderCount++;
        return "ORD-" + String.format("%04d", orderCount);
    }
}