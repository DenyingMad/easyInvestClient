package com.cgpanda.easyinvest.WebServices;

import com.cgpanda.easyinvest.Entity.UserPortfolio;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface PortfolioApi {
    @GET("user/securities/portfolio")
    Call<UserPortfolio> getUserPortfolio(@Header("api_key") String api_key);
}
