package com.example.clift.data;

import com.example.clift.data.model.LoggedInUser;
import com.google.firebase.auth.FirebaseUser;

public class LoginRepo {

    private static volatile LoginRepo instance;
    private FirebaseUser fBUser = null;
    private LoggedInUser user = null;

    private LoginRepo() {  }

    // Singleton para que varias actividades puedan consumir el mismo objeto.
    public static LoginRepo getInstance() {
        if (instance == null) {
            instance = new LoginRepo();
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return fBUser != null;
    }

    public void logout() {
        fBUser = null;
    }

    public void setFirebaseUser(FirebaseUser fbu) {
        this.fBUser = fbu;
    }

    public void setUser(LoggedInUser user) {
        this.user = user;
    }

    public FirebaseUser getfBUser() {
        return fBUser;
    }

    public LoggedInUser getUser() {
        return user;
    }
}
