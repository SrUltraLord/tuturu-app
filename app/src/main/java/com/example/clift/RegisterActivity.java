package com.example.clift;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.example.clift.data.model.RegisterForm;

public class RegisterActivity extends AppCompatActivity {

    public RegisterForm registerForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerForm = new RegisterForm();

    }

}