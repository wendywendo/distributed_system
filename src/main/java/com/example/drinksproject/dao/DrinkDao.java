package com.example.drinksproject.dao;

import com.example.drinksproject.DBConnection;
import com.example.drinksproject.model.Drink;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;

public class DrinkDao {

    public static int getDrinkId(String drinkName) {
        String query = "SELECT drink_id FROM drink WHERE drink_name = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, drinkName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("drink_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static double getDrinkPrice(String drinkName) {
        String query = "SELECT price FROM drink WHERE drink_name = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, drinkName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    // ✅ NEW: Fetch all drinks
    public static List<Drink> getAllDrinks() {
        List<Drink> drinks = new ArrayList<>();
        String query = "SELECT drink_id, drink_name, price FROM drink";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                drinks.add(new Drink(
                        rs.getInt("drink_id"),
                        rs.getString("drink_name"),
                        rs.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drinks;
    }
}
