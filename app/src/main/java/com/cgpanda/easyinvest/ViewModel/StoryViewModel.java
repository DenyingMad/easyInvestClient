package com.cgpanda.easyinvest.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cgpanda.easyinvest.Entity.Episode;
import com.cgpanda.easyinvest.Entity.Story;
import com.cgpanda.easyinvest.Repository.StoriesRepository;

import java.util.Comparator;
import java.util.List;

public class StoryViewModel extends ViewModel {
    private MutableLiveData<List<Story>> stories;
    private MutableLiveData<List<Episode>> episodes;
    private StoriesRepository storiesRepository;
    public void init(){
        if (stories != null)
            return;
        storiesRepository = StoriesRepository.getInstance();
        stories = storiesRepository.getStories();

    }
    public LiveData<List<Story>> getStories(){
        return stories;
    }

    public LiveData<List<Episode>> getEpisodes(String id){
        episodes = storiesRepository.getEpisodes(id);

        return episodes;
    }

}
