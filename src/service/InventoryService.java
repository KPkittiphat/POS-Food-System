package service;

import model.Product;
import util.FileUtil;

import java.util.List;
import java.util.stream.Collectors;

public class InventoryService {
    private List<Product> products;
    private static final String PRODUCTS_FILE_PATH = "data/products.csv";

    public InventoryService() {
        this.products = FileUtil.readProductsFromCsv(PRODUCTS_FILE_PATH);
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public List<Product> getProductsByCategory(String category) {
        return products.stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public Product getProductById(String id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}