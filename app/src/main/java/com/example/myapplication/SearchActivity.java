package com.example.myapplication;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    EditText editSearch;
    RecyclerView recyclerSearch;
    SearchAdapter adapter;
    ArrayList<SearchItem> allItems = new ArrayList<>();
    ArrayList<SearchItem> filteredItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editSearch = findViewById(R.id.editSearch);
        recyclerSearch = findViewById(R.id.recyclerSearch);

        allItems.add(new SearchItem("회원1", "member"));
        allItems.add(new SearchItem("회원2", "member"));
        allItems.add(new SearchItem("모임1", "group"));
        allItems.add(new SearchItem("모임2", "group"));

        filteredItems.addAll(allItems);

        adapter = new SearchAdapter(this, filteredItems);
        recyclerSearch.setLayoutManager(new LinearLayoutManager(this));
        recyclerSearch.setAdapter(adapter);

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override public void afterTextChanged(Editable s) {}
        });


        Button btnSearch = findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(v -> {
            String keyword = editSearch.getText().toString().trim();
            filter(keyword);  // 이게 네가 말한 그 filter(text)!
        });

    }
    private void filter(String text) {
        filteredItems.clear();
        for (SearchItem item : allItems) {
            if (item.getName().contains(text)) {
                filteredItems.add(item);
            }
        }
        adapter.notifyDataSetChanged();
    }

}
