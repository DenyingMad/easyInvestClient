package com.cgpanda.easyinvest.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cgpanda.easyinvest.Entity.Category;
import com.cgpanda.easyinvest.Entity.Story;
import com.cgpanda.easyinvest.Repository.StoriesRepository;

import java.util.List;

public class ArchiveViewModel extends ViewModel {
    private MutableLiveData<List<Category>> categories;
    private StoriesRepository storiesRepository;
    public void init(){
        if (categories != null)
            return;
        storiesRepository = StoriesRepository.getInstance();
    }

    public LiveData<List<Category>> getAllCategories(){
        if (categories == null){
            categories = storiesRepository.getAllCategories();
        }
        return categories;
    }
}
