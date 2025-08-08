package model;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private String orderId;
    private LocalDateTime timestamp;
    private List<OrderItem> items;
    private double totalPrice;

    public Order(String orderId, List<OrderItem> items) {
        this.orderId = orderId;
        this.timestamp = LocalDateTime.now();
        this.items = items;
        this.totalPrice = calculateTotalPrice();
    }

    public String getOrderId() {
        return orderId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    private double calculateTotalPrice() {
        return items.stream()
                .mapToDouble(OrderItem::getTotalPrice)
                .sum();
    }
}