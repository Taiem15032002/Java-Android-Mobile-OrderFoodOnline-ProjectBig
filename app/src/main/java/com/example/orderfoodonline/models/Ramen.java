package com.example.orderfoodonline.models;

public class Ramen {
    private int id, idCategory, idMoreFood;
    private String namefood, foodThumb,foodDecription, foodPrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public int getIdMoreFood() {
        return idMoreFood;
    }

    public void setIdMoreFood(int idMoreFood) {
        this.idMoreFood = idMoreFood;
    }

    public String getNamefood() {
        return namefood;
    }

    public void setNamefood(String namefood) {
        this.namefood = namefood;
    }

    public String getFoodThumb() {
        return foodThumb;
    }

    public void setFoodThumb(String foodThumb) {
        this.foodThumb = foodThumb;
    }

    public String getFoodDecription() {
        return foodDecription;
    }

    public void setFoodDecription(String foodDecription) {
        this.foodDecription = foodDecription;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }
}
