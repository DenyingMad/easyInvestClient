package com.cgpanda.easyinvest.WebServices;

import com.cgpanda.easyinvest.Entity.News;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsApi {
    @GET("news")
    Call<List<News>> getAll();
}
