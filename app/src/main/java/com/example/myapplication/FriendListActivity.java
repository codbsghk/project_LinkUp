package com.example.myapplication;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.content.Intent;


public class FriendListActivity extends AppCompatActivity {

    EditText editSearch;
    RecyclerView friendRecyclerView;
    FriendAdapter adapter;
    ArrayList<Friend> allFriends = new ArrayList<>();
    ArrayList<Friend> filteredFriends = new ArrayList<>();
    MySQLiteHelper dbHelper;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        editSearch = findViewById(R.id.editSearch);
        friendRecyclerView = findViewById(R.id.friendRecyclerView);

        MySQLiteHelper dbHelper = new MySQLiteHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String currentUser = "나"; // 로그인 기능 구현 전까지는 임시로 "나" 고정
        allFriends = new ArrayList<>();
        filteredFriends = new ArrayList<>();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + MySQLiteHelper.TABLE_FRIENDS +
                        " WHERE user1 = ? OR user2 = ?", new String[]{currentUser, currentUser});

        while (cursor.moveToNext()) {
            String user1 = cursor.getString(cursor.getColumnIndexOrThrow("user1"));
            String user2 = cursor.getString(cursor.getColumnIndexOrThrow("user2"));
            String friendName = user1.equals(currentUser) ? user2 : user1;
            allFriends.add(new Friend(friendName));
        }
        cursor.close();

        filteredFriends.addAll(allFriends);

        adapter = new FriendAdapter(this, filteredFriends);
        friendRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        friendRecyclerView.setAdapter(adapter);

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }
            @Override public void afterTextChanged(Editable s) { }
        });

        Toolbar toolbar = findViewById(R.id.friendToolbar);
        setSupportActionBar(toolbar);

// 툴바 왼쪽 상단에 홈 아이콘 표시
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home); // 집 아이콘 직접 사용 가능

    }


    private void filter(String text) {
        filteredFriends.clear();
        for (Friend friend : allFriends) {
            if (friend.getName().contains(text)) {
                filteredFriends.add(friend);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
