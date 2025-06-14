package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    private ArrayList<Schedule> scheduleList;
    private ArrayList<Boolean> joinedList;

    public ScheduleAdapter(ArrayList<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
        this.joinedList = new ArrayList<>();
        for (int i = 0; i < scheduleList.size(); i++) {
            joinedList.add(false); // 초기엔 모두 참여하지 않은 상태
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_schedule, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Schedule schedule = scheduleList.get(position);
        boolean isJoined = joinedList.get(position);

        holder.tvTitle.setText(schedule.getTitle());
        holder.tvDate.setText(schedule.getDate());
        holder.tvCount.setText(isJoined ? "1명 참여 중" : "0명 참여 중");
        holder.btnJoin.setText(isJoined ? "참여취소" : "참여하기");

        holder.btnJoin.setOnClickListener(v -> {
            boolean newState = !joinedList.get(position);
            joinedList.set(position, newState);
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDate, tvCount;
        Button btnJoin;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvScheduleTitle);
            tvDate = itemView.findViewById(R.id.tvScheduleDate);
            tvCount = itemView.findViewById(R.id.tvParticipantCount);
            btnJoin = itemView.findViewById(R.id.btnJoin);
        }
    }
}
