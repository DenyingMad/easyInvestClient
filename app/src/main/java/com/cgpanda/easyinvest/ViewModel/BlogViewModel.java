package com.cgpanda.easyinvest.ViewModel;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cgpanda.easyinvest.Entity.Article;
import com.cgpanda.easyinvest.Entity.Quote;
import com.cgpanda.easyinvest.Repository.BlogRepository;

import java.util.ArrayList;
import java.util.List;

public class BlogViewModel extends ViewModel {
    private MutableLiveData<List<Article>> currentArticles;
    private MutableLiveData<List<Article>> newArticles;
    private MutableLiveData<Quote> quote;
    private BlogRepository blogRepository;
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public void init(int page, int sort){
        if (currentArticles != null)
            return;
        blogRepository = BlogRepository.getInstance();
        blogRepository.setEndOfList(false);
        currentArticles = blogRepository.getArticles(page, sort);
        quote = blogRepository.getQuote();
    }

    public LiveData<Quote> getQuote(){
        return quote;
    }

    public LiveData<List<Article>> getCurrentArticles(){
        return currentArticles;
    }

    public LiveData<Boolean> getIsLoading(){
        return isLoading;
    }

    public void getNewArticles(int page, int sort){
        if(!blogRepository.getEndOfList() || page == 0) {
            blogRepository.setEndOfList(false);
            LoadAsyncTask loadAsyncTask = new LoadAsyncTask();
            loadAsyncTask.execute(page, sort);
        }
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

            List<Article> currentArticleList = currentArticles.getValue();
            List<Article> newArticleList = listMutableLiveData.getValue();

            if(currentArticleList == null) {
                currentArticleList = newArticleList;
            }
            else {
                currentArticleList.addAll(newArticleList);
                currentArticles.setValue(currentArticleList);
            }
            isLoading.setValue(false);
        }

        @Override
        protected MutableLiveData<List<Article>> doInBackground(Integer... integers) {

            if (integers[0] == 0){
                currentArticles.postValue(new ArrayList<>());
            }

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
