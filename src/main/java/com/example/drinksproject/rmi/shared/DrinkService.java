package com.example.drinksproject.rmi.shared;

import com.example.drinksproject.model.Drink;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface DrinkService extends Remote {
    List<Drink> getAllDrinks() throws RemoteException;
}
