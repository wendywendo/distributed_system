package com.example.drinksproject.dao;

import com.example.drinksproject.DBConnection;
import com.example.drinksproject.model.Customer;

import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerDao {
    public static boolean registerUser(String name, String phone) {
        String sql = "INSERT INTO customer (customer_name, phone) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, phone);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT customer_id, customer_name, phone FROM customer";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                customers.add(new Customer(
                        rs.getInt("customer_id"),
                        rs.getString("customer_name"),
                        rs.getString("phone")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public static int getCustomersCount(){
        String query="SELECT COUNT(customer_id) FROM customer";
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception e){
                System.out.println("Error:"+ e);
            }
        return 0;
        }
    }


