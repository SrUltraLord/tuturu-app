package com.example.clift.data.request;

public class PostSimple {

    private boolean ok;
    private String message;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "PostSimple{" +
                "ok=" + ok +
                ", message='" + message + '\'' +
                '}';
    }
}
