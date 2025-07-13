package com.example.drinksproject.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class TotalSalesReport {
    private SimpleIntegerProperty totalOrders;
    private SimpleDoubleProperty totalRevenue;
    private SimpleIntegerProperty totalCustomers;

    public TotalSalesReport(int totalOrders, double totalRevenue, int totalCustomers) {
        this.totalOrders = new SimpleIntegerProperty(totalOrders);
        this.totalRevenue = new SimpleDoubleProperty(totalRevenue);
        this.totalCustomers = new SimpleIntegerProperty(totalCustomers);
    }

    public int getTotalOrders() { return totalOrders.get(); }
    public double getTotalRevenue() { return totalRevenue.get(); }
    public int getTotalCustomers() { return totalCustomers.get(); }

    public SimpleIntegerProperty totalOrdersProperty() {
        return totalOrders;
    }

    public SimpleDoubleProperty totalRevenueProperty() {
        return totalRevenue;
    }

    public SimpleIntegerProperty totalCustomersProperty() {
        return totalCustomers;
    }
}