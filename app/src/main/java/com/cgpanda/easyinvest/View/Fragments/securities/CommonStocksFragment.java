package com.cgpanda.easyinvest.View.Fragments.securities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cgpanda.easyinvest.Adapters.StockQuotes.CommonStocksAdapter;
import com.cgpanda.easyinvest.Entity.Securities.CommonStock.CommonStock;
import com.cgpanda.easyinvest.R;
import com.cgpanda.easyinvest.View.CommonStockActivity;
import com.cgpanda.easyinvest.ViewModel.SecuritiesViewModel;

import java.util.ArrayList;

public class CommonStocksFragment extends Fragment implements CommonStocksAdapter.OnSecurityClickListener {
    private static final String TAG = "CommonStocksFragment";

    private CommonStocksAdapter commonStocksAdapter;
    private SecuritiesViewModel securitiesViewModel;
    private CommonStock stocks;

    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_common_stock, container, false);
        Log.d(TAG, "common stock onCreateView: ");
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "common stock onViewCreated: ");
        securitiesViewModel = ViewModelProviders.of(this).get(SecuritiesViewModel.class);
        securitiesViewModel.init();
        progressBar = view.findViewById(R.id.common_stocks_list_pb);
        stocks = new CommonStock();
        stocks.setMarketDataList(new ArrayList<>());
        stocks.setStaticDataList(new ArrayList<>());
        Log.d(TAG, "onViewCreated: setting observers");
        securitiesViewModel.getIsLoading().observe(getViewLifecycleOwner(), this::setProgressBar);
        securitiesViewModel.observeCommonStocksList().observe(getViewLifecycleOwner(), commonStockList -> {
            Log.d(TAG, "onViewCreated: observer onUpdate");
            stocks.getMarketDataList().addAll(commonStockList.getMarketDataList());
            stocks.getStaticDataList().addAll(commonStockList.getStaticDataList());
            commonStocksAdapter.notifyDataSetChanged();
        });
        securitiesViewModel.updateCommonStocks();
        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: set up recycler view");
        RecyclerView recyclerView = getActivity().findViewById(R.id.common_stocks_list_rv);
        commonStocksAdapter = new CommonStocksAdapter(stocks, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(commonStocksAdapter);
    }

    private void setProgressBar(Boolean isLoading){
        if (isLoading){
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSecurityClick(int position) {
        Log.d(TAG, "onSecurityClick: click: " + position);
        Intent intent = new Intent(getContext(), CommonStockActivity.class);
        intent.putExtra("static_data", stocks.getStaticDataList().get(position));
        intent.putExtra("market", "shares");
        startActivity(intent);
    }
}
