package com.example.drinksproject.model;

public class Branch {
    private int branchId;
    private String branchName;

    public Branch(int branchId, String branchName) {
        this.branchId = branchId;
        this.branchName = branchName;
    }

    public int getBranchId() {
        return branchId;
    }

    @Override
    public String toString() {
        return branchName;
    }
}
