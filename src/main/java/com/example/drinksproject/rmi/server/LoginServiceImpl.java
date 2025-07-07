package com.example.drinksproject.rmi.server;

import com.example.drinksproject.DBConnection;
import com.example.drinksproject.rmi.shared.LoginResponseDTO;
import com.example.drinksproject.rmi.shared.LoginService;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginServiceImpl extends UnicastRemoteObject implements LoginService {

    public LoginServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public LoginResponseDTO login(String username, String password, String branchName) throws RemoteException {
        String query = """
            SELECT u.username, b.branch_id, b.branch_name
            FROM admin u
            JOIN branch b ON u.branch_id = b.branch_id
            WHERE u.username = ? AND u.password = ? AND b.branch_name = ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, branchName);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new LoginResponseDTO(
                        rs.getString("username"),
                        rs.getInt("branch_id"),
                        rs.getString("branch_name")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
