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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    }


    // Code to handle login
    public void onSubmit(ActionEvent event) throws  SQLException {
        String username = usernameTextField.getText().trim();
        String password = passwordTextField.getText().trim();
        String branch = branchComboBox.getValue();

        if (username.isEmpty() || password.isEmpty() || branch.isEmpty()) {
            statusLabel.setText("Please fill in all fields");
            statusLabel.setVisible(true);
            return;
        }

        boolean registered = registerUser(username, password, branch);
        if (registered) {
            statusLabel.setText("Successfully registered!");
            statusLabel.setVisible(true);


            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    Platform.runLater(() -> {
                        try {
                            Parent root = FXMLLoader.load(HelloApplication.class.getResource("dashboard.fxml"));
                            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            scene = new Scene(root);
                            stage.setScene(scene);
                            stage.setMaximized(true);
                            stage.show();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();

        } else {
            statusLabel.setText("❌ Username already exists.");
            statusLabel.setVisible(true);
        }

    }

    private boolean registerUser(String username, String password, String branchName) throws SQLException {
        String getBranchIdQuery = "SELECT branch_id FROM branches WHERE branch_name = ?";
        String checkUserExists = "SELECT * FROM users WHERE username = ?";
        String insertUserQuery = "INSERT INTO users (username, password, branch_id) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()){
            //get branch_id
            int branch_id = -1;

            try(PreparedStatement stmt = conn.prepareStatement(getBranchIdQuery)){
                stmt.setString(1, branchName);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()){
                    branch_id = rs.getInt("branch_id");
                }
            }

            if (branch_id == -1){
                System.out.println(" Invalid Branch !!!");
                return false;
            }

            try (PreparedStatement stmt = conn.prepareStatement(checkUserExists)){
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()){
                    return false;
                }
            }

            try (PreparedStatement stmt = conn.prepareStatement(insertUserQuery)){
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setInt(3, branch_id);
                stmt.executeUpdate();
                return true;
            }

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }
}


