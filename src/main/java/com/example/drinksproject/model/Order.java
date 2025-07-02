package com.example.drinksproject.model;

public class Order {
    private int orderId;
    private String customerName;
    private String branchName;
    private String items;
    private int amount;
    private String date;

    public Order(int orderId, String customerName, String branchName, String items, int amount, String date) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.branchName = branchName;
        this.items = items;
        this.amount = amount;
        this.date = date;
    }
}
