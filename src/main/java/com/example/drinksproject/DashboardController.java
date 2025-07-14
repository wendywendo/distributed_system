package com.example.drinksproject;


import com.sun.tools.jconsole.JConsoleContext;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.drinksproject.dao.*;
import com.example.drinksproject.model.*;
import javafx.scene.layout.VBox;
import javafx.scene.control.ChoiceBox;
import javafx.util.Duration;
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

    @FXML private TableView<Customer> customersTable;
    @FXML private TableColumn<Customer, String> customerIdCol;
    @FXML private TableColumn<Customer, String> customerNameCol;
    @FXML private TableColumn<Customer, String> customerPhoneCol;

    @FXML private TableView<Stock> stocksTable;
    @FXML private TableColumn<Stock, String> stockBranchNameCol;
    @FXML private TableColumn<Stock, String> stockDrinkNameCol;
    @FXML private TableColumn<Stock, String> currentStockCol;

    @FXML private TabPane tabPane;
    @FXML private Tab addOrderTab;
    @FXML private Tab customersTab;
    @FXML private Tab viewReportsTab;

    @FXML private HBox quickActions;
    @FXML private Button viewReportsLink;

    @FXML private TextField customerNameField;
    @FXML private TextField customerPhoneField;
    @FXML private Label customerStatsLabel;

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

    @FXML private ChoiceBox<Drink> stockDrinkChoiceBox;
    @FXML private ChoiceBox<Branch> stockBranchChoiceBox;
    @FXML private TextField stockAddQuantityField;
    @FXML private Button restockButton;

    private List<OrderItem> orderItems = new ArrayList<>();
    private double totalOrderCost = 0.0;

    boolean isHeadquarters;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Show "Reports" tab only when isHQ
        boolean isHQ = Session.getBranchName().equalsIgnoreCase("nairobi");

        if (!isHQ) {
            tabPane.getTabs().remove(viewReportsTab); // Hide tab if not HQ
        }

        // Bind properties
        orderIdCol.setCellValueFactory(cell -> cell.getValue().orderIdProperty().asString());
        customerCol.setCellValueFactory(cell -> cell.getValue().customerProperty());
        branchCol.setCellValueFactory(cell -> cell.getValue().branchProperty());
        itemsCol.setCellValueFactory(cell -> cell.getValue().itemsProperty());
        amountCol.setCellValueFactory(cell -> cell.getValue().amountProperty().asObject());
        dateCol.setCellValueFactory(cell -> cell.getValue().dateProperty());

        customerIdCol.setCellValueFactory(cell -> cell.getValue().customerIdProperty().asString());
        customerNameCol.setCellValueFactory(cell -> cell.getValue().customerNameProperty());
        customerPhoneCol.setCellValueFactory(cell -> cell.getValue().customerPhoneProperty());

        stockBranchNameCol.setCellValueFactory(cell -> cell.getValue().branchNameProperty());
        stockDrinkNameCol.setCellValueFactory(cell -> cell.getValue().drinkNameProperty());
        currentStockCol.setCellValueFactory(cell -> cell.getValue().currentStockProperty().asString());

        // Load stocks
        List<Stock> stocksList = StockDao.getAllStocks();
        ObservableList<Stock> stocksObservable = FXCollections.observableArrayList(
                stocksList
        );
        stocksTable.setItems(stocksObservable);

        // Load customers
        List<Customer> customersList = CustomerDao.getAllCustomers();
        ObservableList<Customer> customersObservable = FXCollections.observableArrayList(
                customersList
        );
        customersTable.setItems(customersObservable);

        // Load orders
        List<Order> ordersList = OrderDao.getAllOrders(searchField.getText());
        ObservableList<Order> orders = FXCollections.observableArrayList(
                ordersList
        );
        ordersTable.setItems(orders);

        // Set customer stats label
        customerStatsLabel.setText(String.valueOf(CustomerDao.getCustomersCount()) + " Registered Customers");

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
        stockDrinkChoiceBox.setItems(FXCollections.observableArrayList(drinks));

        drinkChoiceBox.setOnAction(event -> {
            Drink selected = drinkChoiceBox.getValue();
            if (selected != null) {
                itemPriceLabel.setText("Ksh " + selected.getPrice());
            }
        });
      
        // Set branch name label
        branchNameLabel.setText(Session.getBranchName().toUpperCase() + " BRANCH");

        // Load branches list
        List<Branch> branches = getAllBranches();
        stockBranchChoiceBox.setItems(FXCollections.observableArrayList(branches));

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

    public static List<Branch> getAllBranches() {
        String query = "SELECT branch_id, branch_name FROM branch";

        List<Branch> branches = new ArrayList<>();
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                branches.add(new Branch(
                        rs.getInt("branch_id"),
                        rs.getString("branch_name")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }

        return branches;
    }

    //DASHBOARD STATS
    public static int getAllCustomers() {
        String query = "SELECT COUNT(customer_id) FROM customer";
        try(Connection connection= DBConnection.getConnection();
            PreparedStatement statement =connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery()) {
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
            List<Customer> customersList = CustomerDao.getAllCustomers();
            ObservableList<Customer> customersObservable = FXCollections.observableArrayList(
                    customersList
            );
            customersTable.setItems(customersObservable);

            customerStatsLabel.setText(String.valueOf(CustomerDao.getCustomersCount()) + " Registered Customers");
        } else {
            customerStatsLabel.setText("❌ Failed to register customer.");
        }
    }


    // Handle add item
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

        // Check if item is out of stock
        if (StockDao.isOutOfStock(Session.getBranchId(), selectedDrink.getId(), quantity)) {
            // Item is out of stock
            showAlert("❗ Insufficient items to perform required order!");

        } else {

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
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Low stock warning
    private void showLowStockWarnings() {
        int lowStockThreshold = 10;
        List<Stock> lowStocks = StockDao.getLowStockItems(lowStockThreshold);

        if (!lowStocks.isEmpty()) {
            StringBuilder message = new StringBuilder("Low stock alerts:\n\n");
            for (Stock s : lowStocks) {
                message.append(String.format("- %s at %s: %d left\n", s.getDrinkName(), s.getBranchName(), s.getCurrentStock()));
            }

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Low Stock Warning");
            alert.setHeaderText("Some items are running low!");
            alert.setContentText(message.toString());
            alert.showAndWait();
        }
    }

    // Group orderItems by drink to efficiently check stock
    public List<OrderItem> groupOrderItemsByDrink(List<OrderItem> orderItems) {
        Map<Integer, OrderItem> grouped = new LinkedHashMap<>();

        for (OrderItem item : orderItems) {
            int drinkId = item.getDrinkId();

            if (grouped.containsKey(drinkId)) {
                OrderItem existing = grouped.get(drinkId);
                existing.setQuantity(existing.getQuantity() + item.getQuantity());
            } else {
                grouped.put(drinkId, new OrderItem(
                        item.getDrinkId(),
                        item.getDrinkName(),
                        item.getQuantity(),
                        item.getPrice()
                ));
            }
        }

        return new ArrayList<>(grouped.values());
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

        orderItems = groupOrderItemsByDrink(orderItems);

        // First, check stock availability for all items
        for (OrderItem item : orderItems) {
            boolean hasStock = !StockDao.isOutOfStock(branchId, item.getDrinkId(), item.getQuantity());
            if (!hasStock) {
                showAlert("❌ Cannot place order. Insufficient stock for: " + item.getDrinkName());
                return;
            }
        }

        // Insert order
        int orderId = OrderDao.insertOrder(selectedCustomer.getId(), branchId);

        if (orderId == -1) {
            showAlert("❌ Failed to place order.");
            return;
        }

        boolean allItemsInserted = true;
        for (OrderItem item : orderItems) {

            double unitPrice = DrinkDao.getDrinkPrice(item.getDrinkName());
            double calculatedTotalPrice = unitPrice * item.getQuantity();

            boolean success = OrderItemDao.insertOrderItem(
                    orderId,
                    item.getDrinkId(),
                    item.getQuantity(),
                    calculatedTotalPrice
            );

            if (success) {
                // Update stock
                if (!StockDao.deductStock(branchId, item.getDrinkId(), item.getQuantity())) {
                    allItemsInserted = false;
                }
            } else {
                allItemsInserted = false;
                showAlert("Error: The item " + item.getDrinkName() + " has insufficient stock!");
            }
        }

        if (allItemsInserted) {
            showAlert("✅ Order placed successfully!");

            resetOrder();

            // Reload orders list
            List<Order> ordersList = OrderDao.getAllOrders(searchField.getText());
            ObservableList<Order> orders = FXCollections.observableArrayList(ordersList);
            ordersTable.setItems(orders);

            // Reload stocks table
            List<Stock> stocksList = StockDao.getAllStocks();
            ObservableList<Stock> stocksObservable = FXCollections.observableArrayList(
                    stocksList
            );
            stocksTable.setItems(stocksObservable);

            showLowStockWarnings();
        } else {
            showAlert("Order saved but failed to save one or more items.");
        }
    }

    private void resetOrder() {
        // Reset form
        orderItems.clear();
        orderItemsList.getChildren().clear();
        orderTotalLabel.setText("Ksh 0");
        totalOrderCost = 0.0;
        customerChoiceBox.setValue(null);
    }

    // Cancel order
    @FXML
    private void cancelOrder(ActionEvent event) {
        resetOrder();
        showAlert("Order cancelled!");
    }

    @FXML
    private void handleShowCustomerReport() {
        openReportScene("customer");
    }

    @FXML
    private void handleShowBranchesReport() {
        openReportScene("performance");
    }

    @FXML
    private void handleShowBranchSalesReport() {
        openReportScene("branch");
    }

    @FXML
    private void handleShowTotalReport() {
        openReportScene("total");
    }


    private void openReportScene(String type) {
        try {
            ReportsController.setReportType(type);
            Parent root = FXMLLoader.load(HelloApplication.class.getResource("reports.fxml"));
            Stage reportStage = new Stage();
            reportStage.setTitle("Sales Report");
            reportStage.setScene(new Scene(root));
            reportStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Restock button
    @FXML
    private void handleRestock(ActionEvent event) {
        Branch selectedBranch = stockBranchChoiceBox.getValue();
        Drink selectedDrink = stockDrinkChoiceBox.getValue();
        String qtyText = stockAddQuantityField.getText().trim();

        if (selectedBranch == null || selectedDrink == null || qtyText.isEmpty()) {
            showAlert("❗ Please select a branch, a drink, and enter quantity.");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(qtyText);
            if (quantity <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showAlert("❗ Quantity must be a positive number.");
            return;
        }

        boolean success = StockDao.addStock(selectedBranch.getBranchId(), selectedDrink.getId(), quantity);

        if (success) {
            showAlert("✅ Restocked successfully.");

            // Reload stocks table
            List<Stock> stocksList = StockDao.getAllStocks();
            ObservableList<Stock> stocksObservable = FXCollections.observableArrayList(
                    stocksList
            );
            stocksTable.setItems(stocksObservable);

            // Clear input fields
            stockAddQuantityField.clear();
            stockBranchChoiceBox.setValue(null);
            stockDrinkChoiceBox.setValue(null);
        } else {
            showAlert("❌ Failed to restock.");
        }
    }


}
