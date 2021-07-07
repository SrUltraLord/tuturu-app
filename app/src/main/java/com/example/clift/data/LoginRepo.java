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

    public Result<LoggedInUser> login(String email, String password, Activity actividad) {
        Result[] resultArr = dataSource.login(email, password, actividad);

        Result.Success success = (Result.Success) resultArr[0];
        Result.Error error = (Result.Error) resultArr[1];

        if (success != null) {
            return  success;
        }

        return error;
    }

}
