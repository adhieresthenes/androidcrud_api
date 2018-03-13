package com.posapp.nutech.api;

import com.posapp.nutech.model.ResponsModel;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;


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



    /*if upload using multipart
    * https://www.youtube.com/watch?v=9tX85f8vZ5I*/
    @Multipart
    @POST("apidroid/upload_image.php")
    Call<Retroserver> upload(
            @Header("Authorization") String authorization,
            @PartMap Map<String, RequestBody> map
    );
}
