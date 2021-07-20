package com.example.clift.data.model;

import java.util.Date;

public class Peticion {

    private Date fechaHora;
    private boolean isOnline;
    private Student alumno;

    public Peticion() {
    }

    public Peticion(Date fechaHora, boolean isOnline, Student alumno) {
        this.fechaHora = fechaHora;
        this.isOnline = isOnline;
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

    @Override
    public String toString() {
        return "Peticion{" +
                "fechaHora=" + fechaHora +
                ", isOnline=" + isOnline +
                ", alumno=" + alumno.toString() +
                '}';
    }
}
