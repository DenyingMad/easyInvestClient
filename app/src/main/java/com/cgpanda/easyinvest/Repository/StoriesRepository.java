package com.cgpanda.easyinvest.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.cgpanda.easyinvest.Entity.Episode;
import com.cgpanda.easyinvest.Entity.Story;
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
    private ArrayList<Story> storyList = new ArrayList<>();
    private ArrayList<Episode> episodeList = new ArrayList<>();
    private static final String TAG = "StoriesRepository";
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://protected-cliffs-60934.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private StoriesApi storiesApi = retrofit.create(StoriesApi.class);

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
                storyList.clear();
                storyList.addAll(response.body());
                data.setValue(storyList);
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

    public MutableLiveData<List<Story>> getStories(){
        final MutableLiveData<List<Story>> data = new MutableLiveData<>();
        Call<List<Story>> stories = storiesApi.getAllStories();
        stories.enqueue(new Callback<List<Story>>() {
            @Override
            public void onResponse(Call<List<Story>> call, Response<List<Story>> response) {
                storyList.clear();
                storyList.addAll(response.body());
                data.setValue(storyList);
                Log.d(TAG, "onResponse: Success");
            }
            @Override
            public void onFailure(Call<List<Story>> call, Throwable t) {
                Log.d(TAG, "onFailure: Fail");
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

    static class MyComparator implements Comparator<Episode> {
        @Override
        public int compare(Episode o1, Episode o2) {
            return Long.compare(o1.getId(), o2.getId());
        }
    }


}
