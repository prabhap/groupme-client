package com.groupify.prabhapattabiraman.groupme.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.groupify.prabhapattabiraman.groupme.ListGroupActivity;
import com.groupify.prabhapattabiraman.groupme.R;

import java.util.List;

public class CustomArrayAdapter extends ArrayAdapter {


    private String[] groupsInRange;
    private View.OnClickListener onClickListener;

    public CustomArrayAdapter(ListGroupActivity listGroupActivity, int simple_list_item, String[] groupsInRange, View.OnClickListener listener) {
        super(listGroupActivity, simple_list_item, groupsInRange);
        this.onClickListener = listener;
    }

    static class ViewHolder {
        TextView text;
        Button joinButton;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        String value = (String) getItem(position);

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

        h.text.setText(value);
        h.joinButton.setOnClickListener(onClickListener);

        return rowView;
    }
}
