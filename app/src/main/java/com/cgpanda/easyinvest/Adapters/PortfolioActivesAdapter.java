package com.cgpanda.easyinvest.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cgpanda.easyinvest.Entity.Securities.Securities;
import com.cgpanda.easyinvest.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class PortfolioActivesAdapter extends RecyclerView.Adapter<PortfolioActivesAdapter.ViewHolder> {

    private List<Securities> securitiesList;

    public PortfolioActivesAdapter(List<Securities> securityList) {
        this.securitiesList = securityList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.security_element_of_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        double currentPrice = securitiesList.get(position).getCurrentPrice();
        double lastPrice = securitiesList.get(position).getLastPrice();
        String change = BigDecimal.valueOf((currentPrice / lastPrice - 1) * 100).setScale(2, RoundingMode.HALF_UP) + "% (" + BigDecimal.valueOf(currentPrice - lastPrice).setScale(2, RoundingMode.HALF_UP) + "₽)";

        holder.changePrice.setText(change);
        holder.currentPrice.setText(String.valueOf(currentPrice));
        holder.name.setText(securitiesList.get(position).getName());
        holder.shortName.setText(securitiesList.get(position).getShortName());
    }

    @Override
    public int getItemCount() {
        return securitiesList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView shortName;
        TextView currentPrice;
        TextView changePrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.securities_name);
            shortName = itemView.findViewById(R.id.securities_short_name);
            currentPrice = itemView.findViewById(R.id.securities_current_price);
            changePrice = itemView.findViewById(R.id.securities_change);
        }
    }
}
