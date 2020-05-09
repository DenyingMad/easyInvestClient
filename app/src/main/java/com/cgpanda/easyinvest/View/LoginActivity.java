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
                showErrorUserAlreadyExist();
            } else {
                startUserRegistration();
            }
        });
        registerButton.setOnClickListener(v -> {
            // Проверяем, существует ли пользователь с таким email
            viewModel.checkEmailIfExists(email.getText().toString());

        });

        enterButton.setOnClickListener(v ->{
            // Авторизация:
            // Отправить apiKey на сервер
            // Ждать ответ
            // Если ответ положительный, то показать приветсвие и перейти к главному окну
        });
    }

    private void startUserRegistration(){
        // Захешировать пароль
        // Запросить имя пользователя
        // Отправить пост запрос на сервер
        // Получить apiKey
        // Авторизоваться

        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            byte[] salt = new byte[16];
            random.nextBytes(salt);

            PBEKeySpec spec = new PBEKeySpec(password.getText().toString().toCharArray(), salt, 32768, 64 * 8);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();

            BigInteger bi = new BigInteger(1, hash);
            String hex = bi.toString(16);
            BigInteger bigInteger = new BigInteger(1, salt);
            String saltString = bigInteger.toString(16);

            Log.d(TAG, "startUserRegistration: hash: " + hex);
            Log.d(TAG, "startUserRegistration: salt: " + saltString);
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

    private void continueRegistration(){

    }

    private void showErrorUserAlreadyExist(){
        AttentionDialogFragment.newInstance(R.string.email_exist_message).show(getSupportFragmentManager(), "dialog");
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
