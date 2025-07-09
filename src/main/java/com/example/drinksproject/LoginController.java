package com.example.drinksproject;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ChoiceBox<String> branchComboBox;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Label statusLabel;

    private final String[] branches = {"Select your branch", "Nairobi", "Nakuru", "Mombasa", "Kisumu"};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        branchComboBox.setItems(FXCollections.observableArrayList(branches));
        branchComboBox.setValue(branches[0]); // Default value
        statusLabel.setVisible(false); // Hide initially
    }

    // Handle login
    public void onSubmit(ActionEvent event) {
        String username = usernameTextField.getText().trim();
        String password = passwordTextField.getText().trim();
        String branch = branchComboBox.getValue();

        if (username.isEmpty() || password.isEmpty() || branch.equals("Select your branch")) {
            statusLabel.setText("⚠️ Please fill in all fields correctly.");
            statusLabel.setVisible(true);
            return;
        }

        boolean authenticated = validateLogin(username, password, branch);

        if (authenticated) {
            statusLabel.setText("✅ Login successful!");
            statusLabel.setVisible(true);
            proceedToDashboard(event);

        } else {
            try {
                boolean registered = registerUser(username, password, branch);
                if (registered) {
                    statusLabel.setText("✅ Registered & logged in!");
                    statusLabel.setVisible(true);
                    proceedToDashboard(event);
                    return;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            statusLabel.setText("❌ Invalid username, password, or branch.");
            statusLabel.setVisible(true);
        }
    }

    private void proceedToDashboard(ActionEvent event) {
        new Thread(() -> {
            try {
                Thread.sleep(1000); // Delay to show message
                Platform.runLater(() -> {
                    try {
                        Parent root = FXMLLoader.load(HelloApplication.class.getResource("dashboard.fxml"));
                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.setMaximized(true);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Login validation logic
    private boolean validateLogin(String username, String password, String branchName) {
        String query = """
            SELECT u.username, b.branch_id
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
           if (rs.next()) { // Login successful if user exists
               int branchId = rs.getInt("branch_id");

               // Save session
               Session.set(username, branchId, branchName);
               return true;
           }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    // Registration method — not used now, but preserved for later
    private boolean registerUser(String username, String password, String branchName) throws SQLException {
        String getBranchIdQuery = "SELECT branch_id FROM branch WHERE branch_name = ?";
        String checkUserExists = "SELECT * FROM admin WHERE username = ?";
        String insertUserQuery = "INSERT INTO admin (username, password, branch_id) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            // Get branch_id
            int branch_id = -1;

            try (PreparedStatement stmt = conn.prepareStatement(getBranchIdQuery)) {
                stmt.setString(1, branchName);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    branch_id = rs.getInt("branch_id");
                }
            }

            if (branch_id == -1) {
                System.out.println("Invalid Branch!");
                return false;
            }

            // Check if user exists
            try (PreparedStatement stmt = conn.prepareStatement(checkUserExists)) {
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return false; // Username already taken
                }
            }

            // Insert user
            try (PreparedStatement stmt = conn.prepareStatement(insertUserQuery)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setInt(3, branch_id);
                stmt.executeUpdate();
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
