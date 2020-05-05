package com.cgpanda.easyinvest.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.cgpanda.easyinvest.Entity.Article;
import com.cgpanda.easyinvest.WebServices.BlogApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BlogRepository {
    private static BlogRepository instance;
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://protected-cliffs-60934.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private BlogApi blogApi = retrofit.create(BlogApi.class);
    private Boolean isLoaded = false;
    private Boolean isEndOfList = false;

    private static final String TAG = "BlogRepository";

    public static BlogRepository getInstance(){
        if (instance == null) {
            instance = new BlogRepository();
        }
        return instance;
    }

    public MutableLiveData<List<Article>> getArticles(int page, int sort){
        final MutableLiveData<List<Article>> data = new MutableLiveData<>();
        setLoaded(false);
        Call<List<Article>> call = blogApi.getArticles(page, sort);
        call.enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                data.setValue(response.body());
                Log.d(TAG, "Blog onResponse: Articles loaded successfully, page: " + page + " response: " + response.body().size());
                setLoaded(true);
                if (response.body().size() == 0)
                    setEndOfList(true);
            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                Log.d(TAG, "onFailure: Articles loading failed");
            }
        });
        return data;
    }

    public Boolean getLoaded() {
        return isLoaded;
    }

    public void setLoaded(Boolean loaded) {
        isLoaded = loaded;
    }

    public Boolean getEndOfList() {
        return isEndOfList;
    }

    public void setEndOfList(Boolean endOfList) {
        isEndOfList = endOfList;
    }
}
