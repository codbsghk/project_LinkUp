package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private List<SearchItem> itemList;
    private Context context;

    // 요청 완료 여부 저장 (지금은 간단히 List로 처리)
    private Set<String> requestedFriends = new HashSet<>();

    private Set<String> friendPairs = new HashSet<>();
    private String currentUser = "나"; // 임시 현재 사용자 ID (나중에 로그인 정보로 교체 가능)

    private MySQLiteHelper dbHelper;


    public SearchAdapter(Context context, List<SearchItem> itemList) {
        this.context = context;
        this.itemList = itemList;
        this.dbHelper = new MySQLiteHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SearchItem item = itemList.get(position);
        holder.tvName.setText(item.getName());

        if (item.getType().equals("member")) {
            String key = makeFriendKey(currentUser, item.getName());
            boolean alreadyFriends = friendPairs.contains(key);

            holder.btnAction.setImageResource(
                    alreadyFriends ? android.R.drawable.checkbox_on_background : android.R.drawable.ic_input_add
            );
            holder.btnAction.setEnabled(!alreadyFriends);
        } else {
            holder.btnAction.setImageResource(android.R.drawable.ic_media_next);
        }

        holder.btnAction.setOnClickListener(v -> {
            String message = item.getType().equals("member")
                    ? item.getName() + "님에게 친구 요청하시겠습니까?"
                    : item.getName() + " 모임에 가입 요청하시겠습니까?";

            new AlertDialog.Builder(context)
                    .setTitle(item.getName())
                    .setMessage(message)
                    .setPositiveButton("예", (dialog, which) -> {
                        Toast.makeText(context, "요청 보냈습니다!", Toast.LENGTH_SHORT).show();

                        if (item.getType().equals("member")) {
                            String key = makeFriendKey(currentUser, item.getName());
                            friendPairs.add(key);

                            // DB에 저장
                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            String user1 = currentUser;
                            String user2 = item.getName();
                            if (user1.compareTo(user2) > 0) {  // 정렬해서 저장
                                String temp = user1;
                                user1 = user2;
                                user2 = temp;
                            }
                            db.execSQL("INSERT INTO " + MySQLiteHelper.TABLE_FRIENDS +
                                            " (" + MySQLiteHelper.COL_USER1 + ", " + MySQLiteHelper.COL_USER2 + ") VALUES (?, ?)",
                                    new Object[]{user1, user2});

                            notifyItemChanged(holder.getAdapterPosition());
                        }

                    })
                    .setNegativeButton("아니요", null)
                    .show();
        });
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageButton btnAction;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            btnAction = itemView.findViewById(R.id.btnAction);
        }
    }

    private String makeFriendKey(String user1, String user2) {
        return user1.compareTo(user2) < 0 ? user1 + "_" + user2 : user2 + "_" + user1;
    }


}

