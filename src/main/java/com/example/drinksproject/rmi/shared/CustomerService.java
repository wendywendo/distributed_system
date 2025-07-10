package com.example.drinksproject.rmi.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.example.drinksproject.dao.CustomerDao;
import com.example.drinksproject.model.Customer;


public interface CustomerService extends Remote {
    boolean registerCustomer(String name, String phone) throws RemoteException;
    List<Customer> getAllCustomers() throws RemoteException;
    int getCustomerCount() throws RemoteException;

}
