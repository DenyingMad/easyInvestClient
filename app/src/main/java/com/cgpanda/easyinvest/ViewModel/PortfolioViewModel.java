package com.cgpanda.easyinvest.ViewModel;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cgpanda.easyinvest.Entity.UserPortfolio;
import com.cgpanda.easyinvest.Repository.PortfolioRepository;

public class PortfolioViewModel extends ViewModel {
    private static PortfolioRepository portfolioRepository;

    private static MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    private static MutableLiveData<UserPortfolio> userPortfolio = new MutableLiveData<>();


    public void init(String apiKey){
        if (portfolioRepository != null)
            return;
        portfolioRepository = PortfolioRepository.getInstance();
        GetUserPortfolio getUserPortfolio = new GetUserPortfolio();
        getUserPortfolio.execute(apiKey);
    }

    public LiveData<Boolean> getIsLoading(){
        return isLoading;
    }

    public LiveData<UserPortfolio> getUserPortfolio(){
        return userPortfolio;
    }

    public static class GetUserPortfolio extends AsyncTask<String, Void, UserPortfolio> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isLoading.postValue(true);
        }

        @Override
        protected void onPostExecute(UserPortfolio result) {
            super.onPostExecute(result);
            userPortfolio.postValue(result);
            isLoading.postValue(false);
        }

        @Override
        protected UserPortfolio doInBackground(String... strings) {
            return portfolioRepository.getUserPortfolio(strings[0]);
        }
    }
}
