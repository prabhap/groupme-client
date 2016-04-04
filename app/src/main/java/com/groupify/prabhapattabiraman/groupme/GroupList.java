package com.groupify.prabhapattabiraman.groupme;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.groupify.prabhapattabiraman.groupme.adapters.GroupListAdapter;
import com.groupify.prabhapattabiraman.groupme.retrofit.impl.GroupmeServerService;
import com.groupify.prabhapattabiraman.groupme.util.DBConstants;
import com.groupify.prabhapattabiraman.groupme.util.LocationProber;
import com.groupify.prabhapattabiraman.groupme.util.Session;
import com.groupify.prabhapattabiraman.groupme.util.pojo.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class GroupList extends AppCompatActivity{

    private LocationProber locationProber;
    private ArrayList<Group> groupsToDisplay = new ArrayList<>();
    private LocationManager locationManager;
    private boolean unsubscribedGroups;


    public abstract int getListGroupView();

    public void processAndListGroups(boolean unsubscribedGroups) {
        locationProber = LocationProber.getLocationInstance();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        this.unsubscribedGroups = unsubscribedGroups;
        getCurrentGroups(this.unsubscribedGroups);

    }

    private void getCurrentGroups(boolean unSubscribed) {
        Call<List<Map<String, String>>> groupsInRangeCall;
        if(unSubscribed) {
            groupsInRangeCall = GroupmeServerService.getServiceInstance().getService().listUnSubscribedGroups(Session.getCurrentUser(this),
                   locationProber.getCurrentLocation(locationManager, this));
        } else {
            groupsInRangeCall = GroupmeServerService.getServiceInstance().getService().listSubscribedGroups(Session.getCurrentUser(this));

        }
        processGroupResponse(groupsInRangeCall);
    }

    private void processGroupResponse(Call<List<Map<String, String>>> groupsInRangeCall) {
        final GroupList currentContext = this;
        groupsInRangeCall.enqueue(new Callback<List<Map<String, String>>>() {
            @Override
            public void onResponse(Call<List<Map<String, String>>> call, Response<List<Map<String, String>>> response) {
                List<Map<String, String>> groupsInRange = response.body();
                groupsToDisplay = new ArrayList<Group>();
                for (Map<String, String> keyPair  : groupsInRange) {
                    groupsToDisplay.add(new Group(keyPair.get(DBConstants.GROUP_NAME), Integer.valueOf(keyPair.get(DBConstants.ID))));
                }

                final ListView listview = (ListView) findViewById(getListGroupView());
                TextView noGroupMsg = (TextView) findViewById(R.id.no_group_msg);

                listview.setVisibility(groupsToDisplay.isEmpty() ? View.INVISIBLE : View.VISIBLE);
                noGroupMsg.setVisibility(groupsToDisplay.isEmpty() ? View.VISIBLE : View.INVISIBLE);

                listview.setAdapter(new GroupListAdapter(currentContext, R.layout.simple_list_item, groupsToDisplay, unsubscribedGroups));

            }

            @Override
            public void onFailure(Call<List<Map<String, String>>> call, Throwable t) {
                Toast.makeText(currentContext, "Failed", Toast.LENGTH_LONG);
            }
        });
    }

    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }



}
