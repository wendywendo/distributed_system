package com.example.drinksproject;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML private TableView<Order> ordersTable;
    @FXML private TableColumn<Order, String> orderIdCol;
    @FXML private TableColumn<Order, String> customerCol;
    @FXML private TableColumn<Order, String> branchCol;
    @FXML private TableColumn<Order, String> itemsCol;
    @FXML private TableColumn<Order, Double> amountCol;
    @FXML private TableColumn<Order, String> dateCol;

    @FXML private TabPane tabPane;
    @FXML private Tab addOrderTab;
    @FXML private Tab customersTab;
    @FXML private Tab viewReportsTab;

    @FXML private HBox quickActions;
    @FXML private Button viewReportsLink;

    boolean isHeadquarters;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Bind properties
        orderIdCol.setCellValueFactory(cell -> cell.getValue().orderIdProperty());
        customerCol.setCellValueFactory(cell -> cell.getValue().customerProperty());
        branchCol.setCellValueFactory(cell -> cell.getValue().branchProperty());
        itemsCol.setCellValueFactory(cell -> cell.getValue().itemsProperty());
        amountCol.setCellValueFactory(cell -> cell.getValue().amountProperty().asObject());
        dateCol.setCellValueFactory(cell -> cell.getValue().dateProperty());

        // Sample data
        ObservableList<Order> orders = FXCollections.observableArrayList(
                new Order("ORD-001", "John Doe", "NAIROBI HQ", "Coca Cola x5, Sprite x3", 850, "2024-06-02"),
                new Order("ORD-002", "Jane Smith", "NAKURU", "Pepsi x2, Fanta x4", 720, "2024-06-02"),
                new Order("ORD-003", "Mike Johnson", "MOMBASA", "Water x10, Energy Drink x2", 650, "2024-06-01"),
                new Order("ORD-004", "Sarah Wilson", "KISUMU", "Juice x3, Soda x6", 950, "2024-06-01")
        );

        ordersTable.setItems(orders);

        // Show the reports only when current user isHeadquarters
        // Implement function to check this
        isHeadquarters = true;

        if (!isHeadquarters) {
            tabPane.getTabs().remove(viewReportsTab);
            quickActions.getChildren().remove(viewReportsLink);
        }
    }

    public void goToAddOrder(ActionEvent event) throws IOException {
        tabPane.getSelectionModel().select(addOrderTab);
    }

    public void goToAddCustomer(ActionEvent event) throws IOException {
        tabPane.getSelectionModel().select(customersTab);
    }

    public void goToViewReports(ActionEvent event) throws IOException {
        tabPane.getSelectionModel().select(viewReportsTab);
    }

    // Logout action
    public void logout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("login.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);

        // Force maximize *after* setting the scene
        Platform.runLater(() -> stage.setMaximized(true));

        stage.show();
    }

}
