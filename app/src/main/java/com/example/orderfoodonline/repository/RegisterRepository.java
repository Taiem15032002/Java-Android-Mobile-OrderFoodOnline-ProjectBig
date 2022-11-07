package com.example.orderfoodonline.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;


import com.example.orderfoodonline.models.UserModels;
import com.example.orderfoodonline.retrofit.FoodAppApi;
import com.example.orderfoodonline.retrofit.Retrofitinstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterRepository {
    private FoodAppApi foodAppApi;

    public RegisterRepository() {
        foodAppApi = Retrofitinstance.getRetrofit().create(FoodAppApi.class);
    }
    public MutableLiveData<UserModels> getUsers(String email, String pass, String username){
        MutableLiveData<UserModels> data = new MutableLiveData<>();
        foodAppApi.dangKi(email, pass, username).enqueue(new Callback<UserModels>() {
            @Override
            public void onResponse(Call<UserModels> call, Response<UserModels> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<UserModels> call, Throwable t) {
                Log.d("logg",t.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }
}
