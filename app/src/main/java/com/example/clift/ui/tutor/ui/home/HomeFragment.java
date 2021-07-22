package com.example.clift.ui.tutor.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.clift.R;
import com.example.clift.data.LoginRepo;
import com.example.clift.data.model.LoggedInUser;
import com.example.clift.databinding.FragmentHomeBinding;

import org.jetbrains.annotations.NotNull;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    private EditText eTNombre, eTCedula, eTEmail, eTTelefono;
    private LoginRepo loginRepo;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // UI Components
        eTNombre    = view.findViewById(R.id.pr_name_tutor);
        eTCedula    = view.findViewById(R.id.pr_ci_tutor);
        eTEmail     = view.findViewById(R.id.pr_mail_tutor);
        eTTelefono  = view.findViewById(R.id.pr_phone_tutor);

        loginRepo = LoginRepo.getInstance();

        LoggedInUser user = loginRepo.getUser();

        setEditTextValues(user);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setEditTextValues(LoggedInUser user) {
        eTNombre.setText(user.getDisplayName());
        eTCedula.setText(user.getCi());
        eTEmail.setText(user.getCorreo());
        eTTelefono.setText(user.getTelefono());
    }
}