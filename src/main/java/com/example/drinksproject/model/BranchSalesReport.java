package com.example.drinksproject.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class BranchSalesReport {
    private final SimpleStringProperty branchName;
    private final SimpleIntegerProperty totalOrders;
    private final SimpleDoubleProperty totalRevenue;

    public BranchSalesReport(String branchName, int totalOrders, double totalRevenue) {
        this.branchName = new SimpleStringProperty(branchName);
        this.totalOrders = new SimpleIntegerProperty(totalOrders);
        this.totalRevenue = new SimpleDoubleProperty(totalRevenue);
    }

    public String getBranchName() { return branchName.get(); }
    public int getTotalOrders() { return totalOrders.get(); }
    public double getTotalRevenue() { return totalRevenue.get(); }

    public SimpleStringProperty branchNameProperty() { return branchName; }
    public SimpleIntegerProperty totalOrdersProperty() { return totalOrders; }
    public SimpleDoubleProperty totalRevenueProperty() { return totalRevenue; }
}