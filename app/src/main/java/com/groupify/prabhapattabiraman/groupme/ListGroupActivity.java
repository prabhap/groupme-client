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
        String[] groupsInRange = getIntent().getStringArrayExtra(LocationProber.GROUPS);
        final ArrayList<String> list = new ArrayList<String>();
        final ListGroupActivity listGroupActivity = this;
        for (int i = 0; i < groupsInRange.length; ++i) {
            list.add(groupsInRange[i]);
        }

        listview.setVisibility(list.isEmpty() ? View.INVISIBLE : View.VISIBLE);
        noGroupMsg.setVisibility(list.isEmpty() ? View.VISIBLE : View.INVISIBLE);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(listGroupActivity, ConversationActivity.class);
                listGroupActivity.startActivity(intent);
            }
        };
        listview.setAdapter(new CustomArrayAdapter(this, R.layout.simple_list_item, groupsInRange, onClickListener));
    }

    public void createNewGroup(View view) {
        Intent intent = new Intent(this, CreateNewGroupActivity.class);
        this.startActivity(intent);
    }
}
