package com.groupify.prabhapattabiraman.groupme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.groupify.prabhapattabiraman.groupme.retrofit.impl.GroupmeServerService;
import com.groupify.prabhapattabiraman.groupme.util.DBConstants;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends ValidatableAppCompactActivity {

    private SharedPreferences preferences;
    private LoginActivity loginActivity;
    private ProgressBar spinner;
    @NotEmpty
    @Email
    private EditText email;
    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginActivity = this;
        preferences = PreferenceManager.getDefaultSharedPreferences(loginActivity);

        View loginContainer = findViewById(R.id.login_container);
        showProgressBar();
        launchActivityAndStartBgService(loginContainer);
        setupValidator();
    }

    private void setupValidator() {
        validator = new Validator(this);
        validator.setValidationListener(this);

    }

    private void showProgressBar() {
        spinner = (ProgressBar) findViewById(R.id.progressIndicator);
        spinner.setVisibility(View.VISIBLE);
    }

    private void launchActivityAndStartBgService(View loginContainer) {
        showLoginOrRedirect(loginContainer);
        startService(new Intent(this, RegistrationIntentService.class));
        startService(new Intent(this, MyGcmListenerService.class));
    }

    private void showLoginOrRedirect(View loginContainer) {
        if (preferences.contains(DBConstants.USER_ID)) {
            loginContainer.setVisibility(View.GONE);
            captureCurrentLocationAndProceedCourse();
        } else {
            loginContainer.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.GONE);
        }
    }


    public void createUser(View view) {
        email = (EditText) findViewById(R.id.email);
        validator.validate();
    }

    private void captureCurrentLocationAndProceedCourse() {
        Intent intent = new Intent(this, UserDashboard.class);
        this.startActivity(intent);
    }

    @Override
    public void onValidationSucceeded() {
        final Editable emailText = email.getText();
        Call<String> createUser = GroupmeServerService.getServiceInstance().getService().createUser(emailText.toString());
        createUser.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                spinner.setVisibility(View.GONE);
                String userId = response.body();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(DBConstants.USER_ID, userId);
                editor.apply();
                captureCurrentLocationAndProceedCourse();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                spinner.setVisibility(View.GONE);
            }
        });
    }
}
