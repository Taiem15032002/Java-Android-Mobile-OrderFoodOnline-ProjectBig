package com.example.orderfoodonline.retrofit;

import com.example.orderfoodonline.models.AddFoodModels;
import com.example.orderfoodonline.models.CategoryModels;
import com.example.orderfoodonline.models.FoodDetailModels;
import com.example.orderfoodonline.models.RamenModels;
import com.example.orderfoodonline.models.UserModels;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface FoodAppApi {
    @GET("category.php")
    Call<CategoryModels> getCategory();
    //lay du lieu food tu api do vao man hinh Home
    @POST("ramen.php")
    @FormUrlEncoded
    Call<RamenModels> getRamen(
            @Field("idcate") int idcate
    );
    //sanpham moi
    @POST("sanphammoi.php")
    @FormUrlEncoded
    Observable<RamenModels> getSPmoi(
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
            @Field("email") String email,
            @Field("pass") String pass
    );
    //api detail food
    @POST("fooddetail.php")
    @FormUrlEncoded
    Call<FoodDetailModels> getFooddetail(
            @Field("id") int id
    );
    //api don hang
    @POST("donhang.php")
    @FormUrlEncoded
    Observable<UserModels> createOrder(
            @Field("emailuser") String email,
            @Field("diachi") String diachi,
            @Field("mobile") String mobile,
            @Field("soluong") int soluong,
            @Field("tongtien") String tongtien,
            @Field("username") String username,
            @Field("chitietsp") String chitiet
    );
    //api them san pham
    @POST("insertSp.php")
    @FormUrlEncoded
    Observable<AddFoodModels> themSp(
            @Field("namefood") String namefood,
            @Field("foodThumb") String foodThumb,
            @Field("foodPrice") String foodPrice,
            @Field("foodDecription") String foodDecription,
            @Field("idmorefood") int idmorefood,
            @Field("category") String category
    );

    //upload
    @Multipart
    @POST("uploadimg.php")
    Call<AddFoodModels> uploadFile(@Part MultipartBody.Part file);
}
