package com.cgpanda.easyinvest.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cgpanda.easyinvest.Entity.Equity;
import com.cgpanda.easyinvest.R;
import com.cgpanda.easyinvest.View.EquityActivity;

import java.util.List;

public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.ViewHolder> {

    private Context context;
    private List<Equity> activesList;

    public QuotesAdapter(Context context, List<Equity> activesList) {
        this.context = context;
        this.activesList = activesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quotes_widget_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.shortName.setText(activesList.get(position).getShortName());
        holder.fullName.setText(activesList.get(position).getFullName());
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView shortName;
        private TextView fullName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shortName = itemView.findViewById(R.id.short_name);
            fullName = itemView.findViewById(R.id.full_name);
            itemView.setOnClickListener((view) -> {
                Intent intent = new Intent(context, EquityActivity.class);
                context.startActivity(intent);
            });

        }
    }
}
