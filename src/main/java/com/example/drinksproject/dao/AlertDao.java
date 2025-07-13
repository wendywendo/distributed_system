package com.example.drinksproject.dao;

import com.example.drinksproject.DBConnection;
import com.example.drinksproject.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlertDao {
    public static boolean raiseAlert(int branchId, int drinkId, String alert) {
        String sql = "INSERT INTO alerts (branch_id, drink_id, alert, status) VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);) {

            statement.setInt(1, branchId);
            statement.setInt(2, drinkId);
            statement.setString(3, alert);

             return statement.executeUpdate() > 0;
        }catch (Exception e){
            System.out.println("Error in Alert Creation" +e);
        }
        return false;
    }

}
