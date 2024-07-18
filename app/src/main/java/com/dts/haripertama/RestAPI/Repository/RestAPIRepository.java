package com.dts.haripertama.RestAPI.Repository;

import com.dts.haripertama.RestAPI.PostModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestAPIRepository {
    @GET("posts")
    Call<List<PostModel>> getPosts();

    @GET("posts/{id}")
    Call<PostModel> getPost();

    @POST("posts")
    Call<PostModel> createPost();
}