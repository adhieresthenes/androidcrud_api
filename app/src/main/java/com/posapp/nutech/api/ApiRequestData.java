package com.posapp.nutech.api;

import com.posapp.nutech.model.ResponsModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface ApiRequestData {

    @FormUrlEncoded
    @POST("insert.php")
    Call<ResponsModel> sendData(@Field("name") String name,
                                   @Field("price") String price,
                                   @Field("description") String description);

    @GET("read.php")
    Call<ResponsModel> getData();

    @FormUrlEncoded
    @POST("update.php")
    Call<ResponsModel> updateData( @Field("id") String id,
                                    @Field("name") String name,
                                    @Field("price") String price,
                                    @Field("description") String description);

    @FormUrlEncoded
    @POST("delete.php")
    Call<ResponsModel> deleteData(@Field("id") String id);


    @FormUrlEncoded
    @POST("upload_image.php")
    Call<ResponsModel> uploadImage(
            @Field("title") String title,
            @Field("image") String image
            );
}
