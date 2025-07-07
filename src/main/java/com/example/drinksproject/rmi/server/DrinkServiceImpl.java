package com.example.drinksproject.rmi.server;

import com.example.drinksproject.DBConnection;
import com.example.drinksproject.model.Drink;
import com.example.drinksproject.rmi.shared.DrinkService;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DrinkServiceImpl extends UnicastRemoteObject implements DrinkService {

    public DrinkServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public List<Drink> getAllDrinks() throws RemoteException {
        List<Drink> drinks = new ArrayList<>();
        String query = "SELECT drink_id, drink_name, price FROM drink";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("drink_id");
                String name = rs.getString("drink_name");
                double price = rs.getDouble("price");
                drinks.add(new Drink(id, name, price));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Failed to fetch drinks", e);
        }

        return drinks;
    }

}
