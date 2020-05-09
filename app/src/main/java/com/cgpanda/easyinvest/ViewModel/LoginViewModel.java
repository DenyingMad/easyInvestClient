package com.cgpanda.easyinvest.ViewModel;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cgpanda.easyinvest.Repository.UsersRepository;

public class LoginViewModel extends ViewModel {

    private static final String TAG = "LoginViewModel";

    private UsersRepository usersRepository = null;
    private MutableLiveData<Boolean> isExist = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public void init(){
        if (usersRepository != null) {
            return;
        }
        usersRepository = UsersRepository.getInstance();
    }


    public LiveData<Boolean> checkIsLoading(){
        return isLoading;
    }

    public LiveData<Boolean> checkEmail(){
        return isExist;
    }

    public void checkEmailIfExists(String email){
        Log.d(TAG, "checkEmailIfExists: Создать асинхронный таск");
        CheckEmail checkEmail = new CheckEmail();
        checkEmail.execute(email);
        Log.d(TAG, "checkEmailIfExists: Таск запущен");
    }

    private class CheckEmail extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isLoading.postValue(true);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){
                Log.d(TAG, "onPostExecute: User already exist: " + aBoolean);

            } else {
                Log.d(TAG, "onPostExecute: Continue creating new user: " + aBoolean);
            }
            isExist.postValue(aBoolean);
            isLoading.postValue(false);
            Log.d(TAG, "onPostExecute: Таск закончил работу");
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            return usersRepository.checkEmail(strings[0]);
        }
    }
}
