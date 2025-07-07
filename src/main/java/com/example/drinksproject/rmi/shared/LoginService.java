package com.example.drinksproject.rmi.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LoginService extends Remote {
    LoginResponseDTO login(String username, String password, String branchName) throws RemoteException;
}
