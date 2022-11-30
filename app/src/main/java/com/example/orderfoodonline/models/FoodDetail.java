package com.example.orderfoodonline.models;

public class FoodDetail {
    int id , price;
    String food_name,category,decripstion,foodThumb;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDecripstion() {
        return decripstion;
    }

    public void setDecripstion(String decripstion) {
        this.decripstion = decripstion;
    }

    public String getFoodThumb() {
        return foodThumb;
    }

    public void setFoodThumb(String foodThumb) {
        this.foodThumb = foodThumb;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
