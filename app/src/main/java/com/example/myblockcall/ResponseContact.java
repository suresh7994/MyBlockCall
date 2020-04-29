package com.example.myblockcall;

import retrofit2.http.Field;

class ResponseContact {
    String sucess;
     String message;

    public String getSucess() {
        return sucess;
    }

    public void setSucess(String sucess) {
        this.sucess = sucess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
