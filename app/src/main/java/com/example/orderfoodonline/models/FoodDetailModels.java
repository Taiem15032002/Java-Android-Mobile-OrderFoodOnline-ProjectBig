package com.example.orderfoodonline.models;

import java.util.List;

public class FoodDetailModels {
    private boolean success;
    private String message;
    private List<FoodDetail> result;

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

    public List<FoodDetail> getResult() {
        return result;
    }

    public void setResult(List<FoodDetail> result) {
        this.result = result;
    }
}
