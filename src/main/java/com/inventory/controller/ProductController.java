package com.inventory.controller;

import com.inventory.model.Product;
import com.inventory.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductController {

    // (a) Insert at least three product records
    public void insertProducts() {

        String sql = "INSERT INTO Product VALUES (?, ?, ?, ?, ?)";

        Product[] products = {
                new Product(101, "Laptop", "Electronics", 15, 55000),
                new Product(102, "Mouse", "Electronics", 10, 500),
                new Product(103, "Chair", "Furniture", 25, 2500)
        };

        try (Connection con = DBConnection.getDatabaseConnection()) {

            if (con == null) return;

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                for (Product p : products) {

                    try {
                        ps.setInt(1, p.getProductId());
                        ps.setString(2, p.getProductName());
                        ps.setString(3, p.getCategory());
                        ps.setInt(4, p.getQuantity());
                        ps.setDouble(5, p.getPrice());

                        ps.executeUpdate();

                        System.out.println("Product inserted: "
                                + p.getProductName());

                    } catch (SQLException e) {

                        if ("23000".equals(e.getSQLState())) {
                            System.out.println("Duplicate product ID: "
                                    + p.getProductId());
                        } else {
                            throw e;
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // (b) Conditional retrieval and sorting
    public void retrieveProducts(String category) {

        String sql = "SELECT * FROM Product " +
                "WHERE category = ? AND quantity < 20 " +
                "ORDER BY price DESC";

        try (Connection con = DBConnection.getDatabaseConnection()) {

            if (con == null) return;

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, category);

                try (ResultSet rs = ps.executeQuery()) {

                    System.out.println("\nProducts in " + category);

                    while (rs.next()) {

                        System.out.println(
                                rs.getInt("product_id") + " | " +
                                        rs.getString("product_name") + " | " +
                                        rs.getString("category") + " | " +
                                        rs.getInt("quantity") + " | " +
                                        rs.getDouble("price")
                        );
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // (c) Update product stock
    public void updateStock(int productId, int addedQuantity) {

        String sql = "UPDATE Product SET quantity = quantity + ? " +
                "WHERE product_id = ?";

        try (Connection con = DBConnection.getDatabaseConnection()) {

            if (con == null) return;

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, addedQuantity);
                ps.setInt(2, productId);

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    System.out.println("Stock updated successfully!");
                } else {
                    System.out.println("Product does not exist!");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // (d) Search and modify product price
    public void updatePrice(int productId, double newPrice) {

        if (newPrice <= 0) {
            System.out.println("Price must be greater than zero!");
            return;
        }

        String searchSql = "SELECT * FROM Product WHERE product_id = ?";
        String updateSql = "UPDATE Product SET price = ? WHERE product_id = ?";

        try (Connection con = DBConnection.getDatabaseConnection()) {

            if (con == null) return;

            try (PreparedStatement search = con.prepareStatement(searchSql)) {

                search.setInt(1, productId);

                try (ResultSet rs = search.executeQuery()) {

                    if (rs.next()) {

                        try (PreparedStatement update =
                                     con.prepareStatement(updateSql)) {

                            update.setDouble(1, newPrice);
                            update.setInt(2, productId);
                            update.executeUpdate();
                        }

                        System.out.println("Updated Product Details:");
                        System.out.println("ID: " + productId);
                        System.out.println("Name: "
                                + rs.getString("product_name"));
                        System.out.println("New Price: " + newPrice);

                    } else {
                        System.out.println("Product not found!");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // (e) Aggregate query
    public void showInventoryValue() {

        String sql = "SELECT SUM(quantity * price) AS total_value FROM Product";

        try (Connection con = DBConnection.getDatabaseConnection()) {

            if (con == null) return;

            try (PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    System.out.println("Total Inventory Value: "
                            + rs.getDouble("total_value"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // (e) Delete only when quantity is zero
    public void deleteProduct(int productId) {

        String sql = "DELETE FROM Product " +
                "WHERE product_id = ? AND quantity = 0";

        try (Connection con = DBConnection.getDatabaseConnection()) {

            if (con == null) return;

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, productId);

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    System.out.println("Product deleted successfully!");
                } else {
                    System.out.println(
                            "Cannot delete: Product not found or quantity is not zero!"
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
