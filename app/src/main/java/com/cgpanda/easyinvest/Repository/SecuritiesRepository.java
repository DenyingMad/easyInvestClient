package com.cgpanda.easyinvest.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.cgpanda.easyinvest.Entity.Securities.CommonStock.CommonStock;
import com.cgpanda.easyinvest.WebServices.SecuritiesApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SecuritiesRepository {
    private static final String TAG = "SecuritiesRepository";

    private static SecuritiesRepository instance;
    public static SecuritiesRepository getInstance(){
        if (instance == null){
            instance = new SecuritiesRepository();
        }
        return instance;
    }

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://iss.moex.com/iss/engines/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private SecuritiesApi securitiesApi = retrofit.create(SecuritiesApi.class);

    public CommonStock getCommonStocksList(){
        Call<List<CommonStock>> call = securitiesApi.getCommonStocksList();
        Response<List<CommonStock>> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response != null && response.isSuccessful()){
            Log.d(TAG, "getCommonStocksList: response successful");
            return response.body().get(1);
        } else {
            return new CommonStock();
        }
    }

    public CommonStock getCommonStock(String market, String board, String secid){
        Call<List<CommonStock>> call = securitiesApi.getCommonStock(market, board, secid);
        Response<List<CommonStock>> response = null;

        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response != null && response.isSuccessful()){
            return response.body().get(1);
        } else {
            return new CommonStock();
        }

    }
}
