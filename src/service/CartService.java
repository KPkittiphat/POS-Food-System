package service;

import model.Product;
import model.OrderItem;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class CartService {
    private Map<String, OrderItem> items;

    public CartService() {
        this.items = new HashMap<>();
    }

    public void addItem(Product product, int quantity) {
        if (items.containsKey(product.getId())) {
            OrderItem existingItem = items.get(product.getId());
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            items.put(product.getId(), new OrderItem(product, quantity));
        }
    }

    public void removeItem(String productId) {
        items.remove(productId);
    }

    public void updateQuantity(String productId, int newQuantity) {
        if (items.containsKey(productId)) {
            if (newQuantity > 0) {
                items.get(productId).setQuantity(newQuantity);
            } else {
                removeItem(productId);
            }
        }
    }

    public List<OrderItem> getItems() {
        return new ArrayList<>(items.values());
    }

    public double getTotalPrice() {
        return items.values().stream()
                .mapToDouble(OrderItem::getTotalPrice)
                .sum();
    }

    public void clearCart() {
        items.clear();
    }
}