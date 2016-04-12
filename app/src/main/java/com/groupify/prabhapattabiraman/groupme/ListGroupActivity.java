package com.groupify.prabhapattabiraman.groupme;

import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;

public class ListGroupActivity extends GroupList {
    SimpleCursorAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_group);
        findViewById(R.id.progressIndicator).setVisibility(View.VISIBLE);
        processAndListGroups(true);
    }

    @Override
    public int getListGroupView() {
        return R.id.listGroups;
    }


}
