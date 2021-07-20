package com.example.clift.data.request;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Api {

    @POST("/")
    Call<PostSimple> getPost(@Body HashMap<String, String> params);

    @Headers("Content-type: application/json")
    @POST("/buscar-alumnos")
    Call<PostStudentsReq> postStudentsRequest(@Body HashMap<String, String> params);
}
