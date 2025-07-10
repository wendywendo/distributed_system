package com.example.drinksproject;

import com.example.drinksproject.dao.ReportDao;
import com.example.drinksproject.model.*;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ReportsController {

    @FXML private Label reportTitle;
    @FXML private TableView reportTableView;


    private static String reportType;

    public static void setReportType(String type) {
        reportType = type;
    }

    @FXML
    public void initialize() {

        switch (reportType) {
            case "customer" -> loadCustomerReport();
            case "branch" -> loadBranchSalesReport();
            case "performance" -> loadBranchPerformanceReport();
            case "total" -> loadTotalSalesReport();
        }
    }

    private void loadCustomerReport() {
        reportTitle.setText("Customer Sales Report");

        TableColumn<CustomerSalesReport, String> nameCol = new TableColumn<>("Customer");
        nameCol.setCellValueFactory(cell -> cell.getValue().customerNameProperty());

        TableColumn<CustomerSalesReport, String> spentCol = new TableColumn<>("Total Spent");
        spentCol.setCellValueFactory(cell -> cell.getValue().totalSpentProperty().asString());

        TableColumn<CustomerSalesReport, String> ordersCol = new TableColumn<>("Orders");
        ordersCol.setCellValueFactory(cell -> cell.getValue().ordersProperty().asString());

        reportTableView.getColumns().setAll(nameCol, spentCol, ordersCol);
        reportTableView.setItems(FXCollections.observableArrayList(ReportDao.getCustomerSalesReport()));
    }

    private void loadBranchSalesReport() {
        reportTitle.setText("Branch Sales Report");

        TableColumn<BranchSalesReport, String> branchCol = new TableColumn<>("Branch");
        branchCol.setCellValueFactory(cell -> cell.getValue().branchNameProperty());

        TableColumn<BranchSalesReport, String> ordersCol = new TableColumn<>("Orders");
        ordersCol.setCellValueFactory(cell -> cell.getValue().totalOrdersProperty().asString());

        TableColumn<BranchSalesReport, String> revenueCol = new TableColumn<>("Revenue");
        revenueCol.setCellValueFactory(cell -> cell.getValue().totalRevenueProperty().asString());

        reportTableView.getColumns().setAll(branchCol, ordersCol, revenueCol);
        reportTableView.setItems(FXCollections.observableArrayList(ReportDao.getBranchSalesReport()));
    }

    private void loadBranchPerformanceReport() {
        reportTitle.setText("Branch Performance Report");

        TableColumn<BranchPerformanceReport, String> branchCol = new TableColumn<>("Branch");
        branchCol.setCellValueFactory(cell -> cell.getValue().branchNameProperty());

        TableColumn<BranchPerformanceReport, String> custCol = new TableColumn<>("Active Customers");
        custCol.setCellValueFactory(cell -> cell.getValue().activeCustomersProperty().asString());

        TableColumn<BranchPerformanceReport, String> avgCol = new TableColumn<>("Avg Order Value");
        avgCol.setCellValueFactory(cell -> cell.getValue().averageOrderValueProperty().asString());

        reportTableView.getColumns().setAll(branchCol, custCol, avgCol);
        reportTableView.setItems(FXCollections.observableArrayList(ReportDao.getBranchPerformanceReport()));
    }

    private void loadTotalSalesReport() {
        reportTitle.setText("Total Sales Report");

        TableColumn<TotalSalesReport, String> ordersCol = new TableColumn<>("Orders");
        ordersCol.setCellValueFactory(cell -> cell.getValue().totalOrdersProperty().asString());

        TableColumn<TotalSalesReport, String> revenueCol = new TableColumn<>("Revenue");
        revenueCol.setCellValueFactory(cell -> cell.getValue().totalRevenueProperty().asString());

        TableColumn<TotalSalesReport, String> custCol = new TableColumn<>("Customers");
        custCol.setCellValueFactory(cell -> cell.getValue().totalCustomersProperty().asString());

        reportTableView.getColumns().setAll(ordersCol, revenueCol, custCol);

        TotalSalesReport report = ReportDao.getTotalSalesReport();
        if (report != null) {
            reportTableView.setItems(FXCollections.observableArrayList(report));
        }
    }

    @FXML
    public void handleBackToDashboard() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
            Stage stage = (Stage) reportTitle.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setMaximized(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
