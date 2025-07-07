package com.example.drinksproject.model;

import java.io.Serializable;

public class OrderDTO implements Serializable {
    private int orderId;
    private String customer;
    private String branch;
    private String items;
    private double amount;
    private String date;

    public OrderDTO(int orderId, String customer, String branch, String items, double amount, String date) {
        this.orderId = orderId;
        this.customer = customer;
        this.branch = branch;
        this.items = items;
        this.amount = amount;
        this.date = date;
    }

    // Getters
    public int getOrderId() { return orderId; }
    public String getCustomer() { return customer; }
    public String getBranch() { return branch; }
    public String getItems() { return items; }
    public double getAmount() { return amount; }
    public String getDate() { return date; }
}
