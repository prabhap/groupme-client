package com.groupify.prabhapattabiraman.groupme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.groupify.prabhapattabiraman.groupme.adapters.CustomArrayAdapter;
import com.groupify.prabhapattabiraman.groupme.util.LocationProber;
import com.groupify.prabhapattabiraman.groupme.util.pojo.Group;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ListGroupActivity extends AppCompatActivity {
    SimpleCursorAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_group);
        final ListView listview = (ListView) findViewById(R.id.listGroups);
        TextView noGroupMsg = (TextView) findViewById(R.id.no_group_msg);
        ArrayList<Group> groupsInRange = getIntent().getParcelableArrayListExtra(LocationProber.GROUPS);

        listview.setVisibility(groupsInRange.isEmpty() ? View.INVISIBLE : View.VISIBLE);
        noGroupMsg.setVisibility(groupsInRange.isEmpty() ? View.VISIBLE : View.INVISIBLE);

        listview.setAdapter(new CustomArrayAdapter(this, R.layout.simple_list_item, groupsInRange));
    }

    public void createNewGroup(View view) {
        Intent intent = new Intent(this, CreateNewGroupActivity.class);
        this.startActivity(intent);
    }
}
