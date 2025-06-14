package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class CreateLinkActivity extends AppCompatActivity {

    EditText editLinkName, editIntro;
    RadioGroup radioMeetingCount;
    CheckBox checkNone, checkPassword;
    Button btnCreateLink;
    Button homeButton;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_home) {
            Intent intent = new Intent(this, HomeActivity.class);
            // 홈 화면이 여러 개 쌓이지 않도록 플래그 설정!
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_link);

        editLinkName = findViewById(R.id.editLinkName);
        editIntro = findViewById(R.id.editIntro);
        radioMeetingCount = findViewById(R.id.radioMeetingCount);
        checkNone = findViewById(R.id.checkNone);
        checkPassword = findViewById(R.id.checkPassword);
        btnCreateLink = findViewById(R.id.btnCreateLink);

        homeButton = findViewById(R.id.btnGoHome);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateLinkActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        btnCreateLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editLinkName.getText().toString().trim();
                String intro = editIntro.getText().toString().trim();

                if (name.isEmpty()) {
                    Toast.makeText(CreateLinkActivity.this, "모임 이름을 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String meetCount = "";
                int selectedId = radioMeetingCount.getCheckedRadioButtonId();
                if (selectedId == R.id.radioOnce) meetCount = "한번만 볼래요";
                else if (selectedId == R.id.radioMultiple) meetCount = "여러번 볼래요";
                else meetCount = "선택 안함";

                boolean isNoCondition = checkNone.isChecked();
                boolean isPassword = checkPassword.isChecked();

                // 임시 확인용 출력
                String msg = "모임명: " + name +
                        "\n만남 횟수: " + meetCount +
                        "\n조건 없음: " + isNoCondition +
                        ", 비밀번호: " + isPassword +
                        "\n소개: " + intro;

                Toast.makeText(CreateLinkActivity.this, msg, Toast.LENGTH_LONG).show();

                // TODO: 여기에 실제 저장 처리 or 서버 전송 구현 예정

                Intent intent = new Intent(CreateLinkActivity.this, LinkRoomActivity.class);
                intent.putExtra("linkName", name);
                intent.putExtra("linkIntro", intro);
                startActivity(intent);


            }

        });
    }

}

