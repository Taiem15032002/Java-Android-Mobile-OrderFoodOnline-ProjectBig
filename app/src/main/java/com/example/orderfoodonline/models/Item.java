package com.example.orderfoodonline.models;

public class Item {
    int iddonhang,id,soluong,idsanpham;
    String food_name;
    String category;
    String foodThumb;
    String decripstion;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    String price;

    public int getIddonhang() {
        return iddonhang;
    }

    public void setIddonhang(int iddonhang) {
        this.iddonhang = iddonhang;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getIdsanpham() {
        return idsanpham;
    }

    public void setIdsanpham(int idsanpham) {
        this.idsanpham = idsanpham;
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

    public String getFoodThumb() {
        return foodThumb;
    }

    public void setFoodThumb(String foodThumb) {
        this.foodThumb = foodThumb;
    }

    public String getDecripstion() {
        return decripstion;
    }

    public void setDecripstion(String decripstion) {
        this.decripstion = decripstion;
    }
}
