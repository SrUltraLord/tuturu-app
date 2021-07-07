package com.example.clift.data.model;

public class LoggedInUser {

    private String userId, displayName, type;

    public LoggedInUser() {}

    public LoggedInUser(String userId, String displayName, String type) {
        this.userId = userId;
        this.displayName = displayName;
        this.type = type;
    }

    // Metodos Get y Set
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
