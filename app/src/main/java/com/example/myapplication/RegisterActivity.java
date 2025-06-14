package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText nameEdit, idEdit, pwEdit, phoneEdit, emailEdit;
    Button registerBtn, btnCheckId, btnCheckPhone;
    TextView tvIdWarning, tvPhoneWarning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // EditText 연결
        nameEdit = findViewById(R.id.editName);
        idEdit = findViewById(R.id.editId);
        pwEdit = findViewById(R.id.editPassword);
        phoneEdit = findViewById(R.id.editPhone);
        emailEdit = findViewById(R.id.editEmail);

        // 버튼 & 경고 텍스트뷰 연결
        registerBtn = findViewById(R.id.btnRegister);
        btnCheckId = findViewById(R.id.btnCheckId);
        btnCheckPhone = findViewById(R.id.btnCheckPhone);
        tvIdWarning = findViewById(R.id.tvIdWarning);
        tvPhoneWarning = findViewById(R.id.tvPhoneWarning);

        // ID 중복 확인
        btnCheckId.setOnClickListener(v -> {
            String inputId = idEdit.getText().toString();
            SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
            String savedId = prefs.getString("id", "");

            if (inputId.equals(savedId)) {
                tvIdWarning.setVisibility(View.VISIBLE);
            } else {
                tvIdWarning.setVisibility(View.GONE);
                Toast.makeText(this, "사용 가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        // 전화번호 중복 확인
        btnCheckPhone.setOnClickListener(v -> {
            String inputPhone = phoneEdit.getText().toString();
            SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
            String savedPhone = prefs.getString("phone", "");

            if (inputPhone.equals(savedPhone)) {
                tvPhoneWarning.setVisibility(View.VISIBLE);
            } else {
                tvPhoneWarning.setVisibility(View.GONE);
                Toast.makeText(this, "사용 가능한 전화번호입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        // 회원가입 버튼 클릭
        registerBtn.setOnClickListener(v -> {

            String id = idEdit.getText().toString();
            String pw = pwEdit.getText().toString();
            String phone = phoneEdit.getText().toString();

            if (id.isEmpty() || pw.isEmpty() || phone.isEmpty() || nameEdit.getText().toString().isEmpty()) {
                Toast.makeText(this, "아이디, 비밀번호, 전화번호는 필수입니다.", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("id", id);
            editor.putString("pw", pw);
            editor.putString("phone", phone);
            editor.putString("name", nameEdit.getText().toString());  // 중복 체크용으로 저장
            editor.apply();

            Toast.makeText(this, "회원가입 성공!", Toast.LENGTH_SHORT).show();
            finish(); // 로그인 화면으로 돌아감
        });
    }
}
