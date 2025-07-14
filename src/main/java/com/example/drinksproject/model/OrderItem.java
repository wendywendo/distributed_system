package com.example.drinksproject.model;

import javafx.beans.property.*;

public class OrderItem {
    private final IntegerProperty drinkId = new SimpleIntegerProperty();
    private final StringProperty drinkName = new SimpleStringProperty();
    private IntegerProperty quantity = new SimpleIntegerProperty();
    private final DoubleProperty price = new SimpleDoubleProperty();

    public OrderItem(int drinkId, String drinkName, int quantity, double price) {
        this.drinkId.set(drinkId);
        this.drinkName.set(drinkName);
        this.quantity.set(quantity);
        this.price.set(price);
    }

    public int getDrinkId() { return drinkId.get(); }
    public String getDrinkName() { return drinkName.get(); }
    public int getQuantity() { return quantity.get(); }
    public double getPrice() { return price.get(); }

    public void setQuantity(int quantity) {
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    public double getTotalPrice() {
        double totalPrice = this.quantity.get() * this.price.get();
        return totalPrice;
    }

    public StringProperty drinkNameProperty() { return drinkName; }
    public IntegerProperty quantityProperty() { return quantity; }
    public DoubleProperty totalPriceProperty() { return price; }
}

