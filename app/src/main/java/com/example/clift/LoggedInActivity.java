package com.example.clift;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.clift.data.model.Student;
import com.example.clift.data.request.Api;
import com.example.clift.data.request.PostSimple;
import com.example.clift.data.request.PostStudentsReq;
import com.firebase.geofire.GeoFireUtils;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQueryBounds;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoggedInActivity extends AppCompatActivity {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Para metodo POST
    private Api api;
    private Retrofit retrofit;
    private HashMap<String, String> params = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        // Para POST
        // Armar la URL del servidor.
        // Esto se debe cambiar a la IP de la VPN.
        // IP -> 26.22.204.60
        String serverUrl = "http://192.168.100.39:5000";

        retrofit = new Retrofit.Builder()
                .baseUrl(serverUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(Api.class);

        getStudents();
    }

    /*
    private void getSimpleRes() {
        params.put("lat", "-0.288356");
        params.put("lng", "-78.469716");
        params.put("tutorEmail", "jsins@espe.edu.ec");

        Call<PostSimple> call = api.getPost(params);

        call.enqueue(new Callback<PostSimple>() {
            @Override
            public void onResponse(Call<PostSimple> call, Response<PostSimple> response) {
                if (!response.isSuccessful()) {
                    Log.println(Log.INFO, "Error: ", "En el servidor.");
                    return;
                }

                PostSimple res = response.body();

                if (!res.isOk()) {
                    Log.println(Log.INFO, "Error: ", "Al obtener los datos.");
                    return;
                }

                Log.println(Log.INFO, "Exito: ", res.toString());
            }

            @Override
            public void onFailure(Call<PostSimple> call, Throwable t) {
                Log.println(Log.ERROR, "Error: ", t.getMessage());
                Log.println(Log.ERROR, "Error: ", "Efe");
            }
        });
    }
    */

    private void getStudents() {

        // Mandar todos estos parametros como STRING porfis gracias.
        // Del tutor
        params.put("lat", "-0.288356");
        params.put("lng", "-78.469716");
        params.put("tutorEmail", "jsins@espe.edu.ec");

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

                // Tenemos los datos, imprimir.
                Log.println(Log.INFO, "Hurra: ", res.toString());

                // Ejemplo en JSON de la respuesta del servidor
//                {
//                    "ok": true,
//                        "peticiones": [
    //                      {
    //                        "fechaHora": "2021-07-15T20:00:00.000Z",
    //                        "isOnline": false,
    //                        "materia": "Física",
    //                        "direccion": "Mira",
    //                        "alumno": {
    //                              "correo": "jdrosero1@espe.edu.ec",
    //                              "telefono": "0987079644",
    //                              "apellido": "Rosero",
    //                              "nombre": "Llandely",
    //                              "tipo": "student",
    //                              "ci": "0401786140"
    //                       }
    //                    },
    //                    ...
    //                  ]
//                 }

                for (int i = 0; i < res.getPeticiones().length; i++) {
                    Student alumno = res.getPeticiones()[i].getAlumno();

                    // Para sacar datos del almuno
                    Log.println(Log.INFO, "Nombre: ", alumno.getNombre());
                    Log.println(Log.INFO, "Apellido: ", alumno.getApellido());
                    Log.println(Log.INFO, "CI: ", alumno.getCi());

                    // Para sacar datos de la peticion
                    Log.println(Log.INFO, "Ubicacion: ", res.getPeticiones()[i].getDireccion());
                }

            }

            @Override
            public void onFailure(Call<PostStudentsReq> call, Throwable t) {
                Log.println(Log.ERROR, "Error POST: ", t.getMessage());
            }
        });
    }

}