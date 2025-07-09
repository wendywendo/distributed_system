package com.example.drinksproject.dao;

import com.example.drinksproject.DBConnection;
import com.example.drinksproject.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockDao {
    public static boolean reduceStock(int branchId, int dirnkId, int quantity) {
        String sql = "UPDATE stock SET quantity = quantity - ? WHERE branch_id = ? AND quantity >= ? ";

        try (Connection connection =DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, quantity);
            statement.setInt(2, branchId);
            statement.setInt(3, quantity);
            statement.setInt(4, dirnkId);

            return statement.executeUpdate() > 0;

        }catch (SQLException e){
            System.out.println("Stock Update Error"+e);
        }
        return false;
    }

    public static boolean isOutOfStock(int branchId, int drinkId) {
        String sql = "SELECT quantity FROM stock WHERE branch_id =? AND drink_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);){

            statement.setInt(1, branchId);
            statement.setInt(2, drinkId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return resultSet.getInt("quantity") <= 0;
            }
        }catch (Exception e){
            System.out.println("Stock check Error" +e);
        }
        return false;
    }
}
