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
import com.groupify.prabhapattabiraman.groupme.R;
import com.groupify.prabhapattabiraman.groupme.util.DBConstants;
import com.groupify.prabhapattabiraman.groupme.util.pojo.Group;

import java.util.ArrayList;

public class GroupListAdapter extends ArrayAdapter {
    private final ArrayList<Group> groupsInRange;
    private boolean unsubscribedGroups;
    private Activity currentActivity;

    public GroupListAdapter(Activity listGroupActivity, int simple_list_item, ArrayList<Group> groupsInRange, boolean unsubscribedGroups) {
        super(listGroupActivity, simple_list_item, groupsInRange);
        this.currentActivity = listGroupActivity;
        this.groupsInRange = groupsInRange;
        this.unsubscribedGroups = unsubscribedGroups;
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
        if(unsubscribedGroups) {
            h.joinButton.setOnClickListener(attachOnClickListener(group.getId(), group.getName()));
        }else {
            rowView.setOnClickListener(attachOnClickListener(group.getId(), group.getName()));
            h.joinButton.setVisibility(View.INVISIBLE);
        }

        return rowView;
    }

    @Override
    public int getCount(){
        return groupsInRange!=null ? groupsInRange.size() : 0;
    }



    private View.OnClickListener attachOnClickListener(final int groupId, final String name) {
        return  new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentActivity, ConversationActivity.class);
                intent.putExtra(DBConstants.GROUP_ID, groupId);
                intent.putExtra(DBConstants.GROUP_NAME, name);
                if(unsubscribedGroups) {
                    intent.putExtra(DBConstants.ACTION, DBConstants.JOIN_AND_LIST);
                } else {
                    intent.putExtra(DBConstants.ACTION, DBConstants.LIST);
                }
                currentActivity.startActivity(intent);
            }
        };

    }
}
