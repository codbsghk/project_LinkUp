package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText idEdit, pwEdit;
    Button loginBtn;
    TextView goRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        idEdit = findViewById(R.id.editLoginId);
        pwEdit = findViewById(R.id.editLoginPw);
        loginBtn = findViewById(R.id.btnLogin);
        goRegister = findViewById(R.id.tvGoRegister);

        loginBtn.setOnClickListener(v -> {
            String inputId = idEdit.getText().toString();
            String inputPw = pwEdit.getText().toString();

            SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
            String savedId = prefs.getString("id", "");
            String savedPw = prefs.getString("pw", "");

            if (inputId.equals(savedId) && inputPw.equals(savedPw)) {
                Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show();
                // 홈 화면으로 이동하거나 이후 처리
            } else {
                Toast.makeText(this, "아이디 또는 비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
            }
        });

        goRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish(); // 로그인 화면 종료
        });

        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();  // 이전 로그인 액티비티 종료

    }
}
