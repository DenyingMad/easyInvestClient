package com.cgpanda.easyinvest.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cgpanda.easyinvest.Entity.Episode;
import com.cgpanda.easyinvest.Repository.StoriesRepository;

import java.util.List;

public class EpisodeViewModel extends ViewModel {
    private MutableLiveData<List<Episode>> episodes;
    private StoriesRepository storiesRepository;

    public void init(){
        if (episodes != null)
            return;
        storiesRepository = StoriesRepository.getInstance();
    }
    public LiveData<List<Episode>> getEpisodes(String id){
        episodes = storiesRepository.getEpisodes(id);
        //TODO Добавить значок загрузки
        return episodes;
    }
}
