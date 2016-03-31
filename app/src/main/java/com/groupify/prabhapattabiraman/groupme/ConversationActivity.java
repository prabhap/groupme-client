package com.groupify.prabhapattabiraman.groupme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.groupify.prabhapattabiraman.groupme.adapters.CustomArrayAdapter;
import com.groupify.prabhapattabiraman.groupme.retrofit.impl.GroupmeServerService;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConversationActivity extends AppCompatActivity{

    private int groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        groupId = getIntent().getExtras().getInt(CustomArrayAdapter.GROUP_ID);
        Call<List<Map<String, String>>> conversations = GroupmeServerService.getServiceInstance().getService().getConversations(groupId);
        conversations.enqueue(new Callback<List<Map<String, String>>>() {
            @Override
            public void onResponse(Call<List<Map<String, String>>> call, Response<List<Map<String, String>>> response) {
                List<Map<String, String>> chatMsgs = response.body();
                for (Map<String, String>  msg: chatMsgs) {
                    addConversationToTheBox(msg.get("text"));
                }
            }

            @Override
            public void onFailure(Call<List<Map<String, String>>> call, Throwable t) {

            }
        });
    }

    public void send(View view) {
        EditText chatBox = (EditText) findViewById(R.id.chatText);
        Editable chatText = chatBox.getText();
        chatBox.setText("");

        addConversationToTheBox(chatText.toString());

        Call<ResponseBody> conversation = GroupmeServerService.getServiceInstance().getService().createConversation(groupId, chatText.toString());
        final ConversationActivity currentActivity = this;
        conversation.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(currentActivity, "Success saved conversation", Toast.LENGTH_SHORT);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(currentActivity, "failed saved conversation", Toast.LENGTH_SHORT);
            }
        });
    }

    public void addConversationToTheBox(String chatText) {
        LinearLayout conversationDashboard = (LinearLayout) findViewById(R.id.dashboard);
        TextView child = new TextView(this);
        child.setText(chatText);
        child.setPadding(20, 20, 20, 20);
        child.setBackground(getResources().getDrawable(R.drawable.rounded_corner));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 10, 0 , 10);
        child.setLayoutParams(layoutParams);
        conversationDashboard.addView(child);


    }
}
