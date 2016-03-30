package com.groupify.prabhapattabiraman.groupme;

import android.content.Context;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.groupify.prabhapattabiraman.groupme.util.LocationProber;

import static com.groupify.prabhapattabiraman.groupme.util.LocationProber.getLocationInstance;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        captureCurrentLocationAndProceedCourse();
    }

    private void captureCurrentLocationAndProceedCourse() {
        LocationProber locationProber = getLocationInstance();
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationProber.getCurrentGroups(locationManager, this);
    }
}
