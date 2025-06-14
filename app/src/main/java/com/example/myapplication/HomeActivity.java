package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.widget.EditText;
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
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private EditText searchEdit;
    private RecyclerView recyclerView;
    private LinkAdapter linkAdapter;
    private List<Link> linkList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        fab = findViewById(R.id.fabCreateLink);
        searchEdit = findViewById(R.id.editSearch);
        recyclerView = findViewById(R.id.recyclerPopular);

        // 햄버거 메뉴와 툴바 연결
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CreateLinkActivity.class);
            startActivity(intent);
        });

        // 더미 데이터 추가
        linkList = new ArrayList<>();
        linkList.add(new Link("등산 같이해요", "매주 토요일 오전에 등산해요!", 0, -1));
        linkList.add(new Link("스터디모임", "자료구조 같이 공부해요.", 0, 1234));
        linkList.add(new Link("영화 번개", "오늘 심야에 킬링타임 어때요?", 1, -1));

        // 어댑터 연결
        linkAdapter = new LinkAdapter(linkList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(linkAdapter);

        FloatingActionButton fab = findViewById(R.id.fabCreateLink);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CreateLinkActivity.class);
            startActivity(intent);
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_logout) {
            Toast.makeText(HomeActivity.this, "로그아웃 되었습니다", Toast.LENGTH_SHORT).show();

            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }, 500);
    } else if (id == R.id.menu_withdraw) {
            // 회원 탈퇴 처리
            new AlertDialog.Builder(this)
                    .setTitle("회원 탈퇴")
                    .setMessage("정말로 탈퇴하시겠습니까?")
                    .setPositiveButton("예", (dialog, which) -> {
                        // DB에서 사용자 삭제 처리 등
                        Toast.makeText(this, "탈퇴 완료", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    })
                    .setNegativeButton("아니오", null)
                    .show();
        } else if (id == R.id.menu_friends) {
            Intent intent = new Intent(this, FriendListActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_report) {
            Intent intent = new Intent(this, ReportActivity.class);
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


}

