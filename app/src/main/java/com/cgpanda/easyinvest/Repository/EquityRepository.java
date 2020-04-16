package com.cgpanda.easyinvest.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.cgpanda.easyinvest.Entity.Equity;
import com.cgpanda.easyinvest.WebServices.EquitiesApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EquityRepository {

    private static final String TAG = "EquityRepository";

    private static EquityRepository instance;
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://protected-cliffs-60934.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private EquitiesApi api = retrofit.create(EquitiesApi.class);

    public static EquityRepository getInstance (){
        if (instance == null)
            instance = new EquityRepository();
        return instance;
    }

    public MutableLiveData<List<Equity>> getAll(){
        final MutableLiveData<List<Equity>> data = new MutableLiveData<>();
        Call<List<Equity>> equities = api.getAll();
        equities.enqueue(new Callback<List<Equity>>() {
            @Override
            public void onResponse(Call<List<Equity>> call, Response<List<Equity>> response) {
                data.setValue(response.body());
                Log.d(TAG, "onResponse: Success equity request");
            }

            @Override
            public void onFailure(Call<List<Equity>> call, Throwable t) {
                Log.d(TAG, "onFailure: Fail equity request");
            }
        });
        return data;
    }
}
