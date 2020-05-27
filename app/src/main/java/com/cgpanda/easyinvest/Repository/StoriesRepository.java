package com.cgpanda.easyinvest.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.cgpanda.easyinvest.Entity.Category;
import com.cgpanda.easyinvest.Entity.Episode;
import com.cgpanda.easyinvest.Entity.Story;
import com.cgpanda.easyinvest.WebServices.RetrofitService;
import com.cgpanda.easyinvest.WebServices.StoriesApi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StoriesRepository {
    private static StoriesRepository instance;
    private ArrayList<Episode> episodeList = new ArrayList<>();
    private static final String TAG = "StoriesRepository";
    private StoriesApi storiesApi = RetrofitService.cteateService(StoriesApi.class);

    public static StoriesRepository getInstance(){
        if(instance == null)
            instance = new StoriesRepository();
        return instance;
    }

    public MutableLiveData<List<Story>> getFeaturedStories(){
        final MutableLiveData<List<Story>> data = new MutableLiveData<>();
        Call<List<Story>> stories = storiesApi.getFeaturedStories();
        stories.enqueue(new Callback<List<Story>>() {
            @Override
            public void onResponse(Call<List<Story>> call, Response<List<Story>> response) {
                data.setValue(response.body());
                Log.d(TAG, "onResponse: success featured stories load");
            }

            @Override
            public void onFailure(Call<List<Story>> call, Throwable t) {
                //TODO if null
                Log.d(TAG, "onFailure: featured stories load fail");
            }
        });
        return data;
    }

    public MutableLiveData<List<Episode>> getEpisodes(String id){
        final MutableLiveData<List<Episode>> data = new MutableLiveData<>();
        Call<List<Episode>> episodes = storiesApi.getEpisodesById(id);
        episodes.enqueue(new Callback<List<Episode>>() {
            @Override
            public void onResponse(Call<List<Episode>> call, Response<List<Episode>> response) {
                episodeList.clear();
                episodeList.addAll(response.body());
                MyComparator comparator = new MyComparator();
                episodeList.sort(comparator);
                data.setValue(episodeList);
                Log.d(TAG, "onResponse: Success");
            }
            @Override
            public void onFailure(Call<List<Episode>> call, Throwable t) {
                Log.d(TAG, "onFailure: Fail");
            }
        });
        return data;
    }

    public MutableLiveData<List<Category>> getAllCategories(){
        final MutableLiveData<List<Category>> data = new MutableLiveData<>();
        Call<List<Category>> call = storiesApi.getAllCategories();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                Log.d(TAG, "onResponse: success get all categories");
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.d(TAG, "onFailure: failed get all categories");
            }
        });
        return data;
    }

    public MutableLiveData<List<Category>> getCategories(){
        final MutableLiveData<List<Category>> data = new MutableLiveData<>();
        Call<List<Category>> call = storiesApi.getCategories();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                Log.d(TAG, "onResponse: success get only 3 categories");
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.d(TAG, "onFailure: failed get only 3 categories");
            }
        });
        return data;
    }

    static class MyComparator implements Comparator<Episode> {
        @Override
        public int compare(Episode o1, Episode o2) {
            return Long.compare(o1.getId(), o2.getId());
        }
    }


}
