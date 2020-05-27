package com.cgpanda.easyinvest.Adapters.StockQuotes;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cgpanda.easyinvest.Entity.Securities.CommonStock.CommonStock;
import com.cgpanda.easyinvest.R;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CommonStocksAdapter extends RecyclerView.Adapter<CommonStocksAdapter.ViewHolder> {
    private static final String TAG = "CommonStocksAdapter";
    private CommonStock commonStockList;
    private OnSecurityClickListener onSecurityClickListener;

    public CommonStocksAdapter(CommonStock commonStockList, OnSecurityClickListener onSecurityClickListener) {
        this.commonStockList = commonStockList;
        this.onSecurityClickListener = onSecurityClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.common_stock_list_element, parent, false);
        return new ViewHolder(view, onSecurityClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CommonStock.MarketData marketData = commonStockList.getMarketDataList().get(position);
        CommonStock.StaticData staticData = commonStockList.getStaticDataList().get(position);

        holder.shortName.setText(staticData.getShortName());
        holder.secid.setText(staticData.getSecid());

        BigDecimal currentPrice = marketData.getLast();
        int decimal = staticData.getDecimals();
        BigDecimal changeValue = marketData.getLastChange();
        BigDecimal changePercent = marketData.getLastChangePrcnt();

        Log.d(TAG, "onBindViewHolder: " + currentPrice);
        holder.price.setText(currentPrice.setScale(decimal, RoundingMode.HALF_UP) + "₽");

        switch (changeValue.signum()){
            case 0:
                holder.change.setText(changePercent + "% (" + changeValue + "₽)");
                holder.arrow.setText("");
                break;
            case 1:
                holder.change.setText("+" + changePercent + "% (+" + changeValue + "₽)");
                holder.arrow.setText(R.string.up_arrow);
                holder.arrow.setTextColor(holder.setGreen());
                break;
            case -1:
                holder.change.setText(changePercent + "% (" + changeValue + "₽)");
                holder.arrow.setText(R.string.bottom_arrow);
                holder.arrow.setTextColor(holder.setRed());
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return commonStockList.getMarketDataList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView shortName;
        public TextView secid;
        public TextView price;
        public TextView change;
        public TextView arrow;
        OnSecurityClickListener onSecurityClickListener;
        public ViewHolder(@NonNull View itemView, OnSecurityClickListener onSecurityClickListener) {
            super(itemView);
            this.onSecurityClickListener = onSecurityClickListener;
            itemView.setOnClickListener(this);
            shortName = itemView.findViewById(R.id.common_stock_short_name);
            secid = itemView.findViewById(R.id.common_stock_secid);
            price = itemView.findViewById(R.id.common_stock_price);
            change = itemView.findViewById(R.id.common_stock_change);
            arrow = itemView.findViewById(R.id.c_stock_change_arrow);
        }
        public int setGreen(){
            return itemView.getResources().getColor(R.color.accentGreen);
        }
        public int setRed(){
            return itemView.getResources().getColor(R.color.accentRed);
        }

        @Override
        public void onClick(View v) {
            onSecurityClickListener.onSecurityClick(getAdapterPosition());
        }
    }
    public interface OnSecurityClickListener{
        void onSecurityClick(int position);
    }
}
