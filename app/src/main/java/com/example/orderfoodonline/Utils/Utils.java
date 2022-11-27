package com.example.orderfoodonline.Utils;

import com.example.orderfoodonline.models.AddFoodModels;
import com.example.orderfoodonline.models.Cart;
import com.example.orderfoodonline.models.User;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    //Ham luu tru du lieu add to cart
    public static String hinh = Utils.BASE_URL+"images/";
    public static final String BASE_URL = "https://japanfoodapp.000webhostapp.com/";
    public static List<Cart> cartList = new ArrayList<>();
    public static User user_current = new User();
}
