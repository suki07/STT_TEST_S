package com.example.mouth;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MyAPI{

    public static final String API_URL = "http://9409cc326937.ngrok.io/";

    @POST("/catalog/posts/")
    Call<ResponseItem> post_posts(@Body PostItem data);

    @PATCH("/catalog/posts/{pk}/")
    Call<ResponseItem> patch_posts(@Path("pk") int pk, @Body PostItem post);

    @DELETE("/catalog/posts/{pk}/")
    Call<ResponseItem> delete_posts(@Path("pk") int pk);

    @GET("/catalog/posts/")
    Call<List<ResponseItem>> get_posts();

    @GET("/catalog/posts/{pk}/")
    Call<ResponseItem> get_post_pk(@Path("pk") int pk);

}