package com.cgpanda.easyinvest.ViewModel;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cgpanda.easyinvest.Entity.Securities.CommonStock.CommonStock;
import com.cgpanda.easyinvest.Repository.SecuritiesRepository;

public class SecuritiesViewModel extends ViewModel {
    private static SecuritiesRepository securitiesRepository;

    private static MutableLiveData<CommonStock> commonStocksList = new MutableLiveData<>();
    private static MutableLiveData<CommonStock> commonStock = new MutableLiveData<>();
    private static MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public void init(){
        if (securitiesRepository != null)
            return;
        securitiesRepository = SecuritiesRepository.getInstance();
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }
    public LiveData<CommonStock> observeCommonStocksList(){
        return commonStocksList;
    }
    public LiveData<CommonStock> observeCommonStock(){
        return commonStock;
    }

    public void updateCommonStocks(){
        UpdateCommonStocks u = new UpdateCommonStocks();
        u.execute();
    }
    public void getCommonStock(String market, String boardid, String secid){
        GetCommonStock g = new GetCommonStock();
        g.execute(market, boardid, secid);
    }
    public static class UpdateCommonStocks extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isLoading.postValue(true);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            isLoading.postValue(false);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            commonStocksList.postValue(securitiesRepository.getCommonStocksList());
            return null;
        }
    }
    public static class GetCommonStock extends AsyncTask<String, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isLoading.postValue(true);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            isLoading.postValue(false);
        }

        @Override
        protected Void doInBackground(String... strings) {
            commonStock.postValue(securitiesRepository.getCommonStock(strings[0], strings[1], strings[2]));
            return null;
        }
    }
}
