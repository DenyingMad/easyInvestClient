package com.cgpanda.easyinvest.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.ViewSwitcher;

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
        holder.shortName.setText(activesList.get(position).getCodeName());
        holder.fullName.setText(activesList.get(position).getShortName());

        if(activesList.get(position).getDynamicPercent() < 0){
            holder.dynamic.setText(activesList.get(position).getDynamicPercent() + "%");
            holder.dynamic.setTextColor(context.getResources().getColor(R.color.accentRed));
        }
        else if(activesList.get(position).getDynamicPercent() > 0){
            holder.dynamic.setText("+" + activesList.get(position).getDynamicPercent() + "%");
            holder.dynamic.setTextColor(context.getResources().getColor(R.color.accentGreen));
        }
    }

    @Override
    public int getItemCount() {
        return activesList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView shortName;
        private TextView fullName;
        private ViewSwitcher viewSwitcher;
        private TextView dynamic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shortName = itemView.findViewById(R.id.short_name);
            fullName = itemView.findViewById(R.id.full_name);
            dynamic = itemView.findViewById(R.id.dynamic_price);

            viewSwitcher = itemView.findViewById(R.id.graph_block_vs);

            Animation inAnim = new AlphaAnimation(0, 1);
            inAnim.setDuration(2000);

            Animation outAnim = new AlphaAnimation(1, 0);
            inAnim.setDuration(2000);

            viewSwitcher.setInAnimation(inAnim);
            viewSwitcher.setOutAnimation(outAnim);

//            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(6);
//            executorService.scheduleWithFixedDelay(() -> {
//                viewSwitcher.showNext();
//            }, 0,3, TimeUnit.SECONDS);



            itemView.setOnClickListener((view) -> {
                Intent intent = new Intent(context, EquityActivity.class);
                context.startActivity(intent);
            });

        }


    }
//    class MyThread extends AsyncTask{
//
//        ViewSwitcher viewSwitcher;
//
//        public MyThread(ViewSwitcher viewSwitcher) {
//            this.viewSwitcher = viewSwitcher;
//        }
//
//        @Override
//        protected Object doInBackground(Object[] objects) {
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Object o) {
//            super.onPostExecute(o);
//
//        }
//    }
}
