package com.cgpanda.easyinvest.ViewModel;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cgpanda.easyinvest.Entity.ApiKey;
import com.cgpanda.easyinvest.Entity.UserCredentials;
import com.cgpanda.easyinvest.Repository.UsersRepository;
import com.cgpanda.easyinvest.Security;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class LoginViewModel extends ViewModel {

    private static final String TAG = "LoginViewModel";

    private UsersRepository usersRepository = null;
    private MutableLiveData<Boolean> isExist = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<String> apiKeyString = new MutableLiveData<>();
    private MutableLiveData<Boolean> isAuthorized = new MutableLiveData<>();

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

    public LiveData<String> getApiKey(){ return apiKeyString;}

    public LiveData<Boolean> getIsAuthorized() {
        return isAuthorized;
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

    public void authUser(String email, String password){
        Auth auth = new Auth();
        auth.execute(email, password);
    }

    private class Auth extends AsyncTask<String, Void, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isLoading.postValue(true);
        }

        @Override
        protected void onPostExecute(String hash) {
            super.onPostExecute(hash);
            if (!hash.equals(".")) {
                try {
                    isAuthorized.postValue(Security.validatePassword(hash));
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    e.printStackTrace();
                }
            } else {
                isAuthorized.postValue(false);
            }
            isLoading.postValue(false);
        }

        @Override
        protected String doInBackground(String... strings) {
            String hash = null;
            try {
                hash = usersRepository.authUser(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (hash.contains(":")) {
                return hash + ":" + strings[1];
            } else{
                return ".";
            }
        }
    }

    private class SendData extends AsyncTask<String, Void, ApiKey>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isLoading.postValue(true);
        }

        @Override
        protected void onPostExecute(ApiKey apiKey) {
            super.onPostExecute(apiKey);
            apiKeyString.postValue(apiKey.getApiKey());
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
