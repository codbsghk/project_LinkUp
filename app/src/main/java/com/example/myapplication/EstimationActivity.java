package com.example.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EstimationActivity extends AppCompatActivity {

    private TextView tvGroupName;
    private RatingBar ratingBar;
    private EditText editComment;
    private Button btnSubmitEval, btnGoBack;

    private SQLiteDatabase database;
    private ChatDBHelper dbHelper;
    private String groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estimation);

        tvGroupName = findViewById(R.id.tvGroupName);
        ratingBar = findViewById(R.id.ratingBar);
        editComment = findViewById(R.id.editComment);
        btnSubmitEval = findViewById(R.id.btnSubmitEval);
        btnGoBack = findViewById(R.id.btnGoBack);

        dbHelper = new ChatDBHelper(this);
        database = dbHelper.getWritableDatabase();

        Intent intent = getIntent();
        groupName = intent.getStringExtra("linkName");
        tvGroupName.setText(groupName);

        database.execSQL("CREATE TABLE IF NOT EXISTS evaluation (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "groupName TEXT," +
                "rating INTEGER," +
                "comment TEXT," +
                "timestamp TEXT)");

        btnSubmitEval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rating = ratingBar.getRating();
                String comment = editComment.getText().toString().trim();

                if (rating == 0 || comment.isEmpty()) {
                    Toast.makeText(EstimationActivity.this, "별점과 한 줄 평가를 모두 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                ContentValues values = new ContentValues();
                values.put("groupName", groupName);
                values.put("rating", (int) rating);
                values.put("comment", comment);
                values.put("timestamp", System.currentTimeMillis());

                long result = database.insert("evaluation", null, values);
                if (result != -1) {
                    Toast.makeText(EstimationActivity.this, "평가가 저장되었습니다!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EstimationActivity.this, LinkRoomActivity.class);
                    intent.putExtra("linkName", groupName);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(EstimationActivity.this, "저장 실패ㅠㅠ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnGoBack.setOnClickListener(v -> {
            Intent goBackIntent = new Intent(EstimationActivity.this, LinkRoomActivity.class);
            goBackIntent.putExtra("linkName", groupName);
            startActivity(goBackIntent);
            finish();
        });

    }
}
