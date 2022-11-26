package com.example.orderfoodonline.retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofitinstance {
    private static Retrofit retrofit;
    private String DB = "http://192.168.110.2:8080/FoodApp/";
    private String DB1 = "https://foodapponline.000webhostapp.com/";
    private String DB2 = "https://japanfoodapp.000webhostapp.com/";
    private String DB3 = "http://japanfoodonline.onlinewebshop.net/";
    public static Retrofit getRetrofit(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://japanfoodapp.000webhostapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
