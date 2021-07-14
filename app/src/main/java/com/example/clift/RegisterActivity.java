package com.example.clift;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.clift.data.model.RegisterForm;

public class RegisterActivity extends AppCompatActivity {

    public RegisterForm registerForm;
    public NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Remover titulo
        getSupportActionBar().hide();

        registerForm = new RegisterForm();

        ProgressBar pBRegister = findViewById(R.id.pBRegister);
        pBRegister.setVisibility(View.INVISIBLE);

        // Objetos necesarios para realizar la navegacion entre Fragments.
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.register_fragment_container);
        navController = navHostFragment.getNavController();
    }

}