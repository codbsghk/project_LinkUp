package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LinkAdapter extends RecyclerView.Adapter<LinkAdapter.ViewHolder> {

    private List<Link> linkList;

    public LinkAdapter(List<Link> linkList) {
        this.linkList = linkList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_link, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Link link = linkList.get(position);
        holder.tvName.setText(link.getName());
        holder.tvDescription.setText(link.getDescription());

        String typeStr = (link.getType() == 0) ? "여러번 모임" : "한번만 모임";
        String openStr = link.isPublic() ? "공개" : "비공개";
        holder.tvMeta.setText(typeStr + " · " + openStr);
    }

    @Override
    public int getItemCount() {
        return linkList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDescription, tvMeta;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvMeta = itemView.findViewById(R.id.tvMeta);
        }
    }
}
