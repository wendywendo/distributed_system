package com.example.drinksproject.rmi.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import com.example.drinksproject.model.Order;
import com.example.drinksproject.model.OrderDTO;

import java.util.List;
import java.util.ArrayList;


public interface OrderService extends Remote {
    int placeOrder(int customerId, int branchId) throws RemoteException;
    boolean addOrderItem(int orderId, int drinkId, int quantity, double totalPrice) throws RemoteException;
    List<OrderDTO> getAllOrders(String customer_name, String branchname) throws RemoteException;
    int getOrderCount() throws RemoteException;
    double getTotalSales() throws RemoteException;



}
