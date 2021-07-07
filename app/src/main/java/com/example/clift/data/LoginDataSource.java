package com.example.clift.data;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.clift.data.model.LoggedInUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

public class LoginDataSource {

    private FirebaseAuth mAuth;

    public Result[] login(String email, String password, Activity actividad) {

        final Result.Success[] resSuccess = {null};
        final Result.Error[] resError = {null};

        Result[] res;

        try {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(actividad, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                resError[0] = new Result.Error(new Exception());
                                return;
                            }

                            FirebaseUser user = mAuth.getCurrentUser();
                            resSuccess[0] = new Result.Success(user);
                        }
                    });
        } catch(Exception e) {
            resError[0] = new Result.Error(new IOException("Error logging in", e));
        }

        res = new Result[]{resSuccess[0], resError[0]};
        return res;
    }


    public void logout() {
        mAuth.signOut();
    }

}
