package com.cgpanda.easyinvest.Repository;

import androidx.lifecycle.MutableLiveData;

import com.cgpanda.easyinvest.WebServices.UsersApi;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
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

}
