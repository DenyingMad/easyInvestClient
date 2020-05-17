package com.cgpanda.easyinvest.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.cgpanda.easyinvest.Entity.UserPortfolio;
import com.cgpanda.easyinvest.WebServices.PortfolioApi;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PortfolioRepository {

    private static final String TAG = "PortfolioRepository";

    private static PortfolioRepository instance;

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://protected-cliffs-60934.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private PortfolioApi portfolioApi = retrofit.create(PortfolioApi.class);

    public static PortfolioRepository getInstance(){
        if (instance == null){
            instance = new PortfolioRepository();
        }
        return instance;
    }

    public UserPortfolio getUserPortfolio(String apiKey){
        Call<UserPortfolio> call = portfolioApi.getUserPortfolio(apiKey);
        Response<UserPortfolio> response = null;

        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response != null && response.isSuccessful()){
            Log.d(TAG, "onResponse: Портфолио пользователя получено");
            return response.body();
        } else{
            // TODO Вывод сообщения о том, что портфель пуст
            Log.d(TAG, "onResponse: Портфолио является пустым");
            return new UserPortfolio(-1);
        }
    }
}
