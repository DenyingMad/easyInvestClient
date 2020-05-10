package com.cgpanda.easyinvest.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.cgpanda.easyinvest.R;
import com.cgpanda.easyinvest.Security;
import com.cgpanda.easyinvest.View.Fragments.AttentionDialogFragment;
import com.cgpanda.easyinvest.ViewModel.LoginViewModel;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private Button enterButton, registerButton;
    private LinearLayout progressLayout;
    private EditText email, password;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        uiBinding();
        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        viewModel.init();
        viewModel.checkIsLoading().observe(this, this::setVisibilityProgressBar);
        viewModel.checkEmail().observe(this, aBoolean -> {
            if (aBoolean){
                showErrorUserAlreadyExist(R.string.email_exist_message);
            } else {
                startUserRegistration();
            }
        });
        registerButton.setOnClickListener(v -> {
            // Проверить корректность полей
            if (checkFields()) {
                // Если поля заполнены корректно, проверяем, существует ли пользователь с таким email
                viewModel.checkEmailIfExists(email.getText().toString());
            }
        });

        enterButton.setOnClickListener(v ->{
            // Авторизация:
            // Отправить apiKey на сервер
            // Ждать ответ
            // Если ответ положительный, то показать приветсвие и перейти к главному окну
        });
    }

    private Boolean checkFields() {
        if (email.getText().toString().contains("@") && password.length() >= 8){
            return true;
        } else{
            showErrorUserAlreadyExist(R.string.wrong_email_or_password);
            return false;
        }
    }

    private void startUserRegistration(){
        // Захешировать пароль
        // Запросить имя пользователя
        // Отправить пост запрос на сервер
        // Получить apiKey
        // Авторизоваться
        Security security = new Security();
        try {
            String hash = Security.generateHash(password.getText().toString());
            Log.d(TAG, "startUserRegistration: hash: " + hash);
            viewModel.sendCredentials(email.getText().toString(), hash);
            hash = null;
            System.gc();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

    }


    private void uiBinding() {
        enterButton = findViewById(R.id.enter_button);
        registerButton = findViewById(R.id.register_button);
        email = findViewById(R.id.email_et);
        password = findViewById(R.id.password_et);
        progressLayout = findViewById(R.id.login_progress_bar_layout);
    }

    private void showErrorUserAlreadyExist(int message){
        AttentionDialogFragment.newInstance(message).show(getSupportFragmentManager(), "alert");
        Log.d(TAG, "showErrorUserAlreadyExist: show");
    }

    private void setVisibilityProgressBar(Boolean isVisible){
        if (isVisible){
            progressLayout.setVisibility(View.VISIBLE);
        } else{
            progressLayout.setVisibility(View.GONE);
        }
    }



}