package com.example.clift.ui.tutor.ui.Request;

import java.io.Serializable;

//package com.example.clift.ui.tutor;
//Lista de peticiones por parte de la interfaz de maestros
public class ListElement implements Serializable {
    public String docId;
    public String color;
    public String name;
    public String city;
    public String status;

    public ListElement(String docId, String color, String name, String city, String status) {
        this.docId = docId;
        this.color = color;
        this.name = name;
        this.city = city;
        this.status = status;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }
}

