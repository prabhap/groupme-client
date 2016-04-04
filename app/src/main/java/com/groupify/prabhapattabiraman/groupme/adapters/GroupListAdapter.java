package com.groupify.prabhapattabiraman.groupme.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.groupify.prabhapattabiraman.groupme.ConversationActivity;
import com.groupify.prabhapattabiraman.groupme.ListGroupActivity;
import com.groupify.prabhapattabiraman.groupme.R;
import com.groupify.prabhapattabiraman.groupme.util.DBConstants;
import com.groupify.prabhapattabiraman.groupme.util.pojo.Group;

import java.util.ArrayList;

public class GroupListAdapter extends ArrayAdapter {
    private String[] groupsInRange;
    private ListGroupActivity currentActivity;

    public GroupListAdapter(ListGroupActivity listGroupActivity, int simple_list_item, ArrayList<Group> groupsInRange) {
        super(listGroupActivity, simple_list_item, groupsInRange);
        this.currentActivity = listGroupActivity;
    }

    static class ViewHolder {
        TextView text;
        Button joinButton;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Group group = (Group) getItem(position);
        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            rowView = inflater.inflate(R.layout.simple_list_item, parent, false);
            ViewHolder h = new ViewHolder();
            h.text = (TextView) rowView.findViewById(R.id.list_item);
            h.joinButton = (Button) rowView.findViewById(R.id.joinConversation);
            rowView.setTag(h);
        }

        ViewHolder h = (ViewHolder) rowView.getTag();

        h.text.setText(group.getName());
        h.joinButton.setOnClickListener(attachOnClickListener(group.getId()));

        return rowView;
    }

    private View.OnClickListener attachOnClickListener(final int groupId) {
        return  new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentActivity, ConversationActivity.class);
                intent.putExtra(DBConstants.GROUP_ID, groupId);
                intent.putExtra(DBConstants.ACTION, DBConstants.JOIN_AND_LIST);
                currentActivity.startActivity(intent);
            }
        };

    }
}
