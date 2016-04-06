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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private LoginActivity loginActivity;
    private ProgressBar spinner;
    private EditText phoneNumberInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginActivity = this;
        preferences = PreferenceManager.getDefaultSharedPreferences(loginActivity);

        View loginContainer = findViewById(R.id.login_container);
        spinner = (ProgressBar) findViewById(R.id.pbHeaderProgress);
        spinner.setVisibility(View.VISIBLE);

        showLoginOrRedirect(loginContainer);
        startService(new Intent(this, RegistrationIntentService.class));
        startService(new Intent(this, MyGcmListenerService.class));
    }

    private void showLoginOrRedirect(View loginContainer) {
        if(preferences.contains(DBConstants.USER_ID)) {
            loginContainer.setVisibility(View.GONE);
            captureCurrentLocationAndProceedCourse();
        } else {
            loginContainer.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.GONE);
            EditText phoneNumberInput = (EditText) findViewById(R.id.phone_number);
            phoneNumberInput.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        }
    }


    public void createUser(View view) {
        spinner.setVisibility(View.VISIBLE);
        EditText phoneNumberInput = (EditText) findViewById(R.id.phone_number);

        final Editable phoneNumber = phoneNumberInput.getText();


        Call<String> createUser = GroupmeServerService.getServiceInstance().getService().createUser(phoneNumber.toString());
        createUser.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String  userId = response.body();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(DBConstants.USER_ID, userId);
                editor.apply();
                captureCurrentLocationAndProceedCourse();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    private void captureCurrentLocationAndProceedCourse() {
        Intent intent = new Intent(this, UserDashboard.class);
        this.startActivity(intent);
    }

}
