package com.example.drinksproject.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class BranchPerformanceReport {
    private final SimpleStringProperty branchName;
    private final SimpleIntegerProperty activeCustomers;
    private final SimpleDoubleProperty averageOrderValue;

    public BranchPerformanceReport(String branchName, int activeCustomers, double averageOrderValue) {
        this.branchName = new SimpleStringProperty(branchName);
        this.activeCustomers = new SimpleIntegerProperty(activeCustomers);
        this.averageOrderValue = new SimpleDoubleProperty(averageOrderValue);
    }

    public String getBranchName() { return branchName.get(); }
    public int getActiveCustomers() { return activeCustomers.get(); }
    public double getAverageOrderValue() { return averageOrderValue.get(); }

    public SimpleStringProperty branchNameProperty() { return branchName; }
    public SimpleIntegerProperty activeCustomersProperty() { return activeCustomers; }
    public SimpleDoubleProperty averageOrderValueProperty() { return averageOrderValue; }
}