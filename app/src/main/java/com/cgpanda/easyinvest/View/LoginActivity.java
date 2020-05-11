package com.cgpanda.easyinvest.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cgpanda.easyinvest.R;
import com.cgpanda.easyinvest.Security;
import com.cgpanda.easyinvest.View.Fragments.AttentionDialogFragment;
import com.cgpanda.easyinvest.ViewModel.LoginViewModel;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private Button enterButton, registerButton;
    private LinearLayout progressLayout;
    private EditText email, password;
    private LoginViewModel viewModel;
    private CheckBox savePasswordCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        uiBinding();

        preferences = getSharedPreferences("com.cgpanda.easyinvest", MODE_PRIVATE);
        editor = preferences.edit();
        checkSavedLoginPreferences();

        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        viewModel.init();

        viewModel.checkIsLoading().observe(this, this::setVisibilityProgressBar);

        viewModel.checkEmail().observe(this, aBoolean -> {
            if (aBoolean){
                showAttentionDialog(R.string.dialog_message_email_exist_message);
            } else {
                userRegistration();
            }
        });

        viewModel.getApiKey().observe(this, apiKey ->{
            editor.putString(getString(R.string.shared_pref_api_key) + "_" + email.getText().toString(), apiKey);
            editor.commit();
        });

        viewModel.getIsAuthorized().observe(this, isAuthorized -> {
            if (isAuthorized) {
                // Сохраняем поля ввода логина и пароля, если отмечена соответствующая галочка
                saveLoginPreferences();
                // Пользователь авторизирован, перейти к главному экрану
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                // Неверный логин или пароль
                showAttentionDialog(R.string.dialog_message_login_denied);
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
            if (checkFields()) {

                // Авторизация:
                viewModel.authUser(email.getText().toString(), password.getText().toString());
            }
        });
    }
    
    private void userRegistration(){
        // Сохраняем поля ввода логина и пароля, если отмечена соответствующая галочка
        saveLoginPreferences();
        try {
            // Хешируем пароль
            String hash = Security.generateHash(password.getText().toString());

            // Отправляем данные пользователя на сервер
            viewModel.sendCredentials(email.getText().toString(), hash);

            hash = null;
            System.gc();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    private Boolean checkFields() {
        if (email.getText().toString().contains("@") && password.length() >= 8){
            return true;
        } else{
            showAttentionDialog(R.string.dialog_message_wrong_email_or_password);
            return false;
        }
    }

    private void checkSavedLoginPreferences(){
        String checkbox = preferences.getString(getString(R.string.shared_pref_checkbox), "False");
        String mail = preferences.getString(getString(R.string.shared_pref_email), "");
        String password = preferences.getString(getString(R.string.shared_pref_password), "");

        email.setText(mail);
        this.password.setText(password);

        if (checkbox.equals("True")){
            savePasswordCheck.setChecked(true);
        } else {
            savePasswordCheck.setChecked(false);
        }
    }

    private void saveLoginPreferences(){
        // Если отмечено сохранение пароля, то добавляем в sharedPreferences значения полей, если галочка снята, то очищаем shared pref.
        if (savePasswordCheck.isChecked()){
            // set checkbox
            editor.putString(getString(R.string.shared_pref_checkbox), "True");
            editor.commit();

            // set email
            editor.putString(getString(R.string.shared_pref_email), email.getText().toString());
            editor.commit();

            // set the password
            editor.putString(getString(R.string.shared_pref_password), password.getText().toString());
            editor.commit();
        } else {
            editor.putString(getString(R.string.shared_pref_checkbox), "False");
            editor.commit();

            editor.putString(getString(R.string.shared_pref_email), "");
            editor.commit();

            editor.putString(getString(R.string.shared_pref_password), "");
            editor.commit();
        }

    }

    private void uiBinding() {
        enterButton = findViewById(R.id.enter_button);
        registerButton = findViewById(R.id.register_button);
        email = findViewById(R.id.email_et);
        password = findViewById(R.id.password_et);
        progressLayout = findViewById(R.id.login_progress_bar_layout);
        savePasswordCheck = findViewById(R.id.save_fields_cb);
    }

    private void showAttentionDialog(int message){
        AttentionDialogFragment.newInstance(message).show(getSupportFragmentManager(), "alert");
    }

    private void setVisibilityProgressBar(Boolean isVisible){
        if (isVisible){
            progressLayout.setVisibility(View.VISIBLE);
        } else{
            progressLayout.setVisibility(View.GONE);
        }
    }
}
