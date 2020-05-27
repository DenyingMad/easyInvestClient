package com.cgpanda.easyinvest.ViewModel;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cgpanda.easyinvest.Entity.PortfolioSecurities.PortfolioSecurities;
import com.cgpanda.easyinvest.Entity.PortfolioSecurities.Securities;
import com.cgpanda.easyinvest.Entity.UserPortfolio;
import com.cgpanda.easyinvest.Repository.PortfolioRepository;

import java.math.BigDecimal;
import java.util.List;

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
            if (result.getId() != -1 && result.getPortfolioSecuritiesList() != null) {
                UpdateUserPortfolio userPortfolio = new UpdateUserPortfolio();
                userPortfolio.execute(result);
            } else {
                isLoading.postValue(false);
                userPortfolio.postValue(result);
            }
        }

        @Override
        protected UserPortfolio doInBackground(String... strings) {
            return portfolioRepository.getUserPortfolio(strings[0]);
        }
    }

    public static class UpdateUserPortfolio extends AsyncTask<UserPortfolio, Void, UserPortfolio> {

        @Override
        protected void onPostExecute(UserPortfolio portfolio) {
            super.onPostExecute(portfolio);
            userPortfolio.postValue(portfolio);
            isLoading.postValue(false);
        }

        @Override
        protected UserPortfolio doInBackground(UserPortfolio... userPortfolios) {
            UserPortfolio portfolio = userPortfolios[0];
            List<PortfolioSecurities> portfolioSecurities = portfolio.getPortfolioSecuritiesList();
            if (portfolioSecurities != null) {
                for (PortfolioSecurities securities : portfolioSecurities) {
                    Securities securities1 = securities.getSecurities();
                    Securities securities2 = portfolioRepository.getSecurities(securities1.getMarket(), securities1.getBoardId(), securities1.getSecid());

                    if (securities2.getPortfolioSecuritiesMarketData().get(0).getLast() == null) {
                        securities2.getPortfolioSecuritiesMarketData().get(0).setLast(BigDecimal.valueOf(123.4));
                        securities2.getPortfolioSecuritiesStaticData().get(0).setPrevPrice(BigDecimal.valueOf(123.4));
                    } else {
                        securities.setSecurities(securities2);
                    }

                }

                portfolio.setPortfolioSecuritiesList(portfolioSecurities);
            }
            return portfolio;
        }
    }
}
