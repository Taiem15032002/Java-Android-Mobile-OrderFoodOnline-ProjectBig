package com.example.orderfoodonline.models;

public class Category {
    private int id;
    private String category, categoryThumb, categoryDescription;

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getCategoryThumb() {
        return categoryThumb;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCategoryThumb(String categoryThumb) {
        this.categoryThumb = categoryThumb;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }
}
