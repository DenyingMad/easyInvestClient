package com.cgpanda.easyinvest.ViewModel;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cgpanda.easyinvest.Entity.ApiKey;
import com.cgpanda.easyinvest.Entity.UserCredentials;
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

    public void sendCredentials(String email, String hash){
        Log.d(TAG, "sendCredentials: Запускаем таск");
        SendData registerNewUser = new SendData();
        registerNewUser.execute(email, hash);
        Log.d(TAG, "sendCredentials: Таск зпущен");
    }

    public class SendData extends AsyncTask<String, Void, ApiKey>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isLoading.postValue(true);
        }

        @Override
        protected void onPostExecute(ApiKey apiKey) {
            super.onPostExecute(apiKey);
            // TODO сохранить apiKey в sharedPreferences
            Log.d(TAG, "onPostExecute: Сохраняем apiKey: " + apiKey.getApiKey());
            isLoading.postValue(false);
        }

        @Override
        protected ApiKey doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: Посылаем POST запрос на сервер");
            // Создаем объект пользователя для передачи логина и пароля
            UserCredentials user = new UserCredentials(strings[0], strings[1]);
            // Отправляем данные на сервер и получаем apiKey
            ApiKey apiKey = usersRepository.registerUser(user);
            user = null;
            System.gc();
            return apiKey;
        }
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
