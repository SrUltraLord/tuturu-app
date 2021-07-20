package com.example.clift.data.model;

public class Student {

    private String ci, correo, tipo, telefono, nombre, apellido;

    public Student() {  }

    public Student(String ci, String correo, String tipo, String telefono, String nombre, String apellido) {
        this.ci = ci;
        this.correo = correo;
        this.tipo = tipo;
        this.telefono = telefono;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    // Metodos Get y Set
    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    @Override
    public String toString() {
        return "Student{" +
                "ci='" + ci + '\'' +
                ", correo='" + correo + '\'' +
                ", tipo='" + tipo + '\'' +
                ", telefono='" + telefono + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                '}';
    }
}
