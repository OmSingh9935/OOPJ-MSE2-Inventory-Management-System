package com.inventory;

import com.inventory.controller.ProductController;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ProductController controller = new ProductController();

        int choice;

        do {

            System.out.println("\n================================");
            System.out.println("   INVENTORY MANAGEMENT SYSTEM");
            System.out.println("================================");
            System.out.println("1. Insert Product Records");
            System.out.println("2. Retrieve Products by Category");
            System.out.println("3. Update Product Stock");
            System.out.println("4. Search and Modify Product Price");
            System.out.println("5. Display Inventory Value");
            System.out.println("6. Delete Product (Quantity Zero)");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    controller.insertProducts();
                    break;

                case 2:
                    System.out.print("Enter category: ");
                    sc.nextLine();
                    String category = sc.nextLine();

                    controller.retrieveProducts(category);
                    break;

                case 3:
                    System.out.print("Enter Product ID: ");
                    int stockId = sc.nextInt();

                    System.out.print("Enter quantity to add: ");
                    int quantity = sc.nextInt();

                    if (quantity <= 0) {
                        System.out.println("Quantity must be positive!");
                    } else {
                        controller.updateStock(stockId, quantity);
                    }
                    break;

                case 4:
                    System.out.print("Enter Product ID: ");
                    int priceId = sc.nextInt();

                    System.out.print("Enter new price: ");
                    double price = sc.nextDouble();

                    controller.updatePrice(priceId, price);
                    break;

                case 5:
                    controller.showInventoryValue();
                    break;

                case 6:
                    System.out.print("Enter Product ID: ");
                    int deleteId = sc.nextInt();

                    controller.deleteProduct(deleteId);
                    break;

                case 0:
                    System.out.println("Exiting Inventory System...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 0);

        sc.close();
    }
}
