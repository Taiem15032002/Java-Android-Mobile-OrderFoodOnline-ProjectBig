package com.example.orderfoodonline.models;

import java.util.List;

public class RamenModels {
    private boolean success;
    private String message;
    private List<Ramen> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Ramen> getResult() {
        return result;
    }

    public void setResult(List<Ramen> result) {
        this.result = result;
    }
}
