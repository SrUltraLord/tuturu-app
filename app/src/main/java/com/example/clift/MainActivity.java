package com.example.clift;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clift.data.LoginRepo;
import com.example.clift.data.model.LoggedInUser;
import com.example.clift.ui.tutor.TutorActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.example.clift.ui.student.StudentActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    // UI components
    private Button btnLogin;
    private EditText txtEmail, txtPassword;
    private TextView txtVRegister;
    private ProgressBar pBLogin;

    // Para guardar las credenciales en memoria.
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private LoginRepo loginRepo;

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

        loginRepo = LoginRepo.getInstance();

        // Aplicamos modo privado para que otras apps no puedan ver esta informacion.
        sharedPreferences = getSharedPreferences("credentials", MODE_PRIVATE);

        // Intentamos loggear automaticamente.
        tryAutoLogin();

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
    }

    /**
     * La función toma los strings de credenciales del usuario y valida que no sean strings vacíos,
     * en caso de que no sean vacíos, va a proceder con el login, esa función es timbrada de la
     * documentación de Firebase así que no entiendo mucho. Cuando la tarea es satisfactoria, se
     * renderiza la interfaz de alumno o tutor y se guardan las credenciales en la memoria del
     * dispositivo.
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
                        FirebaseUser user = mAuth.getCurrentUser();
                        loginRepo.setFirebaseUser(user);

                        // Guardamos las credenciales en Sharedpreferences para evitar que el
                        // usuario tenga que ingresar sus credenciales cada vez que abre la app.
                        editor = sharedPreferences.edit();
                        editor.putString("email", email);
                        editor.putString("password", password);
                        editor.apply(); // Guardamos los cambios.

                        // Validar que, en caso de que sea estudiante renderizar la interfaz de
                        // estudiante, si es profesor, la interfaz de tutor.
                        db.collection("usuarios")
                                .whereEqualTo("correo", user.getEmail())
                                .get()
                                .addOnCompleteListener(taskDoc -> {
                                    if (taskDoc.isSuccessful()) {
                                        for (QueryDocumentSnapshot doc : taskDoc.getResult()) {
                                            String type = doc.getData().get("tipo").toString();
                                            String name = doc.getData().get("nombre").toString();
                                            String lastName = doc.getData().get("apellido").toString();
                                            String telefono = doc.getData().get("telefono").toString();
                                            String ci = doc.getData().get("ci").toString();
                                            String correo = doc.getData().get("correo").toString();


                                            loginRepo.setUser(new LoggedInUser(
                                                    user.getUid(),
                                                    name + " " + lastName,
                                                    type,
                                                    ci,
                                                    telefono,
                                                    correo
                                            ));

                                            // Si es estudiante, ir a StudentActivity.
                                            if (type.equals("student")) {
                                                switchToActivity(StudentActivity.class);
                                            } else {
                                                switchToActivity(TutorActivity.class);
                                            }
                                        }

                                    }
                                });


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

    /**
     * Funcion que lee las Shared Preferences y en caso de que no se tenga las credenciales, no
     * hace el login de manera automatica.
     */
    private void tryAutoLogin() {
        String email    = sharedPreferences.getString("email", null);
        String password = sharedPreferences.getString("password", null);

        if (email != null && password != null) {
            login(email, password);
        }
    }

    private void switchToActivity(Class actividad) {
        Intent switchActivityIntent = new Intent(this, actividad);
        startActivity(switchActivityIntent);
    }

}