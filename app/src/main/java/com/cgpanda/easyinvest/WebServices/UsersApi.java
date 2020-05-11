package com.cgpanda.easyinvest.WebServices;

import com.cgpanda.easyinvest.Entity.ApiKey;
import com.cgpanda.easyinvest.Entity.UserCredentials;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UsersApi {

    @GET("login/check-email")
    Call<Boolean> checkEmail(@Query("email") String email);

    @POST("login/register")
    Call<ApiKey> registerUser(@Body UserCredentials userCredentials);

    @GET("login/sign-in")
    Call<ResponseBody> authUser(@Query("email") String email);
}
