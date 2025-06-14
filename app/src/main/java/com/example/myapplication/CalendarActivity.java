package com.example.myapplication;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CalendarActivity extends AppCompatActivity {

    CalendarView calendarView;
    TextView textSelectedDate;
    Button btnAddSchedule;

    String selectedDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);  // ← 레이아웃 이름 주의!

        calendarView = findViewById(R.id.calendarView);
        textSelectedDate = findViewById(R.id.textSelectedDate);
        btnAddSchedule = findViewById(R.id.btnAddSchedule);

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
            textSelectedDate.setText("선택된 날짜: " + selectedDate);
        });

        btnAddSchedule.setOnClickListener(v -> {
            if (selectedDate.isEmpty()) {
                Toast.makeText(this, "먼저 날짜를 선택하세요!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, selectedDate + "에 일정 추가 예정!", Toast.LENGTH_SHORT).show();
                // TODO: 일정 추가 기능 여기 연결 가능
            }
        });
    }
}
