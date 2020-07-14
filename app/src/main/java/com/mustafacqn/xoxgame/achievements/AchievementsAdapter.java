package com.mustafacqn.xoxgame.achievements;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.huawei.hms.jos.games.achievement.Achievement;
import com.mustafacqn.xoxgame.R;

import java.util.List;

public class AchievementsAdapter extends RecyclerView.Adapter<AchievementsAdapter.ViewHolder> {

    private List<Achievement> listItems;
    private Context context;

    public AchievementsAdapter(List<Achievement> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.achievements_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AchievementsAdapter.ViewHolder holder, int position) {

        Achievement listItem = listItems.get(position);

        Glide.with(context).load(listItem.getReachedThumbnailUri()).into(holder.imageView);
        holder.textViewHead.setText(listItem.getDisplayName());
        holder.textViewDesc.setText(listItem.getDescInfo());
    }

    @Override
    public int getItemCount() { return listItems.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewHead;
        public TextView textViewDesc;
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewHead = itemView.findViewById(R.id.achievement_header);
            textViewDesc = itemView.findViewById(R.id.achievement_description);
            imageView = itemView.findViewById(R.id.achievement_image);

        }
    }
}
