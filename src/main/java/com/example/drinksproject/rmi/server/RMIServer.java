package com.example.drinksproject.rmi.server;

import com.example.drinksproject.rmi.shared.CustomerService;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class RMIServer {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099); // default RMI port
            CustomerService service = new CustomerServiceImpl();
            Naming.rebind("rmi://localhost/CustomerService", service);
            System.out.println("✅ RMI Server started and CustomerService bound.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
