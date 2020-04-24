package com.cgpanda.easyinvest.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cgpanda.easyinvest.Entity.Story;
import com.cgpanda.easyinvest.R;
import com.cgpanda.easyinvest.View.ArchiveActivity;
import com.cgpanda.easyinvest.View.EpisodeActivity;

import java.util.List;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder> {

    private Context context;
    private List<Story> storyList;

    public StoryAdapter(Context context, List<Story> storyList) {
        this.context = context;
        this.storyList = storyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.story_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (position != 4) {
            RequestOptions options = new RequestOptions().placeholder(R.drawable.ic_launcher_foreground);
            Glide.with(context)
                    .load(storyList.get(position).getImageUrl())
                    .apply(options)
                    .into(holder.imageView);
            holder.textView.setText(storyList.get(position).getTitle());
        }
        else{
            Glide.with(context)
                    .load(storyList.get(position).getImageUrl())
                    .into(holder.imageView);
            holder.textView.setText(storyList.get(position).getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.story_title);
            imageView = itemView.findViewById(R.id.story_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != 4) {
                        Intent i = new Intent(context, EpisodeActivity.class);
                        i.putExtra("story", storyList.get(position).getStory_id() + "");
                        context.startActivity(i);
                    }else{
                        Intent i = new Intent(context, ArchiveActivity.class);
                        context.startActivity(i);
                    }
                }
            });


        }
    }
}
