package com.example.drinksproject.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CustomerSalesReport {
    private final SimpleStringProperty customerName;
    private final SimpleDoubleProperty totalSpent;
    private final SimpleIntegerProperty orders;

    public CustomerSalesReport(String customerName, double totalSpent, int orders) {
        this.customerName = new SimpleStringProperty(customerName);
        this.totalSpent = new SimpleDoubleProperty(totalSpent);
        this.orders = new SimpleIntegerProperty(orders);
    }

    public String getCustomerName() { return customerName.get(); }
    public double getTotalSpent() { return totalSpent.get(); }
    public int getOrders() { return orders.get(); }

    public SimpleStringProperty customerNameProperty() { return customerName; }
    public SimpleDoubleProperty totalSpentProperty() { return totalSpent; }
    public SimpleIntegerProperty ordersProperty() { return orders; }
}