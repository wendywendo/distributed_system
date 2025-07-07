package com.example.drinksproject.model;

import javafx.beans.property.*;

import java.io.Serializable;

public class Order{
    private IntegerProperty orderId;
    private StringProperty customer;
    private StringProperty branch;
    private StringProperty items;
    private DoubleProperty amount;
    private StringProperty date;

    public Order(int orderId, String customer, String branch, String items, double amount, String date) {
        this.orderId = new SimpleIntegerProperty(orderId);
        this.customer = new SimpleStringProperty(customer);
        this.branch = new SimpleStringProperty(branch);
        this.items = new SimpleStringProperty(items);
        this.amount = new SimpleDoubleProperty(amount);
        this.date = new SimpleStringProperty(date);
    }

    // Property getters for JavaFX binding
    public IntegerProperty orderIdProperty() {
        return orderId;
    }

    public StringProperty customerProperty() {
        return customer;
    }

    public StringProperty branchProperty() {
        return branch;
    }

    public StringProperty itemsProperty() {
        return items;
    }

    public DoubleProperty amountProperty() {
        return amount;
    }

    public StringProperty dateProperty() {
        return date;
    }

    // Standard getters and setters
    public int getOrderId() {
        return orderId.get();
    }

    public void setOrderId(int orderId) {
        this.orderId.set(orderId);
    }

    public String getCustomer() {
        return customer.get();
    }

    public void setCustomer(String customer) {
        this.customer.set(customer);
    }

    public String getBranch() {
        return branch.get();
    }

    public void setBranch(String branch) {
        this.branch.set(branch);
    }

    public String getItems() {
        return items.get();
    }

    public void setItems(String items) {
        this.items.set(items);
    }

    public double getAmount() {
        return amount.get();
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }
}
