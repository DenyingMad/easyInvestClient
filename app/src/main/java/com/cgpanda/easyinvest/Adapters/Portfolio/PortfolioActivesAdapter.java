package com.cgpanda.easyinvest.Adapters.Portfolio;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cgpanda.easyinvest.Entity.PortfolioSecurities.PortfolioSecurities;
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
        BigDecimal currentPrice = securitiesList.get(position).getSecurities().getPortfolioSecuritiesMarketData().get(0).getLast().multiply(BigDecimal.valueOf(amount));
        BigDecimal lastPrice = securitiesList.get(position).getSecurities().getPortfolioSecuritiesStaticData().get(0).getPrevPrice().multiply(BigDecimal.valueOf(amount));

        String changePercent = currentPrice
                .divide(lastPrice, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .subtract(BigDecimal.valueOf(100))
                .stripTrailingZeros().toPlainString() + "%";
        String changeValue = currentPrice.subtract(lastPrice)
                .setScale(4, RoundingMode.HALF_UP)
                .stripTrailingZeros().toPlainString() + "₽)";


        String current = currentPrice.stripTrailingZeros().toPlainString() + "₽";
        String change = changePercent + " (" + changeValue;

        switch (currentPrice.subtract(lastPrice).signum()){
            case -1: {
                // negative
                holder.changePrice.setTextColor(holder.redColor);
                break;
            }
            case 0: {
                //zero
                holder.changePrice.setTextColor(holder.neuralColor);
                break;
            }
            case 1:
                //positive
                change = "+" + changePercent + " (+" + changeValue;
                holder.changePrice.setTextColor(holder.greenColor);
                break;
        }

        holder.changePrice.setText(change);
        holder.currentPrice.setText(current);
        holder.shortName.setText(securitiesList.get(position).getSecurities().getPortfolioSecuritiesStaticData().get(0).getShortName());
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

        int greenColor;
        int redColor;
        int neuralColor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shortName = itemView.findViewById(R.id.securities_short_name);
            currentPrice = itemView.findViewById(R.id.securities_current_price);
            changePrice = itemView.findViewById(R.id.securities_change);
            amount = itemView.findViewById(R.id.securities_amount);
            greenColor = itemView.getResources().getColor(R.color.accentGreen);
            redColor = itemView.getResources().getColor(R.color.accentRed);
            neuralColor = itemView.getResources().getColor(R.color.neutral);
        }
    }
}
