package com.cgpanda.easyinvest.Adapters;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cgpanda.easyinvest.Entity.Category;
import com.cgpanda.easyinvest.R;

import java.util.List;

public class ArchiveAdapter extends RecyclerView.Adapter<ArchiveAdapter.ViewHolder> {
    private List<Category> categoryList;
    private RecyclerView.RecycledViewPool viewPool;
    private Context context;

    public ArchiveAdapter(List<Category> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.archive_category_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(categoryList.get(position).getCategoryName());

        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.recyclerView.getContext(), RecyclerView.HORIZONTAL, false);
        layoutManager.setInitialPrefetchItemCount(4);
        holder.recyclerView.setLayoutManager(layoutManager);
        holder.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                if (parent.getChildAdapterPosition(view) == 0)
                    outRect.left = 60;
                if (parent.getChildAdapterPosition(view) == 3)
                    outRect.right = 60;
                outRect.right = 40;
            }
        });
        holder.recyclerView.setAdapter(new StoryAdapter(holder.recyclerView.getContext(), categoryList.get(position).getStoryList()));
        holder.recyclerView.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private RecyclerView recyclerView;
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.child_category_rv);
            textView = itemView.findViewById(R.id.category_name);
        }
    }
}
