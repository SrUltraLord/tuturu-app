package com.example.clift.data.request;

import com.example.clift.data.model.Peticion;

import java.util.Arrays;

public class PostStudentsReq {

    private boolean ok;
    private Peticion[] peticiones;

    public PostStudentsReq() {  }

    public PostStudentsReq(boolean isOk, Peticion[] peticiones) {
        this.ok = isOk;
        this.peticiones = peticiones;
    }

    // Metodos Get y Set
    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public Peticion[] getPeticiones() {
        return peticiones;
    }

    public void setPeticiones(Peticion[] peticiones) {
        this.peticiones = peticiones;
    }

    @Override
    public String toString() {
        return "PostStudentsReq{" +
                "ok=" + ok +
                ", peticiones=" + Arrays.toString(peticiones) +
                '}';
    }
}
