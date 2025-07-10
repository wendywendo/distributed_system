package com.example.drinksproject.model;

public class CustomerOrder {
    private String id;
    private String customerName;
    private String customerPhone;
    private int totalOrders;

    public CustomerOrder(String id, String name, String phone, int totalOrders) {
        this.id = id;
        this.customerName = name;
        this.customerPhone = (phone != null) ? phone : "0712345678";
        this.totalOrders = totalOrders;
    }

    public String getId() { return id; }
    public String getCustomerName() { return customerName; }
    public String getCustomerPhone() { return customerPhone; }
    public int getTotalOrders() { return totalOrders; }
}