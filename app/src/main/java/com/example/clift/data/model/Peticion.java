package com.example.clift.data.model;

import java.util.Date;

public class Peticion {

    private Date fechaHora;
    private boolean isOnline;
    private String direccion;
    private Student alumno;

    public Peticion() {
    }

    public Peticion(Date fechaHora, boolean isOnline, String direccion, Student alumno) {
        this.fechaHora = fechaHora;
        this.isOnline = isOnline;
        this.direccion = direccion;
        this.alumno = alumno;
    }

    // Metodos Get y Set
    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public Student getAlumno() {
        return alumno;
    }

    public void setAlumno(Student alumno) {
        this.alumno = alumno;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Peticion{" +
                "fechaHora=" + fechaHora +
                ", isOnline=" + isOnline +
                ", direccion=" + direccion +
                ", alumno=" + alumno.toString() +
                '}';
    }
}
