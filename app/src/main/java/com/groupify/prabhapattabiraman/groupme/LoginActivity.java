package com.groupify.prabhapattabiraman.groupme;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import com.groupify.prabhapattabiraman.groupme.retrofit.impl.GroupmeServerService;
import com.groupify.prabhapattabiraman.groupme.util.DBConstants;
import com.groupify.prabhapattabiraman.groupme.util.LocationProber;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.groupify.prabhapattabiraman.groupme.util.LocationProber.getLocationInstance;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private LoginActivity loginActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginActivity = this;
        preferences = PreferenceManager.getDefaultSharedPreferences(loginActivity);
        if(preferences.contains(DBConstants.USER_ID)) {
            captureCurrentLocationAndProceedCourse();
        }
    }


    public void createUser(View view) {
        EditText phoneNumberInput = (EditText) findViewById(R.id.phone_number);
        final Editable phoneNumber = phoneNumberInput.getText();

        Call<String> createUser = GroupmeServerService.getServiceInstance().getService().createUser(phoneNumber.toString());
        createUser.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String  confirmationPhoneNumber = response.body();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(DBConstants.USER_ID, confirmationPhoneNumber);
                editor.apply();
                captureCurrentLocationAndProceedCourse();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    private void captureCurrentLocationAndProceedCourse() {
        LocationProber locationProber = getLocationInstance();
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationProber.getCurrentGroups(locationManager, this);
    }

}
