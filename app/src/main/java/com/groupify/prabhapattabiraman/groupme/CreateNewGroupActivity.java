package com.groupify.prabhapattabiraman.groupme;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.groupify.prabhapattabiraman.groupme.retrofit.impl.GroupmeServerService;
import com.groupify.prabhapattabiraman.groupme.util.LocationProber;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.groupify.prabhapattabiraman.groupme.util.LocationProber.getLocationInstance;

public class CreateNewGroupActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_group);
    }

    public void onCreateGroup(View view) {
        String groupName = ((EditText) findViewById(R.id.groupName)).getText().toString();
        int range = Integer.valueOf(((EditText) findViewById(R.id.groupRange)).getText().toString());
        int checkedRadioButtonId = ((RadioGroup) findViewById(R.id.open)).getCheckedRadioButtonId();
        RadioButton isOpen = (RadioButton) findViewById(checkedRadioButtonId);
        boolean open = isOpen.getText() == getResources().getString(R.string.openGroup) ? true : false;
        LocationProber locationProber = getLocationInstance();
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        String currentLocation = locationProber.getCurrentLocation(locationManager, this);


        Call<ResponseBody> response = new GroupmeServerService().getService().createGroup(groupName, currentLocation, range, open);
        final Context currentActivity = this;

        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                LocationProber locationProber = getLocationInstance();
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                locationProber.getCurrentGroups(locationManager, currentActivity);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}
