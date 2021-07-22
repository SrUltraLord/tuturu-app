package com.example.clift.ui.tutor.ui.Request;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.example.clift.R;

public class DescriptionActivity extends AppCompatActivity {
    TextView titleDescriptionTextView;
    TextView cityDescription;
    TextView statusDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        ListElement element = (ListElement) getIntent().getSerializableExtra("ListElement");
        titleDescriptionTextView = findViewById(R.id.titleDescriptionTextView);
        cityDescription = findViewById(R.id.cityDescription);
        statusDescription = findViewById(R.id.statusDescription);


        titleDescriptionTextView.setText(element.getName());
        titleDescriptionTextView.setTextColor(Color.parseColor(element.getColor()));

        cityDescription.setText(element.getCity());

        statusDescription.setText(element.getStatus());
        statusDescription.setTextColor(Color.GRAY);


    }
}