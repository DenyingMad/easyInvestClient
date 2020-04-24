package com.cgpanda.easyinvest.WebServices;

import com.cgpanda.easyinvest.Entity.Episode;
import com.cgpanda.easyinvest.Entity.Story;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface StoriesApi {

    @GET("stories/featured")
    Call<List<Story>> getFeaturedStories();

    @GET("stories")
    Call<List<Story>> getAllStories();

    @GET("episodes/{id}")
    Call<List<Episode>> getEpisodesById(@Path("id") String id);
}
