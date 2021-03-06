package com.groupify.prabhapattabiraman.groupme.util;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.groupify.prabhapattabiraman.groupme.ListGroupActivity;
import com.groupify.prabhapattabiraman.groupme.UserDashboard;
import com.groupify.prabhapattabiraman.groupme.retrofit.impl.GroupmeServerService;
import com.groupify.prabhapattabiraman.groupme.util.pojo.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LocationProber implements LocationListener{


    private static LocationProber locationProber;

    private LocationProber() {

    }

    public static LocationProber getLocationInstance() {
        if(locationProber == null) {
            locationProber = new LocationProber();
        }
        return locationProber;
    }


    public String getCurrentLocation(LocationManager locationManager, Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        android.location.Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        String geoLocation = ",";
        if(location != null) {
            String latitude = String.valueOf(location.getLatitude());
            String longitude = String.valueOf(location.getLongitude());
            geoLocation = latitude + "," + longitude;
            return geoLocation;
        }
        return null;
    }



    @Override
    public void onLocationChanged(android.location.Location location) {
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
