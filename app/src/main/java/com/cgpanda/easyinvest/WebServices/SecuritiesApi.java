package com.cgpanda.easyinvest.WebServices;

import com.cgpanda.easyinvest.Entity.Securities.CommonStock.CommonStock;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SecuritiesApi {
    //https://iss.moex.com/iss/engines/stock/markets/shares/boards/tqbr/securities.json?iss.json=extended&iss.meta=off
    @GET("stock/markets/shares/boards/tqbr/securities.json?iss.json=extended&iss.meta=off")
    Call<List<CommonStock>> getCommonStocksList();

    @GET("stock/markets/{market}/boards/{board}/securities/{secid}.json?iss.json=extended&iss.meta=off")
    Call<List<CommonStock>> getCommonStock(@Path("market") String market, @Path("board") String board, @Path("secid") String secid);
}
