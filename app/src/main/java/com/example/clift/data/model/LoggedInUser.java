package com.example.clift.data.model;

public class LoggedInUser {

    private String userId, displayName, type, ci, telefono, correo;

    public LoggedInUser() {
        this.userId = "";
        this.displayName = "";
        this.type = "";
        this.ci = "";
    }

    public LoggedInUser(String userId, String displayName, String type, String ci, String telefono, String correo) {
        this.userId = userId;
        this.displayName = displayName;
        this.type = type;
        this.ci = ci;
        this.telefono = telefono;
        this.correo = correo;
    }

    // Metodos Get y Set
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
