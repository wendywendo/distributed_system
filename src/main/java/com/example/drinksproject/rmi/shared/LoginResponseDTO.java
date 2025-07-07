package com.example.drinksproject.rmi.shared;

import java.io.Serializable;

public class LoginResponseDTO implements Serializable {
    private String username;
    private int branchId;
    private String branchName;

    public LoginResponseDTO(String username, int branchId, String branchName) {
        this.username = username;
        this.branchId = branchId;
        this.branchName = branchName;
    }

    public String getUsername() {
        return username;
    }

    public int getBranchId() {
        return branchId;
    }

    public String getBranchName() {
        return branchName;
    }
}
