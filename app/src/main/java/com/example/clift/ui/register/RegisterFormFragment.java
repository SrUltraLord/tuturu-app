package com.example.clift.ui.register;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clift.MainActivity;
import com.example.clift.R;
import com.example.clift.RegisterActivity;
import com.example.clift.utils.validations.Validation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class RegisterFormFragment extends Fragment {

    // Firestore
    private final FirebaseFirestore db    = FirebaseFirestore.getInstance();
    private final FirebaseAuth mAuth      = FirebaseAuth.getInstance();

    private LinearLayout linearLayoutSubjects;
    private RegisterActivity registerActivity;

    private EditText eTName, eTLastname, eTCI, eTPhone, eTEmail, eTPassword;
    private ProgressBar pBRegister;
    private Button btnRegister;

    private Validation validador;

    private Map<String, Object> userDoc;
    private Map<String, String> materias;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userDoc = new HashMap<>();
        materias = new HashMap<>();
        validador   = new Validation();

        registerActivity = (RegisterActivity) getActivity();

        linearLayoutSubjects = getActivity().findViewById(R.id.linear_layout_subjects);

        TextView tVSubjects = getActivity().findViewById(R.id.tVSubjects);

        eTName      = getActivity().findViewById(R.id.eTName);
        eTLastname  = getActivity().findViewById(R.id.eTLastname);
        eTCI        = getActivity().findViewById(R.id.eTCI);
        eTPhone     = getActivity().findViewById(R.id.eTPhone);
        eTEmail     = getActivity().findViewById(R.id.eTEmail);
        eTPassword  = getActivity().findViewById(R.id.eTPassword);
        pBRegister  = getActivity().findViewById(R.id.pBRegister);
        btnRegister = getActivity().findViewById(R.id.btnRegister);

        pBRegister.setVisibility(View.INVISIBLE);

        // Si es Tutor, renderizar Materias
        if (registerActivity.registerForm.getType().equals("tutor")) {
            tVSubjects.setVisibility(View.VISIBLE);
            fetchSubjects(view);
        } else {
            tVSubjects.setVisibility(View.INVISIBLE);
        }

        btnRegister.setOnClickListener(v -> registerUser());
    }


    private void registerUser() {

        if (!isValidForm()) {
            Toast.makeText(registerActivity, "Los datos ingresados no son correctos.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        String nameText     = eTName.getText().toString().trim();
        String lastNameText = eTLastname.getText().toString().trim();
        String ciText       = eTCI.getText().toString().trim();
        String phoneText    = eTPhone.getText().toString().trim();
        String emailText    = eTEmail.getText().toString().trim();
        String passwordText = eTPassword.getText().toString().trim();

        String userType     = registerActivity.registerForm.getType();
        ArrayList<String> idMaterias;

        userDoc.put("nombre", nameText);
        userDoc.put("apellido", lastNameText);
        userDoc.put("ci", ciText);
        userDoc.put("telefono", phoneText);
        userDoc.put("tipo", userType);
        userDoc.put("correo", emailText);

        // En caso de que el usuario sea docente, agregar materias al arreglo.
        if (userType.equals("tutor")) {
            idMaterias = new ArrayList<>();
            for (int i = 0; i < linearLayoutSubjects.getChildCount(); i++) {
                CheckBox cb = (CheckBox) linearLayoutSubjects.getChildAt(i);
                String materiaId = materias.get(cb.getText());

                if (cb.isChecked()) {
                    idMaterias.add(materiaId);
                }
            }
            userDoc.put("materias", idMaterias);
        }


        // Iniciar proceso de Registro
        pBRegister.setVisibility(View.VISIBLE);
        btnRegister.setEnabled(false);

        mAuth.createUserWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener((task -> {
                    if (task.isSuccessful()) {
                        db.collection("usuarios")
                                .add(userDoc)
                                .addOnSuccessListener(documentReference -> {
                                    Toast.makeText(registerActivity, "Usuario creado.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent switchActivityIntent = new Intent(registerActivity, MainActivity.class);
                                    startActivity(switchActivityIntent);
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(registerActivity, "El usuario no ha podido ser creado.",
                                            Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        Toast.makeText(registerActivity, "El usuario no ha podido ser creado.",
                                Toast.LENGTH_SHORT).show();
                    }
                    pBRegister.setVisibility(View.INVISIBLE);
                    btnRegister.setEnabled(true);
                }));

    }

    private void fetchSubjects(View view) {
        db.collection("materias")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            addDynamicCheckBox(document.getId(), document.getData().get("nombre").toString(), view);
                            materias.put(document.getData().get("nombre").toString(), document.getId());
                        }
                    } else {
                        Log.w("TAG", "Error getting documents.", task.getException());
                    }
                });
    }

    private void addDynamicCheckBox(String id, String name, View view) {
        CheckBox ch = new CheckBox(view.getContext());
        ch.setText(name);
        ch.setTextColor(Color.WHITE);
        linearLayoutSubjects.addView(ch);
    }

    private boolean isValidForm() {
        // Textos de los Edit Text.
        String nameText     = eTName.getText().toString().trim();
        String lastNameText = eTLastname.getText().toString().trim();
        String ciText       = eTCI.getText().toString().trim();
        String phoneText    = eTPhone.getText().toString().trim();
        String emailText    = eTEmail.getText().toString().trim();
        String passwordText = eTPassword.getText().toString().trim();

        // Verificar campos no vacios.
        if (TextUtils.isEmpty(nameText)) {
            eTName.setError("Debes ingresar un nombre");
            return false;
        } else {
            eTName.setError(null);
        }

        if (TextUtils.isEmpty(lastNameText)) {
            eTLastname.setError("Debes ingresar un apellido");
            return false;
        } else {
            eTLastname.setError(null);
        }

        if (TextUtils.isEmpty(ciText)) {
            eTCI.setError("Debes ingresar una cédula de identidad");
            return false;
        }

        if (TextUtils.isEmpty(phoneText)) {
            eTPhone.setError("Debes ingresar un teléfono celular");
            return false;
        } else {
            eTPhone.setError(null);
        }

        if (TextUtils.isEmpty(emailText)) {
            eTEmail.setError("Debes ingresar un correo electrónico");
            return false;
        } else {
            eTEmail.setError(null);
        }

        if (TextUtils.isEmpty(passwordText)) {
            eTPassword.setError("Debes ingresar una contraseña");
            return false;
        } else {
            eTPassword.setError(null);
        }

        // Validar la cedula
        if (!validador.isValidCI(ciText)) {
            eTCI.setError("Debes ingresar una cédula de identidad válida");
            return false;
        } else {
            eTCI.setError(null);
        }

        return true;
    }
}