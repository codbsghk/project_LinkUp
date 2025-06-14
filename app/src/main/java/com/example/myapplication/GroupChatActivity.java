package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GroupChatActivity extends AppCompatActivity {

    private RecyclerView groupChatRecyclerView;
    private EditText editGroupMessage;
    private Button btnSendGroupMessage;

    private ArrayList<ChatMessage> groupMessageList;
    private ChatAdapter groupChatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        Toolbar toolbar = findViewById(R.id.groupChatToolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); // ← 버튼 보이기
            actionBar.setHomeAsUpIndicator(R.drawable.ic_home); // 집 아이콘 설정 (ic_home 이미지를 drawable에 넣어야 함)
        }

        groupChatRecyclerView = findViewById(R.id.groupChatRecyclerView);
        editGroupMessage = findViewById(R.id.editGroupMessage);
        btnSendGroupMessage = findViewById(R.id.btnSendGroupMessage);

        groupMessageList = new ArrayList<>();
        groupChatAdapter = new ChatAdapter(groupMessageList);
        groupChatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        groupChatRecyclerView.setAdapter(groupChatAdapter);

        btnSendGroupMessage.setOnClickListener(v -> {
            String sender = CurrentUser.getNickname();
            String content = editGroupMessage.getText().toString().trim();
            String time = getCurrentTime();

            ChatMessage message = new ChatMessage(sender, content, time);
            groupMessageList.add(message);
            groupChatAdapter.notifyItemInserted(groupMessageList.size() - 1);
            editGroupMessage.setText("");
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // ← 또는 집 아이콘 누르면 모임 홈 화면으로
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, LinkRoomActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String getCurrentTime() {
        return new java.text.SimpleDateFormat("HH:mm").format(new java.util.Date());
    }
}
