package com.cgpanda.easyinvest.WebServices;

import com.cgpanda.easyinvest.Entity.PortfolioSecurities.Securities;
import com.cgpanda.easyinvest.Entity.UserPortfolio;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface PortfolioApi {
    @GET("user/securities/portfolio/")
    Call<UserPortfolio> getUserPortfolio(@Header("api_key") String api_key);

    @GET("{engine}/markets/{market}/boards/{boardid}/securities/{secid}.json?iss.json=extended&iss.meta=off")
    Call<List<Securities>> getSecurities(@Path("engine") String engine, @Path("market") String market, @Path("boardid") String boardid, @Path("secid") String secid);
}
