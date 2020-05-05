package com.cgpanda.easyinvest.WebServices;

import com.cgpanda.easyinvest.Entity.Article;
import com.cgpanda.easyinvest.Entity.Quote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BlogApi {

    @GET("blog/articles")
    Call<List<Article>> getArticles(@Query("page") int page, @Query("sort") int sort);

    @GET("blog/random-quote")
    Call<Quote> getRandomQuote();
}
