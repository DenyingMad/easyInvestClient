package com.cgpanda.easyinvest.ViewModel;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cgpanda.easyinvest.Entity.Article;
import com.cgpanda.easyinvest.Repository.BlogRepository;

import java.util.List;

public class BlogViewModel extends ViewModel {
    private MutableLiveData<List<Article>> currentArticles;
    private MutableLiveData<List<Article>> newArticles;
    private BlogRepository blogRepository;
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public void init(int page, int sort){
        if (currentArticles != null)
            return;
        blogRepository = BlogRepository.getInstance();
        blogRepository.setEndOfList(false);
        currentArticles = blogRepository.getArticles(page, sort);
    }

    public LiveData<List<Article>> getCurrentArticles(){
        return currentArticles;
    }

    public void getNewArticles(int page, int sort){
        if(!blogRepository.getEndOfList()) {
            LoadAsyncTask loadAsyncTask = new LoadAsyncTask();
            loadAsyncTask.execute(page, sort);
        }
    }

    public LiveData<Boolean> getIsLoading(){
        return isLoading;
    }

    public class LoadAsyncTask extends AsyncTask<Integer, Void, MutableLiveData<List<Article>>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            newArticles = null;
            isLoading.setValue(true);
        }

        @Override
        protected void onPostExecute(MutableLiveData<List<Article>> listMutableLiveData) {
            super.onPostExecute(listMutableLiveData);
            isLoading.setValue(false);
            List<Article> currentArticleList = currentArticles.getValue();
            List<Article> newArticleList = listMutableLiveData.getValue();
            currentArticleList.addAll(newArticleList);
            currentArticles.setValue(currentArticleList);
        }

        @Override
        protected MutableLiveData<List<Article>> doInBackground(Integer... integers) {
            newArticles = blogRepository.getArticles(integers[0], integers[1]);

            while (!blogRepository.getLoaded()){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return newArticles;
        }
    }

}
