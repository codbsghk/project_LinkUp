package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import java.util.ArrayList;
import android.content.Intent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class LinkRoomActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private TextView tvIntro;
    private FloatingActionButton fabChat;
    private CalendarView calendarView;


    private RecyclerView scheduleRecyclerView;
    private ScheduleAdapter scheduleAdapter;
    private ArrayList<Schedule> scheduleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_room);

        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        tvIntro = findViewById(R.id.tvIntro);
        fabChat = findViewById(R.id.fabChat);
        FloatingActionButton fabChat = findViewById(R.id.fabChat);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // ← 버튼 보이게
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home); // 아이콘을 🏠로 바꿔줘
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        // 모임 정보 받기
        Intent intent = getIntent();
        String linkName = intent.getStringExtra("linkName");
        String linkIntro = intent.getStringExtra("linkIntro");

        fabChat.setOnClickListener(v -> {
            Intent chatIntent = new Intent(this, GroupChatActivity.class);
            chatIntent.putExtra("groupName", linkName);
            startActivity(chatIntent);
        });


        if (linkName != null) {
            toolbar.setTitle(linkName);
        }
        if (linkIntro != null) {
            tvIntro.setText(linkIntro);
        }

        ImageButton btnAddSchedule = findViewById(R.id.btnAddSchedule);

        btnAddSchedule.setOnClickListener(v -> {
            Intent scheduleintent = new Intent(this, AddScheduleActivity.class);
            startActivityForResult(scheduleintent, 100);
        });


        // 채팅 버튼
        fabChat.setOnClickListener(v -> {
            Intent chatintent = new Intent(this, GroupChatActivity.class);
            chatintent.putExtra("groupName", toolbar.getTitle().toString());  // 현재 모임 이름 전달
            startActivity(chatintent);
        });

        // 일정 RecyclerView 설정
        scheduleRecyclerView = findViewById(R.id.scheduleRecyclerView);
        scheduleList = new ArrayList<>();
        scheduleList.add(new Schedule("정모 준비 회의", "2025-06-08"));
        scheduleList.add(new Schedule("스터디 정리", "2025-06-10"));

        scheduleAdapter = new ScheduleAdapter(scheduleList);
        scheduleRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        scheduleRecyclerView.setAdapter(scheduleAdapter);

        calendarView = findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
            Toast.makeText(this, selectedDate + " 선택됨", Toast.LENGTH_SHORT).show();
            // TODO: 선택된 날짜에 맞는 일정 필터링 or 다른 처리 가능
        });


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_member_list) {
            Toast.makeText(this, "모임원 목록 (예정)", Toast.LENGTH_SHORT).show();
        } else  if (id == R.id.menu_delete) {
            new AlertDialog.Builder(this)
                    .setTitle("모임 삭제")
                    .setMessage("정말 이 모임을 삭제하시겠습니까?")
                    .setPositiveButton("예", (dialog, which) -> {
                        deleteGroup(toolbar.getTitle().toString());
                    })
                    .setNegativeButton("아니요", null)
                    .show();
        } else if (id == R.id.menu_eval) {
            Intent intent = new Intent(this, EstimationActivity.class);
            intent.putExtra("linkName", toolbar.getTitle().toString()); // 모임 이름 넘기기
            startActivity(intent);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // ← 또는 🏠 버튼 눌렀을 때 동작
            Intent intent = new Intent(LinkRoomActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item); // 다른 메뉴 눌렀을 때 기본 처리
    }

    private void deleteGroup(String groupName) {
        ChatDBHelper dbHelper = new ChatDBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 관련 테이블 모두 삭제 (예: chat, schedule, etc.)
        db.delete("chat", "groupName = ?", new String[]{groupName});
        db.delete("schedule", "groupName = ?", new String[]{groupName});
        db.delete("evaluation", "groupName = ?", new String[]{groupName});
        // 필요한 테이블 더 추가 가능

        Toast.makeText(this, "모임이 삭제되었습니다", Toast.LENGTH_SHORT).show();

        // 홈으로 이동
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            String title = data.getStringExtra("scheduleTitle");
            String date = data.getStringExtra("scheduleDate");

            scheduleList.add(new Schedule(title, date));
            scheduleAdapter.notifyItemInserted(scheduleList.size() - 1);
        }
    }



}

