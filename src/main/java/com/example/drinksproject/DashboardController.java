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

import com.example.drinksproject.dao.*;
import com.example.drinksproject.model.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.control.ChoiceBox;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.net.URL;
import java.util.*;



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

    @FXML private TextField customerNameField;
    @FXML private TextField customerPhoneField;
    @FXML private Label customerStatsLabel;
    @FXML private TableView<?> customersTable; // You can type this more specifically later if needed

    @FXML private ChoiceBox<Customer> customerChoiceBox;
    @FXML private ChoiceBox<Drink> drinkChoiceBox;
    @FXML private TextField quantityField;
    @FXML private Label itemPriceLabel;
    @FXML private VBox orderItemsList;
    @FXML private Label orderTotalLabel;

    private final List<OrderItem> orderItems = new ArrayList<>();
    private double totalOrderCost = 0.0;

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

        // Load customer list
        List<Customer> customers = CustomerDao.getAllCustomers();
        customerChoiceBox.setItems(FXCollections.observableArrayList(customers));

// Load drink list
        List<Drink> drinks = DrinkDao.getAllDrinks();
        drinkChoiceBox.setItems(FXCollections.observableArrayList(drinks));

        drinkChoiceBox.setOnAction(event -> {
            Drink selected = drinkChoiceBox.getValue();
            if (selected != null) {
                itemPriceLabel.setText("Ksh " + selected.getPrice());
            }
        });



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

    @FXML
    private void registerUser(ActionEvent event) {
        String name = customerNameField.getText().trim();
        String phone = customerPhoneField.getText().trim();

        if (name.isEmpty() || phone.isEmpty()) {
            customerStatsLabel.setText("❗ Please fill in all fields.");
            return;
        }

        boolean success = CustomerDao.registerUser(name, phone);
        if (success) {
            customerStatsLabel.setText("✅ Customer registered successfully!");
            customerNameField.clear();
            customerPhoneField.clear();

            // Optional: refresh customer table here if implemented
        } else {
            customerStatsLabel.setText("❌ Failed to register customer.");
        }
    }




}
