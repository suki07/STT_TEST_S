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

    public static final String API_URL = "https://c4d547b34c9b.ngrok.io/"

    @POST("/catalog/posts/")
    Call<PostItem> post_posts(@Body PostItem post);

    @PATCH("/catalog/posts/{pk}/")
    Call<PostItem> patch_posts(@Path("pk") int pk, @Body PostItem post);

    @DELETE("/catalog/posts/{pk}/")
    Call<PostItem> delete_posts(@Path("pk") int pk);

    @GET("/catalog/posts/")
    Call<List<PostItem>> get_posts();

    @GET("/catalog/posts/{pk}/")
    Call<PostItem> get_post_pk(@Path("pk") int pk);

}