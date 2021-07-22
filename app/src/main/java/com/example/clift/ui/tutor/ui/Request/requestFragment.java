package com.example.clift.ui.tutor.ui.Request;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;


import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;

import android.content.Intent;
import android.media.audiofx.AudioEffect;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clift.R;
import com.example.clift.data.LoginRepo;
import com.example.clift.data.model.Peticion;
import com.example.clift.data.model.Student;
import com.example.clift.data.request.Api;
import com.example.clift.data.request.PostStudentsReq;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.xml.namespace.QName;


public class requestFragment extends Fragment {
    // Constantes
    private final String SERVER_URL = "192.168.100.39"; // Local Ultreins
//    private final String SERVER_URL = "26.22.204.60"; // VPN

    List<ListElement> elements;
    private RequestViewModel mViewModel;

    // Para metodo POST
    private Api api;
    private Retrofit retrofit;
    private HashMap<String, String> params = new HashMap<>();

    // Para obtener la ubicacion actual del tutor
    private LocationManager locationManager;
    private LoginRepo loginRepo;

    public static requestFragment newInstance() {
        return new requestFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.request_fragment2, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginRepo = LoginRepo.getInstance();

        // Instanciamos el locationManager
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        // Para POST
        // Armar la URL del servidor.
        String serverUrl = "http://" + SERVER_URL + ":5000";
        retrofit = new Retrofit.Builder()
                .baseUrl(serverUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(Api.class);

        init();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RequestViewModel.class);
        // TODO: Use the ViewModel
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public  void init(){
        elements = new ArrayList<>();


        // Obtenemos la latitud y la longitud.
        try {
            if (ContextCompat.checkSelfPermission(
                    getContext(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(
                        getActivity(),
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        String tutorEmail = loginRepo.getUser().getCorreo();

        // No c como detener esta madre :'v
        locationManager.requestSingleUpdate(
                LocationManager.GPS_PROVIDER,
                location -> {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    getStudents(String.valueOf(latitude), String.valueOf(longitude), tutorEmail);
        }, null);
    }

    private void getStudents(String lat, String lng, String tutorEmail) {

        // Mandar todos estos parametros como STRING porfis gracias.
        // Del tutor
        params.put("lat", lat);
        params.put("lng", lng);
        params.put("tutorEmail", tutorEmail);

        Call<PostStudentsReq> call = api.postStudentsRequest(params);

        call.enqueue(new Callback<PostStudentsReq>() {
            @Override
            public void onResponse(Call<PostStudentsReq> call, Response<PostStudentsReq> response) {
                if (!response.isSuccessful()) {
                    Log.println(Log.INFO, "Error: ", "En el servidor.");
                    return;
                }

                PostStudentsReq res = response.body();

                // Respuesta del servidor, pero no se ha podido obtener los datos solicitados.
                if (!res.isOk()) {
                    Log.println(Log.INFO, "Error: ", "Al obtener los datos.");
                    return;
                }

                for (int i = 0; i < res.getPeticiones().length; i++) {
                    Peticion peticion = res.getPeticiones()[i];
                    Student alumno = peticion.getAlumno();

                    elements.add(new ListElement(
                            peticion.getId(),
                            peticion.isOnline() ? "#31BEB2" : "#e05248",
                            alumno.getNombre() + " " + alumno.getApellido(),
                            peticion.getDireccion(),
                            peticion.isOnline() ? "En línea" : "Presencial"));
                }

                ListAdapter listAdapter = new ListAdapter(elements, getContext(), item -> moveToDescription(item));
                RecyclerView recyclerView = getActivity().findViewById(R.id.listRecyclerView);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(listAdapter);
            }

            @Override
            public void onFailure(Call<PostStudentsReq> call, Throwable t) {
                // Sacar un Snackbar
                Log.println(Log.ERROR, "Error POST: ", t.getMessage());
            }
        });
    }





    public void moveToDescription(ListElement item){
        Intent intent = new Intent(getContext(),DescriptionActivity.class);

        intent.putExtra("ListElement", item);
        startActivity(intent);
    }

}

