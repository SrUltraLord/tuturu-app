package com.example.clift.ui.register;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.clift.R;
import com.example.clift.RegisterActivity;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterOptionsFragment extends Fragment {

    private ToggleButton tBStudent, tBTutor;
    private RegisterActivity registerActivity;

    public RegisterOptionsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_options, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Componente para la navegacion
        registerActivity = (RegisterActivity) getActivity();

        // Componentes UI
        tBStudent   = view.findViewById(R.id.tBStudent);
        tBTutor     = view.findViewById(R.id.tBTutor);
        Button btnNext = view.findViewById(R.id.btnNext);

        addListener(tBStudent, "student");
        addListener(tBTutor, "tutor");

        tBStudent.setTextOff("");
        tBTutor.setTextOff("");

        btnNext.setOnClickListener(v -> {
            if (!(tBStudent.isChecked() || tBTutor.isChecked())) {
                Toast.makeText(registerActivity, "Por favor, selecciona una de las dos opciones.",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            registerActivity.navController.navigate(R.id.action_registerOptionsFragment_to_registerFormFragment);
        });
    }

    private void addListener(ToggleButton tb, String userType) {
        tb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                boolean isStudent = userType.equals("student");
                tBTutor.setChecked(!isStudent);
                tBStudent.setChecked(isStudent);

                registerActivity.registerForm.setType(userType);
            }
        });
    }
}