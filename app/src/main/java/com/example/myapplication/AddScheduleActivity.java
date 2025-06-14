package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddScheduleActivity extends AppCompatActivity {

    EditText editTitle, editDate;
    Button btnAddSchedule;


    Button btnGoBack = findViewById(R.id.btnGoBackToLinkRoom);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule); // 방금 만든 XML과 연결

        editTitle = findViewById(R.id.editTitle);
        editDate = findViewById(R.id.editDate);
        btnAddSchedule = findViewById(R.id.btnAddSchedule);

        editDate.setOnClickListener(v -> showDatePicker());

        btnAddSchedule.setOnClickListener(v -> {
            String title = editTitle.getText().toString().trim();
            String date = editDate.getText().toString().trim();

            if (title.isEmpty() || date.isEmpty()) {
                Toast.makeText(this, "제목과 날짜를 입력하세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent resultIntent = new Intent();
            resultIntent.putExtra("scheduleTitle", title);
            resultIntent.putExtra("scheduleDate", date);
            setResult(RESULT_OK, resultIntent);
            finish(); // 액티비티 종료
        });

        btnGoBack.setOnClickListener(v -> {
            Intent intent = new Intent(AddScheduleActivity.this, LinkRoomActivity.class);
            // 이전에 넘겨받은 모임 이름 다시 전달 (필요한 경우)
            intent.putExtra("LinkName", getIntent().getStringExtra("LinkName"));
            intent.putExtra("LinkIntro", getIntent().getStringExtra("LinkIntro"));
            startActivity(intent);
            finish();
        });
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            String date = year + "-" + String.format("%02d", (month + 1)) + "-" + String.format("%02d", dayOfMonth);
            editDate.setText(date);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}
