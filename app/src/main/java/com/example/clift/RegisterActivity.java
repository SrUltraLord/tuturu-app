package com.example.clift;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ToggleButton;

public class RegisterActivity extends AppCompatActivity {

    private ToggleButton tBStudent, tBTutor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tBStudent   = findViewById(R.id.tBStudent);
        tBTutor     = findViewById(R.id.tBTutor);


    }
}