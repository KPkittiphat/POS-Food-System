package util;

import model.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static List<Product> readProductsFromCsv(String filePath) {
        List<Product> products = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String id = data[0].trim();
                String name = data[1].trim();
                double price = Double.parseDouble(data[2].trim());
                String category = data[3].trim();
                String imagePath = data[4].trim();
                products.add(new Product(id, name, price, category, imagePath));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }
}