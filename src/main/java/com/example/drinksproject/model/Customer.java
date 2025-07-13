package com.example.drinksproject.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Customer {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty phone;

    public Customer(int id, String name, String phone) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.phone = new SimpleStringProperty(phone);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty customerIdProperty() {
        return id;
    }

    public String getName() { return name.get(); }

    public SimpleStringProperty customerNameProperty() {
        return name;
    }

    public String getPhone() {
        return phone.get();
    }

    public SimpleStringProperty customerPhoneProperty() {
        return phone;
    }

    @Override
    public String toString() {
        return getName() + " (" + getPhone() + ")";
    }
}
