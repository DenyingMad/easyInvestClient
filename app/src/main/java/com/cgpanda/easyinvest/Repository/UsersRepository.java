package com.cgpanda.easyinvest.Repository;

import androidx.lifecycle.MutableLiveData;

import com.cgpanda.easyinvest.Entity.ApiKey;
import com.cgpanda.easyinvest.Entity.UserCredentials;
import com.cgpanda.easyinvest.WebServices.UsersApi;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsersRepository {

    private static UsersRepository instance;
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://protected-cliffs-60934.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private UsersApi usersApi = retrofit.create(UsersApi.class);

    public static UsersRepository getInstance(){
        if (instance == null)
            instance = new UsersRepository();
        return instance;
    }

    public Boolean checkEmail(String email) {
        Call<Boolean> call = usersApi.checkEmail(email);
        try {
            call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ApiKey registerUser(UserCredentials credentials){
        Call<ApiKey> apiKeyCall = usersApi.registerUser(credentials);
        Response<ApiKey> response = null;
        try {
             response = apiKeyCall.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response.body() != null) {
            return response.body();
        } else{
            return new ApiKey("default ApiKey");
        }
    }
}
