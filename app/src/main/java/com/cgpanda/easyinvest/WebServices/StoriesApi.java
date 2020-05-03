package com.cgpanda.easyinvest.WebServices;

import com.cgpanda.easyinvest.Entity.Category;
import com.cgpanda.easyinvest.Entity.Episode;
import com.cgpanda.easyinvest.Entity.Story;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface StoriesApi {

    @GET("stories/featured")
    Call<List<Story>> getFeaturedStories();

    @GET("stories/categories")
    Call<List<Category>> getAllCategories();

    @GET("stories/episodes/{id}")
    Call<List<Episode>> getEpisodesById(@Path("id") String id);

    @GET("stories/categories/limited")
    Call<List<Category>> getCategories();
}
