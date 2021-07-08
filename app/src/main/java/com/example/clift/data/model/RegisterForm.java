package com.example.clift.data.model;

public class RegisterForm {
    private String type, name, lastname, ci,email, password, phone;
    private String[] subjects;

    public RegisterForm() {
    }

    public RegisterForm(String type, String name, String lastname, String ci, String email, String password, String phone, String[] subjects) {
        this.type = type;
        this.name = name;
        this.lastname = lastname;
        this.ci = ci;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.subjects = subjects;
    }
}
