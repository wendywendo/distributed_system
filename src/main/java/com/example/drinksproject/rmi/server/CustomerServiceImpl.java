package com.example.drinksproject.rmi.server;

import com.example.drinksproject.DBConnection;
import com.example.drinksproject.rmi.shared.CustomerService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class CustomerServiceImpl extends UnicastRemoteObject implements CustomerService {

    public CustomerServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public boolean registerCustomer(String name, String phone) throws RemoteException {
        String query = "INSERT INTO customer (name, phone) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, phone);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
