package com.example.orderfoodonline.models.EventBus;

import com.example.orderfoodonline.models.Ramen;

public class UpdateFood {
    Ramen ramen;

    public Ramen getRamen() {
        return ramen;
    }

    public void setRamen(Ramen ramen) {
        this.ramen = ramen;
    }

    public UpdateFood(Ramen ramen) {
        this.ramen = ramen;
    }
}
