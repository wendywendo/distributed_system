package com.example.drinksproject.dao;

import com.example.drinksproject.model.*;
import com.example.drinksproject.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDao {

    public static List<CustomerSalesReport> getCustomerSalesReport() {
        List<CustomerSalesReport> reportList = new ArrayList<>();
        String query = """
            SELECT c.customer_name, SUM(oi.total_price) AS total_spent, COUNT(DISTINCT o.order_id) AS orders
            FROM customer c
            JOIN `order` o ON c.customer_id = o.customer_id
            JOIN orderitem oi ON o.order_id = oi.order_id
            GROUP BY c.customer_id
            ORDER BY total_spent DESC
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                reportList.add(new CustomerSalesReport(
                        rs.getString("customer_name"),
                        rs.getDouble("total_spent"),
                        rs.getInt("orders")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return reportList;
    }

    public static List<BranchSalesReport> getBranchSalesReport() {
        List<BranchSalesReport> reportList = new ArrayList<>();
        System.out.println("Branch Sales Report size: " + reportList.size()); // 👀 DEBUG

        String query = """
            SELECT b.branch_name, COUNT(DISTINCT o.order_id) AS total_orders, SUM(oi.total_price) AS total_revenue
            FROM branch b
            JOIN `order` o ON b.branch_id = o.branch_id
            JOIN orderitem oi ON o.order_id = oi.order_id
            GROUP BY b.branch_id
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                reportList.add(new BranchSalesReport(
                        rs.getString("branch_name"),
                        rs.getInt("total_orders"),
                        rs.getDouble("total_revenue")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return reportList;
    }

    public static List<BranchPerformanceReport> getBranchPerformanceReport() {
        List<BranchPerformanceReport> reportList = new ArrayList<>();
        String query = """
            SELECT b.branch_name, COUNT(DISTINCT o.customer_id) AS active_customers, 
                   IFNULL(SUM(oi.total_price)/COUNT(DISTINCT o.order_id), 0) AS avg_order_value
            FROM branch b
            JOIN `order` o ON b.branch_id = o.branch_id
            JOIN orderitem oi ON o.order_id = oi.order_id
            GROUP BY b.branch_id
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                reportList.add(new BranchPerformanceReport(
                        rs.getString("branch_name"),
                        rs.getInt("active_customers"),
                        rs.getDouble("avg_order_value")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return reportList;
    }

    public static TotalSalesReport getTotalSalesReport() {
        String query = """
            SELECT 
                (SELECT COUNT(order_id) FROM `order`) AS total_orders,
                (SELECT SUM(total_price) FROM orderitem) AS total_revenue,
                (SELECT COUNT(customer_id) FROM customer) AS total_customers
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return new TotalSalesReport(
                        rs.getInt("total_orders"),
                        rs.getDouble("total_revenue"),
                        rs.getInt("total_customers")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
