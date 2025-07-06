package com.example.drinksproject;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import java.security.Key;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import javafx.util.Duration;

import javax.swing.*;
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

    @FXML private Label todaySalesLabel;
    @FXML private Label ordersCountLabel;
    @FXML private Label customersCountLabel;
  
    @FXML private Button addItemButton;
    @FXML private TextField searchField;
    @FXML private Label branchNameLabel;

    private final List<OrderItem> orderItems = new ArrayList<>();
    private double totalOrderCost = 0.0;

    boolean isHeadquarters;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Bind properties
        orderIdCol.setCellValueFactory(cell -> cell.getValue().orderIdProperty().asString());
        customerCol.setCellValueFactory(cell -> cell.getValue().customerProperty());
        branchCol.setCellValueFactory(cell -> cell.getValue().branchProperty());
        itemsCol.setCellValueFactory(cell -> cell.getValue().itemsProperty());
        amountCol.setCellValueFactory(cell -> cell.getValue().amountProperty().asObject());
        dateCol.setCellValueFactory(cell -> cell.getValue().dateProperty());


        // Load orders
        List<Order> ordersList = OrderDao.getAllOrders(searchField.getText());
        ObservableList<Order> orders = FXCollections.observableArrayList(
                ordersList
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
      
        // Set branch name label
        branchNameLabel.setText(Session.getBranchName().toUpperCase() + " BRANCH");

        //updating the stats
        updateDashboardStats();
                                                            //updates after every 1 sec
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateDashboardStats()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }

    public void searchOrders(ActionEvent event) throws IOException {
        List<Order> ordersList = OrderDao.getAllOrders(searchField.getText());
        ObservableList<Order> orders = FXCollections.observableArrayList(
                ordersList
        );
        ordersTable.setItems(orders);
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

                    //DASHBOARD STATS
    public static int getAllCustomers() {
        String query = "SELECT COUNT(customer_id) FROM customer";
        try(Connection connection= DBConnection.getConnection(); PreparedStatement statement =connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e    );
        }
        return 0;
    }

    public static int getTotalOrders() {
        String query = "SELECT COUNT(order_id) FROM `order`";

        try (Connection connection= DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()){
            if (resultSet.next()){
                return resultSet.getInt(1);
            }
        } catch (Exception e){
            System.out.println("Error: " + e    );
        }
        return 0;
    }

    public static double getTotalOrderCost() {
        String query = "SELECT SUM(total_price) FROM orderitem";

        try(Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet= statement.executeQuery()) {
            if (resultSet.next()){
                return resultSet.getDouble(1);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e    );
        }
        return 0.0;
    }

    //Updating the Dashboard stats
    private void updateDashboardStats(){
        int totalCustomers = getAllCustomers();
        int totalOrders = getTotalOrders();
        double totalCost = getTotalOrderCost();

        Platform.runLater(() ->{
            customersCountLabel.setText(String.valueOf(totalCustomers));
            ordersCountLabel.setText(String.valueOf(totalOrders));
            todaySalesLabel.setText(String.format("Ksh %.2f",totalCost));
        });
    }


    // Logout action
    public void logout(ActionEvent event) throws IOException {
        Session.clear();
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


//    Handle add item
    @FXML
    private void handleAddItem(ActionEvent event) {
        Drink selectedDrink = drinkChoiceBox.getValue();
        String quantityText = quantityField.getText().trim();

        if (selectedDrink == null || quantityText.isEmpty()) {
            showAlert("❗ Please select a drink and enter quantity.");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityText);
            if (quantity <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showAlert("❗ Quantity must be a positive number.");
            return;
        }

        double price = selectedDrink.getPrice();
        double itemTotal = price * quantity;

        OrderItem item = new OrderItem(selectedDrink.getId(), selectedDrink.getName(), quantity, price);
        orderItems.add(item);

        // Display item in orderItemsList (VBox)
        Label itemLabel = new Label(selectedDrink.getName() + " x" + quantity + " - Ksh " + itemTotal);
        itemLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #374151;");
        orderItemsList.getChildren().add(itemLabel);

        // Update total
        totalOrderCost += itemTotal;
        orderTotalLabel.setText("Ksh " + totalOrderCost);

        // Reset input fields
        drinkChoiceBox.setValue(null);
        quantityField.clear();
        itemPriceLabel.setText("Ksh 0");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    //   Handle place order new implementation
    @FXML
    private void handlePlaceOrder(ActionEvent event) {
        Customer selectedCustomer = customerChoiceBox.getValue();

        if (selectedCustomer == null) {
            showAlert("❗ Please select a customer.");
            return;
        }

        if (orderItems.isEmpty()) {
            showAlert("❗ Please add at least one item to the order.");
            return;
        }

        // Get current branch Id
        int branchId = Session.getBranchId();

        // Insert order
        int orderId = OrderDao.insertOrder(selectedCustomer.getId(), branchId);

        if (orderId == -1) {
            showAlert("❌ Failed to place order.");
            return;
        }

        boolean allItemsInserted = true;
        for (OrderItem item : orderItems) {
            boolean success = OrderItemDao.insertOrderItem(orderId, item.getDrinkId(), item.getQuantity(), item.getTotalPrice());
            if (!success) {
                allItemsInserted = false;
                break;
            }
        }

        if (allItemsInserted) {
            showAlert("✅ Order placed successfully!");

            // Reset form
            orderItems.clear();
            orderItemsList.getChildren().clear();
            orderTotalLabel.setText("Ksh 0");
            totalOrderCost = 0.0;
            customerChoiceBox.setValue(null);

            // Reload orders list
            List<Order> ordersList = OrderDao.getAllOrders(searchField.getText());
            ObservableList<Order> orders = FXCollections.observableArrayList(ordersList);
            ordersTable.setItems(orders);
        } else {
            showAlert("⚠️ Order saved but failed to save one or more items.");
        }
    }






}
