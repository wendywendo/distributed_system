package com.example.drinksproject.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Stock {
    private final SimpleStringProperty branchName;
    private final SimpleStringProperty drinkName;
    private final SimpleIntegerProperty currentStock;

    public Stock(String branchName, String drinkName, int currentStock) {
        this.branchName = new SimpleStringProperty(branchName);
        this.drinkName = new SimpleStringProperty(drinkName);
        this.currentStock = new SimpleIntegerProperty(currentStock);
    }

    public String getBranchName() {
        return branchName.get();
    }

    public SimpleStringProperty branchNameProperty() {
        return branchName;
    }

    public String getDrinkName() {
        return drinkName.get();
    }

    public SimpleStringProperty drinkNameProperty() {
        return drinkName;
    }

    public int getCurrentStock() {
        return currentStock.get();
    }

    public SimpleIntegerProperty currentStockProperty() {
        return currentStock;
    }
}
