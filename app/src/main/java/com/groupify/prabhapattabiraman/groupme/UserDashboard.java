package com.groupify.prabhapattabiraman.groupme;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.groupify.prabhapattabiraman.groupme.adapters.GroupListAdapter;
import com.groupify.prabhapattabiraman.groupme.util.DBConstants;
import com.groupify.prabhapattabiraman.groupme.util.LocationProber;
import com.groupify.prabhapattabiraman.groupme.util.pojo.Group;

import java.util.ArrayList;

public class UserDashboard extends GroupList {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        processAndListGroups(false);
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
