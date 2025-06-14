package com.example.myapplication;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText editMessage;
    private Button btnSend;
    private ChatAdapter adapter;
    private ArrayList<ChatMessage> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        String friendName = getIntent().getStringExtra("friendName");
        setTitle(friendName + "님과의 채팅"); // 툴바 제목 바꾸기

        recyclerView = findViewById(R.id.recyclerViewChat);
        editMessage = findViewById(R.id.editMessage);
        btnSend = findViewById(R.id.btnSend);
        messageList = new ArrayList<>();
        adapter = new ChatAdapter(messageList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = editMessage.getText().toString().trim();
                if (!content.isEmpty()) {
                    String time = DateFormat.format("HH:mm", new Date()).toString();
                    ChatMessage msg = new ChatMessage("나", content, time);
                    messageList.add(msg);
                    adapter.notifyItemInserted(messageList.size() - 1);
                    recyclerView.scrollToPosition(messageList.size() - 1);
                    editMessage.setText("");
                }
            }
        });

        btnSend.setOnClickListener(v -> {
            String sender = CurrentUser.getNickname();
            String content = editMessage.getText().toString().trim();
            String time = getCurrentTime();

            if (!content.isEmpty()) {
                ChatMessage message = new ChatMessage(sender, content, time);
                messageList.add(message);
                adapter.notifyItemInserted(messageList.size() - 1);

                recyclerView.scrollToPosition(messageList.size() - 1);
                editMessage.setText("");
            }
        });



    }

    private String getCurrentTime() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("a hh:mm", java.util.Locale.getDefault());
        return sdf.format(new java.util.Date());
    }

}
