package com.cgpanda.easyinvest.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cgpanda.easyinvest.Entity.Story;
import com.cgpanda.easyinvest.Repository.StoriesRepository;

import java.util.List;

public class ArchiveViewModel extends ViewModel {
    private MutableLiveData<List<Story>> stories;
    private StoriesRepository storiesRepository;
    public void init(){
        if (stories != null)
            return;
        storiesRepository = StoriesRepository.getInstance();
    }
    public LiveData<List<Story>> getStories(){
        if(stories == null){
            stories = new MutableLiveData<>();
            stories = storiesRepository.getStories();
        }
        return stories;
    }

}
