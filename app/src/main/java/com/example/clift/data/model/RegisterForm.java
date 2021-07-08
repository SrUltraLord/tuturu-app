package com.example.clift.data.model;

public class RegisterForm {
    private String type, name, lastname, ci,email, password, phone;
    private String[] subjects;

    public RegisterForm() {
        type = "";
        name = "";
        lastname = "";
        ci = "";
        email = "";
        password = "";
        phone = "";
        subjects = new String[]{};
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String[] getSubjects() {
        return subjects;
    }

    public void setSubjects(String[] subjects) {
        this.subjects = subjects;
    }
}
