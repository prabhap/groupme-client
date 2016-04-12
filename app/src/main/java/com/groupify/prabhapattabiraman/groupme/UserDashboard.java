package com.groupify.prabhapattabiraman.groupme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;

import com.groupify.prabhapattabiraman.groupme.util.DBConstants;

public class UserDashboard extends GroupList {

    private String gcmToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        processAndListGroups(false);
        findViewById(R.id.progressIndicator).setVisibility(View.VISIBLE);
        BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(DBConstants.REGISTRATION_COMPLETE, "COMPLETE HURRY");
            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter(DBConstants.REGISTRATION_COMPLETE));

    }

    public void createNewGroup(View view) {
        Intent intent = new Intent(this, CreateNewGroupActivity.class);
        this.startActivity(intent);
    }

    @Override
    public int getListGroupView() {
        return R.id.user_groups_list_view;
    }


    public void searchNearbyGroups(View view) {
        this.startActivity(new Intent(this, ListGroupActivity.class));
    }
}
