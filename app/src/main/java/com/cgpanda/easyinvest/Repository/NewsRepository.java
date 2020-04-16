package com.cgpanda.easyinvest.Repository;

import androidx.lifecycle.MutableLiveData;

import com.cgpanda.easyinvest.Entity.News;
import com.cgpanda.easyinvest.WebServices.NewsApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsRepository {
    private static NewsRepository instance;
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://protected-cliffs-60934.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private NewsApi newsApi = retrofit.create(NewsApi.class);


    public static NewsRepository getInstance(){
        if (instance == null)
            instance = new NewsRepository();
        return instance;
    }

    public MutableLiveData<List<News>> getAll(){
        final MutableLiveData<List<News>> data = new MutableLiveData<>();
        Call<List<News>> news = newsApi.getAll();
        news.enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {

            }
        });
        return data;
    }
}
