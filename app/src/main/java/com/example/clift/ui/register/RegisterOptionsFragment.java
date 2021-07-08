package com.example.clift.ui.register;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import com.example.clift.R;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterOptionsFragment extends Fragment {

    private ToggleButton tBStudent, tBTutor;

    public RegisterOptionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_options, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tBStudent   = view.findViewById(R.id.tBStudent);
        tBTutor     = view.findViewById(R.id.tBTutor);

        addListener(tBStudent, "student");
        addListener(tBTutor, "tutor");
    }

    private void addListener(ToggleButton tb, String userType) {
        tb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                boolean isStudent = userType.equals("student");
                tBTutor.setChecked(!isStudent);
                tBStudent.setChecked(isStudent);
            }
        });
    }
}