package com.cgpanda.easyinvest.WebServices;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UsersApi {

    @GET("user/check-email")
    Call<Boolean> checkEmail(@Query("email") String email);

}
