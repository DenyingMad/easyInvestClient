package com.cgpanda.easyinvest.WebServices;

import com.cgpanda.easyinvest.Entity.ApiKey;
import com.cgpanda.easyinvest.Entity.UserCredentials;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UsersApi {

    @GET("user/check-email")
    Call<Boolean> checkEmail(@Query("email") String email);

    @POST("user/register")
    Call<ApiKey> registerUser(@Body UserCredentials userCredentials);
}
