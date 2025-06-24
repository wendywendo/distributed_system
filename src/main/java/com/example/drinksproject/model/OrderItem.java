package com.example.drinksproject.model;

import javafx.beans.property.*;

public class OrderItem {
    private final IntegerProperty drinkId = new SimpleIntegerProperty();
    private final StringProperty drinkName = new SimpleStringProperty();
    private final IntegerProperty quantity = new SimpleIntegerProperty();
    private final DoubleProperty totalPrice = new SimpleDoubleProperty();

    public OrderItem(int drinkId, String drinkName, int quantity, double totalPrice) {
        this.drinkId.set(drinkId);
        this.drinkName.set(drinkName);
        this.quantity.set(quantity);
        this.totalPrice.set(totalPrice);
    }

    public int getDrinkId() { return drinkId.get(); }
    public String getDrinkName() { return drinkName.get(); }
    public int getQuantity() { return quantity.get(); }
    public double getTotalPrice() { return totalPrice.get(); }

    public StringProperty drinkNameProperty() { return drinkName; }
    public IntegerProperty quantityProperty() { return quantity; }
    public DoubleProperty totalPriceProperty() { return totalPrice; }
}

