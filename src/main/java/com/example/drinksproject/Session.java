package com.example.drinksproject;

public class Session {

    private static String username;
    private static int branchId;
    private static String branchName;

    public static void set(String username, int branchId, String branchName) {
        Session.username = username;
        Session.branchId = branchId;
        Session.branchName = branchName;
    }

    public static String getUsername() {
        return username;
    }

    public static int getBranchId() {
        return branchId;
    }

    public static String getBranchName() {
        return branchName;
    }

    public static void clear() {
        username = null;
        branchId = -1;
        branchName = null;
    }

}
