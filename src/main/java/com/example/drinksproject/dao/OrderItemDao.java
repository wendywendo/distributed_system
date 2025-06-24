package com.example.drinksproject.dao;

import com.example.drinksproject.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class OrderItemDao {
    public static boolean insertOrderItem(int orderId, int drinkId, int quantity, double totalPrice) {
        String query = "INSERT INTO orderitem (order_id, drink_id, quantity, total_price) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            stmt.setInt(2, drinkId);
            stmt.setInt(3, quantity);
            stmt.setDouble(4, totalPrice);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

