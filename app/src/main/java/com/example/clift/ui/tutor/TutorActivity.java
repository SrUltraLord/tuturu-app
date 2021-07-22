package com.example.clift.ui.tutor;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.clift.R;
import com.example.clift.data.LoginRepo;
import com.example.clift.databinding.ActivityTutorBinding;
import com.google.android.material.navigation.NavigationView;

public class TutorActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityTutorBinding binding;
    private LoginRepo loginRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTutorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarTutor.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,R.id.nav_request) //  R.id.nav_gallery, R.id.nav_slideshow,
                .setDrawerLayout(drawer)
                .build();

        loginRepo = LoginRepo.getInstance();

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.nh_tutor_name);
        TextView navEmail = headerView.findViewById(R.id.nh_tutor_email);

        navUsername.setText(loginRepo.getUser().getDisplayName());
        navEmail.setText(loginRepo.getUser().getCorreo());

        // Esta wea da error :c
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_tutor);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tutor, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_tutor);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}