package com.example.orderfoodonline.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.orderfoodonline.models.FoodDetailModels;
import com.example.orderfoodonline.models.RamenModels;
import com.example.orderfoodonline.retrofit.FoodAppApi;
import com.example.orderfoodonline.retrofit.Retrofitinstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailFoodRepository {
    private final FoodAppApi foodAppApi;

    public DetailFoodRepository() {
        foodAppApi = Retrofitinstance.getRetrofit().create(FoodAppApi.class);
    }

    public MutableLiveData<FoodDetailModels> getFooddetail(int id) {
        MutableLiveData<FoodDetailModels> data = new MutableLiveData<>();
        foodAppApi.getFooddetail(id).enqueue(new Callback<FoodDetailModels>() {
            @Override
            public void onResponse(Call<FoodDetailModels> call, Response<FoodDetailModels> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<FoodDetailModels> call, Throwable t) {
                Log.d("logg", t.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }
}
