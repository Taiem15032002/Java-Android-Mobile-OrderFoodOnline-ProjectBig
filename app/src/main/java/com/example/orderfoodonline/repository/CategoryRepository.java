package com.example.orderfoodonline.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.orderfoodonline.models.CategoryModels;
import com.example.orderfoodonline.retrofit.FoodAppApi;
import com.example.orderfoodonline.retrofit.Retrofitinstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {
    private final FoodAppApi foodAppApi;
    public CategoryRepository(){
        foodAppApi = Retrofitinstance.getRetrofit().create(FoodAppApi.class);
    }
    public MutableLiveData<CategoryModels> getCategory(){
        MutableLiveData<CategoryModels> data = new MutableLiveData<>();
        foodAppApi.getCategory().enqueue(new Callback<CategoryModels>() {
            @Override
            public void onResponse(Call<CategoryModels> call, Response<CategoryModels> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<CategoryModels> call, Throwable t) {
                Log.d("logg", t.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }
}
