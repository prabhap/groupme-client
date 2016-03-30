package com.groupify.prabhapattabiraman.groupme.adapters;

import android.app.Activity;
import android.content.Context;
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

    public CustomArrayAdapter(ListGroupActivity listGroupActivity, int simple_list_item, String[] groupsInRange) {
        super(listGroupActivity, simple_list_item, groupsInRange);
    }

    static class ViewHolder {
        TextView text;
        Button btn;
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
            h.btn = (Button) rowView.findViewById(R.id.join);
            rowView.setTag(h);
        }

        ViewHolder h = (ViewHolder) rowView.getTag();

        h.text.setText(value);
//        h.indicator.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // DO what you want to recieve on btn click there.
//            }
//        });

        return rowView;
    }
}
