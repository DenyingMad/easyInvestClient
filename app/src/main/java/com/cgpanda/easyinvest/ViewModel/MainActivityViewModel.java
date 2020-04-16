package com.cgpanda.easyinvest.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cgpanda.easyinvest.Entity.Episode;
import com.cgpanda.easyinvest.Entity.Equity;
import com.cgpanda.easyinvest.Entity.News;
import com.cgpanda.easyinvest.Entity.Story;
import com.cgpanda.easyinvest.Repository.EquityRepository;
import com.cgpanda.easyinvest.Repository.NewsRepository;
import com.cgpanda.easyinvest.Repository.StoriesRepository;

import java.util.List;

public class MainActivityViewModel extends ViewModel {
    private MutableLiveData<List<Story>> stories;
    private MutableLiveData<List<Equity>> equities;
    private MutableLiveData<List<News>> news;

    private StoriesRepository storiesRepository;
    private EquityRepository equityRepository;
    private NewsRepository newsRepository;

    public void init(){
        if (stories != null && equities != null)
            return;
        storiesRepository = StoriesRepository.getInstance();
        equityRepository = EquityRepository.getInstance();
        newsRepository = NewsRepository.getInstance();
    }

    //TODO загружать только несколько(7) историй для отображения на главном экране
    public LiveData<List<Story>> getStories(){
        if(stories == null){
            stories = new MutableLiveData<>();
            stories = storiesRepository.getStories();
        }
        return stories;
    }


    public LiveData<List<Equity>> getQuotes() {
        if (equities == null) {
            equities = equityRepository.getAll();
        }
        return equities;
    }


    public LiveData<List<News>> getNews(){
        if (news == null)
            news = newsRepository.getAll();
        return news;
    }

}
