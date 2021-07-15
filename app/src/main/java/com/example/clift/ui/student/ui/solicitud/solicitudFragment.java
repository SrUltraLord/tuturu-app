package com.example.clift.ui.student.ui.solicitud;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.clift.R;
import com.example.clift.ui.student.ui.map.MapsFragment;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

public class solicitudFragment extends Fragment {

    private SolicitudViewModel mViewModel;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private EditText btnCalendar, txtAddress, txtTime;

    public Bundle pasoDatos = new Bundle();
    private double lat, lng;
    private int tHour, tMinute;



    public static solicitudFragment newInstance() {
        return new solicitudFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.solicitud_fragment, container, false);

        btnCalendar = root.findViewById(R.id.etPlannedDate);
        txtTime = root.findViewById(R.id.etTime);

        // Creacion de calendario
        btnCalendar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Calendar kal = Calendar.getInstance();
                int year = kal.get(Calendar.YEAR);
                int month = kal.get(Calendar.MONTH);
                int day = kal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getContext(),
                        android.R.style.Theme_DeviceDefault_Dialog,
                        dateSetListener, year, month, day);

                dialog.show();

            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month+1;
                Log.d(TAG, "onDateSet: dd/mm/yyyy" + day + "/" + month + "/" + year);

                String date = day + "/" + month + "/" + year;
                btnCalendar.setText(date);

            }
        };

        //Creacion de timer

        txtTime.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                tHour = hourOfDay;
                                tMinute = minute;

                                Calendar calendar = Calendar.getInstance();

                                calendar.set(0,0,0,tHour,tMinute);

                                String time = tHour + ":" + tMinute;
                                SimpleDateFormat format1 = new SimpleDateFormat("hh:mm aa");

                                try {
                                    Date date = format1.parse(time);

                                    txtTime.setText(format1.format(date));

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                        },12,0,false
                );
                timePickerDialog.updateTime(tHour,tMinute);

                timePickerDialog.show();

            }
        });


        //Utilizacion API Google

        txtAddress = root.findViewById(R.id.Address);

        Places.initialize(getActivity().getApplicationContext(), "AIzaSyCaPPtcnohBcc7TMHJBAhhgg3oXR1sxGS0");

        txtAddress.setFocusable(false);
        txtAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS,
                        Place.Field.LAT_LNG, Place.Field.NAME);

                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
                        fieldList).build(getContext());

                startActivityForResult(intent, 100);

            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SolicitudViewModel.class);
        // TODO: Use the ViewModel

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK ){
            Place place = Autocomplete.getPlaceFromIntent(data);
            txtAddress.setText(place.getAddress());

            lat = place.getLatLng().latitude;
            lng = place.getLatLng().longitude;

            pasoDatos.putDouble("lat", lat);
            pasoDatos.putDouble("lng", lng);

            getParentFragmentManager().setFragmentResult("key", pasoDatos);

        }else if(resultCode == AutocompleteActivity.RESULT_ERROR){
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getActivity().getApplicationContext(),status.getStatusMessage(),
                    Toast.LENGTH_SHORT).show();

        }
    }
}
