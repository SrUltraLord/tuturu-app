package com.example.clift;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.example.clift.ui.student.StudentActivity;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private Button btnLogin;
    private EditText txtEmail, txtPassword;
    private TextView txtVRegister;

    private ProgressBar pBLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Remover titulo
        getSupportActionBar().hide();

        // UI Components
        btnLogin        = findViewById(R.id.btnLogin);
        txtEmail        = findViewById(R.id.eTEmailAddress);
        txtPassword     = findViewById(R.id.eTPassword);
        txtVRegister    = findViewById(R.id.txtRegister);
        pBLogin         = findViewById(R.id.pBLogin);

        pBLogin.setVisibility(View.INVISIBLE);

        // Objeto autenticador de Firebase
        mAuth = FirebaseAuth.getInstance();

        // Listener del boton de Login, obtiene los strings de los campos de texto y los manda
        // a la funcion login.
        btnLogin.setOnClickListener(v -> {
            String email = txtEmail.getText().toString();
            String password = txtPassword.getText().toString();

            login(email, password);
        });

        // Listener encargado de cambiar a la actividad de registro.
        txtVRegister.setOnClickListener(v -> switchToActivity(RegisterActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();

        // FirebaseUser currentUser = mAuth.getCurrentUser();
        // updateUI;
    }

    /**
     * La función toma los strings de credenciales del usuario y valida que no sean strings vacíos,
     * en caso de que no sean vacíos, va a proceder con el login, esa función es timbrada de la
     * documentación de Firebase así que no entiendo mucho.
     * @param email { String }
     * @param password { String }
     */
    private void login(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(MainActivity.this, "Completa todos los campos.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        pBLogin.setVisibility(View.VISIBLE);
        btnLogin.setEnabled(false);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "signInWithEmail:success");
                        // FirebaseUser user = mAuth.getCurrentUser();
                        switchToActivity(StudentActivity.class);

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("TAG", "signInWithEmail:failure", task.getException());
                        Toast.makeText(MainActivity.this, "Usuario o contraseña incorrecto(s).",
                                Toast.LENGTH_SHORT).show();
                    }

                    pBLogin.setVisibility(View.INVISIBLE);
                    btnLogin.setEnabled(true);
                });


    }

    private void switchToActivity(Class actividad) {
        Intent switchActivityIntent = new Intent(this, actividad);
        startActivity(switchActivityIntent);
    }

}