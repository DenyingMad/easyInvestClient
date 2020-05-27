package com.cgpanda.easyinvest.Repository;

import android.util.Log;

import com.cgpanda.easyinvest.Entity.PortfolioSecurities.Securities;
import com.cgpanda.easyinvest.Entity.UserPortfolio;
import com.cgpanda.easyinvest.WebServices.PortfolioApi;
import com.cgpanda.easyinvest.WebServices.RetrofitService;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PortfolioRepository {

    private static final String TAG = "PortfolioRepository";

    private static PortfolioRepository instance;

    private Retrofit iss_retrofit = new Retrofit.Builder()
            .baseUrl("https://iss.moex.com/iss/engines/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private PortfolioApi portfolioApi = RetrofitService.cteateService(PortfolioApi.class);
    private PortfolioApi issApi = iss_retrofit.create(PortfolioApi.class);

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
            Log.d(TAG, "onResponse: Портфолио является пустым");
            return new UserPortfolio(-1);
        }
    }

    public Securities getSecurities(String market, String boardid, String secid) {
        Call<List<Securities>> call = issApi.getSecurities("stock", market, boardid, secid);
        Response<List<Securities>> response = null;

        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response != null && response.isSuccessful()){
            return response.body().get(1);
        } else {
            return new Securities();
        }
    }
}
