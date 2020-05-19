package com.cgpanda.easyinvest.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cgpanda.easyinvest.Entity.Securities.PortfolioSecurities;
import com.cgpanda.easyinvest.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class PortfolioActivesAdapter extends RecyclerView.Adapter<PortfolioActivesAdapter.ViewHolder> {

    private List<PortfolioSecurities> securitiesList;

    public PortfolioActivesAdapter(List<PortfolioSecurities> securityList) {
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
        int amount = securitiesList.get(position).getAmount();
        double currentPrice = securitiesList.get(position).getCurrentPrice() * amount;
        double lastPrice = securitiesList.get(position).getLastPrice() * amount;

        String change = BigDecimal.valueOf((currentPrice / lastPrice - 1) * 100).setScale(2, RoundingMode.HALF_UP) + "% (" + BigDecimal.valueOf(currentPrice - lastPrice).setScale(2, RoundingMode.HALF_UP) + "₽)";

        holder.changePrice.setText(change);
        holder.currentPrice.setText(String.valueOf(currentPrice));
        holder.shortName.setText(securitiesList.get(position).getShortName());
        holder.amount.setText(String.format("%d шт.", amount));
    }

    @Override
    public int getItemCount() {
        return securitiesList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView shortName;
        TextView currentPrice;
        TextView changePrice;
        TextView amount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shortName = itemView.findViewById(R.id.securities_short_name);
            currentPrice = itemView.findViewById(R.id.securities_current_price);
            changePrice = itemView.findViewById(R.id.securities_change);
            amount = itemView.findViewById(R.id.securities_amount);
        }
    }
}
