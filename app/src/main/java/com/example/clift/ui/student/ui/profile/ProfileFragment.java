package com.example.clift.ui.student.ui.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.clift.R;
import com.example.clift.data.LoginRepo;
import com.example.clift.data.model.LoggedInUser;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private EditText eTNombre, eTCedula, eTEmail, eTTelefono;
    private LoginRepo loginRepo;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // UI Components
        eTNombre    = getActivity().findViewById(R.id.pr_name);
        eTCedula    = getActivity().findViewById(R.id.pr_ci);
        eTEmail     = getActivity().findViewById(R.id.pr_mail);
        eTTelefono  = getActivity().findViewById(R.id.pr_phone);

        loginRepo = LoginRepo.getInstance();

        LoggedInUser user = loginRepo.getUser();

        setEditTextValues(user);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    private void setEditTextValues(LoggedInUser user) {
        eTNombre.setText(user.getDisplayName());
        eTCedula.setText(user.getCi());
        eTEmail.setText(user.getCorreo());
        eTTelefono.setText(user.getTelefono());
    }
}