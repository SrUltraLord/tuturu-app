package com.example.clift.data;

import android.app.Activity;

import com.example.clift.data.model.LoggedInUser;

public class LoginRepo {

    private static volatile LoginRepo instance;
    private LoginDataSource dataSource;
    private LoggedInUser user = null;

    private LoginRepo(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepo getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepo(dataSource);
        }

        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
    }
    
}
