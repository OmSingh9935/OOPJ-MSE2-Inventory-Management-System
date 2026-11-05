
package com.inventory.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/";

    private static final String USER = "root";

    private static final String PASSWORD =
            "MYSQL_PASSWORD";

    // Establish connection with MySQL
    public static Connection getConnection() {

        try {
            return DriverManager.getConnection(
                    URL, USER, PASSWORD
            );

        } catch (Exception e) {
            System.out.println("Connection Failed!");
            e.printStackTrace();
            return null;
        }
    }

    // Create database and Product table
    public static void createDatabase() {

        Connection con = getConnection();

        if (con == null) {
            System.out.println("Connection unavailable!");
            return;
        }

        try (Connection connection = con;
             Statement stmt = connection.createStatement()) {

            stmt.executeUpdate(
                    "CREATE DATABASE IF NOT EXISTS retail_db"
            );

            stmt.executeUpdate("USE retail_db");

            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS Product (" +
                            "product_id INT PRIMARY KEY, " +
                            "product_name VARCHAR(100), " +
                            "category VARCHAR(50), " +
                            "quantity INT, " +
                            "price DOUBLE)"
            );

            System.out.println("Database created successfully!");
            System.out.println("Product table created successfully!");

        } catch (Exception e) {
            System.out.println("Database creation failed!");
            e.printStackTrace();
        }
    }

    public static Connection getDatabaseConnection() {

        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/retail_db",
                    USER,
                    PASSWORD
            );

        } catch (Exception e) {
            System.out.println("Database connection failed!");
            e.printStackTrace();
            return null;
        }
    }

}
