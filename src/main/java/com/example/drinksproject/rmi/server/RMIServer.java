package com.example.drinksproject.rmi.server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import com.example.drinksproject.rmi.shared.CustomerService;
import com.example.drinksproject.rmi.shared.DrinkService;
import com.example.drinksproject.rmi.shared.LoginService;
import com.example.drinksproject.rmi.shared.OrderService;

public class RMIServer {
    public static void main(String[] args) {
        try {
            // Start RMI registry on port 1099 (only if not already started)
            LocateRegistry.createRegistry(1099);

            // Bind CustomerService
            CustomerService customerService = new CustomerServiceImpl();
            Naming.rebind("rmi://localhost/CustomerService", customerService);
            System.out.println("✅ CustomerService bound.");

            // Bind OrderService
            OrderService orderService = new OrderServiceImpl();
            Naming.rebind("rmi://localhost/OrderService", orderService);
            System.out.println("✅ OrderService bound.");

            DrinkService drinkService = new DrinkServiceImpl();
            Naming.rebind("rmi://localhost/DrinkService", drinkService);
            System.out.println("✅ DrinkService bound.");

            LoginService loginService = new LoginServiceImpl();
            Naming.rebind("rmi://localhost/LoginService", loginService);
            System.out.println("✅ LoginService bound.");

            System.out.println("🚀 RMI Server running successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
