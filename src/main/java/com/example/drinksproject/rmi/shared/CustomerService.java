package com.example.drinksproject.rmi.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CustomerService extends Remote {
    boolean registerCustomer(String name, String phone) throws RemoteException;
}
