package com.example.drinksproject.dao;

import com.example.drinksproject.DBConnection;
import com.example.drinksproject.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class OrderDao {
    public static int insertOrder(int customerId, int branchId) {
        System.out.println("Inserting order for customerId=" + customerId + ", branchId=" + branchId);
        String query = "INSERT INTO `order` (customer_id, branch_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, customerId);
            stmt.setInt(2, branchId);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static List<Order> getAllOrders(String branchname, String customer_name) {
        if (branchname == null){ branchname = "";}
        if (customer_name == null){customer_name = "";}

        List<Order> orders = new ArrayList<>();

        String sql = "SELECT \n" +
                "    o.order_id,\n" +
                "    c.customer_name,\n" +
                "    b.branch_name,\n" +
                "    GROUP_CONCAT(CONCAT(oi.quantity, 'x ', d.drink_name) SEPARATOR ', ') AS items,\n" +
                "    SUM(oi.total_price) AS amount,\n" +
                "    o.order_date AS date\n" +
                "FROM `order` o\n" +
                "JOIN customer c ON o.customer_id = c.customer_id\n" +
                "JOIN branch b ON o.branch_id = b.branch_id\n" +
                "JOIN orderitem oi ON o.order_id = oi.order_id\n" +
                "JOIN drink d ON oi.drink_id = d.drink_id\n" +
                "WHERE customer_name LIKE ? OR branch_name LIKE ?\n" +
                "GROUP BY o.order_id, c.customer_name, b.branch_name, o.order_date\n" +
                "ORDER BY o.order_date DESC;\n";

        try {

            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, "%" + customer_name + "%");
            stmt.setString(2, "%" + branchname + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt("order_id"),
                        rs.getString("customer_name"),
                        rs.getString("branch_name"),
                        rs.getString("items"),
                        rs.getInt("amount"),
                        rs.getString("date")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }
}
