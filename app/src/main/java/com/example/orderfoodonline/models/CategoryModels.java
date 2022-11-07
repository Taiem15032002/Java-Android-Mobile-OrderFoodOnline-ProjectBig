package com.example.orderfoodonline.models;

import java.util.List;

public class CategoryModels {
    private boolean success;
    private String message;
    private List<Category> result;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<Category> getResult() {
        return result;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setResult(List<Category> result) {
        this.result = result;
    }
}
