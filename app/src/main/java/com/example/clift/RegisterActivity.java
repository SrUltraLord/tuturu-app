package com.example.clift;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.example.clift.data.model.RegisterForm;

public class RegisterActivity extends AppCompatActivity {

    public RegisterForm registerForm;
    public NavController navController;
    private NavHostFragment navHostFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Remover titulo
        getSupportActionBar().hide();

        registerForm = new RegisterForm();

        // Objetos necesarios para realizar la navegacion entre Fragments.
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.register_fragment_container);
        navController = navHostFragment.getNavController();
    }

}