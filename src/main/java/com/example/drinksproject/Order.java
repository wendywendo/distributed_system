package com.example.drinksproject;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Order {
    private final SimpleStringProperty orderId;
    private final SimpleStringProperty customer;
    private final SimpleStringProperty branch;
    private final SimpleStringProperty items;
    private final SimpleDoubleProperty amount;
    private final SimpleStringProperty date;

    public Order(String orderId, String customer, String branch, String items, double amount, String date) {
        this.orderId = new SimpleStringProperty(orderId);
        this.customer = new SimpleStringProperty(customer);
        this.branch = new SimpleStringProperty(branch);
        this.items = new SimpleStringProperty(items);
        this.amount = new SimpleDoubleProperty(amount);
        this.date = new SimpleStringProperty(date);
    }

    // Getters and setters for the properties (for JavaFX bindings)

    public String getOrderId() {
        return orderId.get();
    }

    public void setOrderId(String value) {
        orderId.set(value);
    }

    public SimpleStringProperty orderIdProperty() {
        return orderId;
    }

    public String getCustomer() {
        return customer.get();
    }

    public void setCustomer(String value) {
        customer.set(value);
    }

    public SimpleStringProperty customerProperty() {
        return customer;
    }

    public String getBranch() {
        return branch.get();
    }

    public void setBranch(String value) {
        branch.set(value);
    }

    public SimpleStringProperty branchProperty() {
        return branch;
    }

    public String getItems() {
        return items.get();
    }

    public void setItems(String value) {
        items.set(value);
    }

    public SimpleStringProperty itemsProperty() {
        return items;
    }

    public double getAmount() {
        return amount.get();
    }

    public void setAmount(double value) {
        amount.set(value);
    }

    public SimpleDoubleProperty amountProperty() {
        return amount;
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String value) {
        date.set(value);
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }
}
