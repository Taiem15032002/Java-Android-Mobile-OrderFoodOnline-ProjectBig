package com.example.orderfoodonline.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.orderfoodonline.models.CategoryModels;
import com.example.orderfoodonline.models.RamenModels;
import com.example.orderfoodonline.retrofit.FoodAppApi;
import com.example.orderfoodonline.retrofit.Retrofitinstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllRepository {
    private final FoodAppApi foodAppApi;
    public AllRepository(){
        foodAppApi = Retrofitinstance.getRetrofit().create(FoodAppApi.class);
    }
    public MutableLiveData<RamenModels> getAll(int idcate){
        MutableLiveData<RamenModels> data = new MutableLiveData<>();
        foodAppApi.getSPmoi(idcate).enqueue(new Callback<RamenModels>() {
            @Override
            public void onResponse(Call<RamenModels> call, Response<RamenModels> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<RamenModels> call, Throwable t) {
                Log.d("logg", t.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }
}
