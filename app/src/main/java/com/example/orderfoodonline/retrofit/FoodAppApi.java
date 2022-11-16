package com.example.orderfoodonline.retrofit;

import com.example.orderfoodonline.models.CategoryModels;
import com.example.orderfoodonline.models.FoodDetailModels;
import com.example.orderfoodonline.models.RamenModels;
import com.example.orderfoodonline.models.UserModels;

import io.reactivex.rxjava3.core.Observable;
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
    Observable<UserModels> dangKi(
            @Field("email") String email,
            @Field("pass") String pass,
            @Field("username") String username,
            @Field("mobile") String mobile
    );
    //Api dang nhap
    @POST("dangnhap.php")
    @FormUrlEncoded
    Observable<UserModels> dangNhap(
            @Field("username") String username,
            @Field("pass") String pass
    );
    @POST("reset.php")
    @FormUrlEncoded
    Observable<UserModels> quenMatKhau(
            @Field("email") String username,
            @Field("password") String pass,
            @Field("repassword") String repass

    );
    //api detail food
    @POST("fooddetail.php")
    @FormUrlEncoded
    Call<FoodDetailModels> getFooddetail(
            @Field("id") int id
    );

}
