package com.example.orderfoodonline.retrofit;

import com.example.orderfoodonline.models.CategoryModels;
import com.example.orderfoodonline.models.FoodDetailModels;
import com.example.orderfoodonline.models.RamenModels;
import com.example.orderfoodonline.models.UserModels;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface FoodAppApi {
    @GET("category.php")
    Call<CategoryModels> getCategory();
    //lay du lieu food tu api do vao man hinh Home
    @POST("ramen.php")
    @FormUrlEncoded
    Call<RamenModels> getRamen(
            @Field("idcate") int idcate
    );
    //Lay du lieu tu list more food
    @POST("food_category.php")
    @FormUrlEncoded
    Call<RamenModels> getMoreFood(
            @Field("idcate") int idcate
    );
    //Api user
    @POST("user.php")
    @FormUrlEncoded
    Call<UserModels> dangKi(
            @Field("email") String email,
            @Field("pass") String pass,
            @Field("username") String username
    );
    //api detail food
    @POST("fooddetail.php")
    @FormUrlEncoded
    Call<FoodDetailModels> getFooddetail(
            @Field("id") int id
    );
}
