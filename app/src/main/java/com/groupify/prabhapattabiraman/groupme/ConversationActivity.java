package com.groupify.prabhapattabiraman.groupme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ConversationActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
    }

    public void send(View view) {
        LinearLayout conversationDashboard = (LinearLayout) findViewById(R.id.dashboard);
        EditText chatBox = (EditText) findViewById(R.id.chatText);
        Editable chatText = chatBox.getText();
        TextView child = new TextView(this);
        child.setText(chatText);
        child.setPadding(20, 20, 20, 20);
        child.setBackground(getResources().getDrawable(R.drawable.rounded_corner));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 10, 0 , 10);
        child.setLayoutParams(layoutParams);
        conversationDashboard.addView(child);

        chatBox.setText("");
    }
}
