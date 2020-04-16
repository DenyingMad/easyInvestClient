package com.cgpanda.easyinvest.WebServices;

import com.cgpanda.easyinvest.Entity.Equity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EquitiesApi {
    @GET("equities")
    Call<List<Equity>> getAll ();
}
