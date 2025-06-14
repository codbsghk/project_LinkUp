package com.example.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ReportActivity extends AppCompatActivity {

    private EditText editReporter, editReason;
    private Button btnSubmitReport, btnGoHome;
    private ChatDBHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        editReporter = findViewById(R.id.editReporter);
        editReason = findViewById(R.id.editReason);
        btnSubmitReport = findViewById(R.id.btnSubmitReport);
        btnGoHome = findViewById(R.id.btnGoHome);

        dbHelper = new ChatDBHelper(this);
        database = dbHelper.getWritableDatabase();

        // Create report table if it doesn't exist
        database.execSQL("CREATE TABLE IF NOT EXISTS report (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "reporter TEXT," +
                "reason TEXT," +
                "timestamp TEXT)");

        btnSubmitReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reporter = editReporter.getText().toString().trim();
                String reason = editReason.getText().toString().trim();

                if (reporter.isEmpty() || reason.isEmpty()) {
                    Toast.makeText(ReportActivity.this, "모든 항목을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                ContentValues values = new ContentValues();
                values.put("reporter", reporter);
                values.put("reason", reason);
                values.put("timestamp", System.currentTimeMillis());

                long result = database.insert("report", null, values);

                if (result != -1) {
                    Toast.makeText(ReportActivity.this, "신고가 접수되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ReportActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(ReportActivity.this, "신고 접수에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnGoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}
