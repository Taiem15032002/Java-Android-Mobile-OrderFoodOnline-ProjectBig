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
    public static User user_current = new User();
    public static List<Cart> cartList = new ArrayList<>();
    public static String ID_RECEIVE ;
    public static final String ID_SEND = "idsend";
    public static final String ID_RECEIVED = "idreceived";
    public static final String MESS = "message";
    public static final String DATE_TIME = "datetime";
    public static final String PATH_CHAT = "chat";
}
