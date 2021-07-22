package com.example.clift.ui.student.ui.solicitud;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.clift.MainActivity;
import com.example.clift.R;
import com.example.clift.data.LoginRepo;
import com.example.clift.ui.student.ui.map.MapsFragment;
import com.firebase.geofire.GeoFireUtils;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

public class solicitudFragment extends Fragment {

    private SolicitudViewModel mViewModel;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private EditText btnCalendar, txtAddress, txtTime, txtSolicitud;
    private Spinner spSubject;
    private LoginRepo loginRepo;
    private CheckBox isOnline;
    private Button btnSolicitar;

    public Bundle pasoDatos = new Bundle();
    private double lat, lng;
    private int tHour, tMinute;

    private final FirebaseFirestore db    = FirebaseFirestore.getInstance();

    private Map<String, Object> userDoc;
    private List<Materia> materias;



    public static solicitudFragment newInstance() {
        return new solicitudFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.solicitud_fragment, container, false);

        btnCalendar = root.findViewById(R.id.etPlannedDate);
        txtTime = root.findViewById(R.id.etTime);
        spSubject = root.findViewById(R.id.Subjects);
        txtSolicitud = root.findViewById(R.id.petition);
        isOnline = root.findViewById(R.id.checkBox);
        btnSolicitar = root.findViewById(R.id.aceptar);

        loginRepo = LoginRepo.getInstance();

        userDoc = new HashMap<>();

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
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                tHour = hourOfDay;
                                tMinute = minute;


                                String time = tHour + ":" + tMinute;
                                SimpleDateFormat f24hours = new SimpleDateFormat("HH:mm");

                                try {
                                    Date date = f24hours.parse(time);
                                    SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
                                    txtTime.setText(format1.format(date));

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                        },12,0,true
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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

        isOnline.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(isOnline.isChecked()){
                   txtAddress.setVisibility(View.INVISIBLE);
                }else{
                    txtAddress.setVisibility(View.VISIBLE);
                }
            }
        });


        fetchSubjects();

        btnSolicitar.setOnClickListener(v -> agregarSolicitud());

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

            getChildFragmentManager().setFragmentResult("key", pasoDatos);

        }else if(resultCode == AutocompleteActivity.RESULT_ERROR){
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getActivity().getApplicationContext(),status.getStatusMessage(),
                    Toast.LENGTH_SHORT).show();

        }


    }



    private void agregarSolicitud (){
        HashMap<String, Object> latLng = new HashMap<>();
        String solicitud = txtSolicitud.getText().toString().trim();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date fechaHora;
        System.out.println(btnCalendar.getText().toString() + ' ' +txtTime.getText().toString());
        try {
            fechaHora = format.parse(btnCalendar.getText().toString() + ' ' +txtTime.getText().toString());

        } catch (ParseException e) {
            e.printStackTrace();
            fechaHora = new Date();
        }

        String direccion = txtAddress.getText().toString().trim();
        double latitud = lat;
        double longitud = lng;

        String hash = GeoFireUtils.getGeoHashForLocation(new GeoLocation(lat, lng));

        String Mat = "";

        for(Materia m:materias){
            if(m.nombre.equals(spSubject.getSelectedItem().toString())){
                Mat = m.id;
                break;
            }
        }

        String correo = loginRepo.getUser().getCorreo();

        boolean enLinea = isOnline.isChecked();

        latLng.put("lat",latitud);
        latLng.put("lng", longitud);

        userDoc.put("mensaje", solicitud);
        userDoc.put("correoAlumno", correo);
        userDoc.put("direccion", direccion);
        userDoc.put("estado", "pendiente");
        userDoc.put("fechaHora", fechaHora);
        userDoc.put("idMateria", Mat);
        userDoc.put("isOnline", enLinea);
        userDoc.put("ubicacion", latLng);
        userDoc.put("ubicacionHash", hash);

//        System.out.println(userDoc.toString());

        db.collection("reuniones")
                .add(userDoc)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(getActivity() , "Solicitud enviada.",
                            Toast.LENGTH_SHORT).show();
                            cleanField();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getActivity(), "La solicitud no ha sido enviada.",
                            Toast.LENGTH_SHORT).show();
                });

    }

    private void fetchSubjects() {
        materias = new ArrayList<>();
        db.collection("materias")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String id = document.getId();
                            String nombre = document.getData().get("nombre").toString();
                            materias.add(new Materia(id, nombre));
                        }
                        ArrayAdapter<Materia> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, materias);
                        spSubject.setAdapter(arrayAdapter);
                    } else {
                        Log.w("TAG", "Error getting documents.", task.getException());
                    }
                });
    }

    private void cleanField(){
        txtSolicitud.setText("");
        btnCalendar.setText("");
        txtTime.setText("");
        txtAddress.setText("");
    }
}
