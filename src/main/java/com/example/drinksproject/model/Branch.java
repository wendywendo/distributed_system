package com.example.drinksproject.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

public class Branch implements Serializable {
    private final SimpleIntegerProperty branchId;
    private final SimpleStringProperty branchName;

    public Branch() {
        this.branchId = new SimpleIntegerProperty();
        this.branchName = new SimpleStringProperty();
    }

    public Branch(int branchId, String branchName) {
        this.branchId = new SimpleIntegerProperty(branchId);
        this.branchName = new SimpleStringProperty(branchName);
    }

    public int getBranchId() {
        return branchId.get();
    }

    public void setBranchId(int branchId) {
        this.branchId.set(branchId);
    }

    public SimpleIntegerProperty branchIdProperty() {
        return branchId;
    }

    public String getBranchName() {
        return branchName.get();
    }

    public void setBranchName(String branchName) {
        this.branchName.set(branchName);
    }

    public SimpleStringProperty branchNameProperty() {
        return branchName;
    }

    @Override
    public String toString() {
        return getBranchName();
    }
}
