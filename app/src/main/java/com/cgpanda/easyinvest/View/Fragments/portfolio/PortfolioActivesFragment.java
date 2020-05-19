package com.cgpanda.easyinvest.View.Fragments.portfolio;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.cgpanda.easyinvest.Adapters.PortfolioActivesAdapter;
import com.cgpanda.easyinvest.Entity.Securities.InstrumentTypes;
import com.cgpanda.easyinvest.Entity.Securities.PortfolioSecurities;
import com.cgpanda.easyinvest.Entity.Securities.Securities;
import com.cgpanda.easyinvest.R;
import com.cgpanda.easyinvest.ViewModel.PortfolioViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PortfolioActivesFragment extends Fragment {

    private PortfolioActivesAdapter activesAdapter;
    private PortfolioViewModel portfolioViewModel;
    private SharedPreferences sharedPreferences;
    private List<PortfolioSecurities> securitiesList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_portfolio_actives_fragment, container, false);

        sharedPreferences = Objects.requireNonNull(this.getActivity()).getSharedPreferences("com.cgpanda.easyinvest", Context.MODE_PRIVATE);

        portfolioViewModel = ViewModelProviders.of(this).get(PortfolioViewModel.class);
        portfolioViewModel.init(sharedPreferences.getString(getString(R.string.shared_pref_api_key)+ "_" + getString(R.string.shared_pref_active_email), ""));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setEmptyData();
        portfolioViewModel.getUserPortfolio().observe(getViewLifecycleOwner(), userPortfolio -> {
            if (userPortfolio.getPortfolioSecuritiesList() != null) {
                securitiesList.clear();
                securitiesList.addAll(userPortfolio.getPortfolioSecuritiesList());
                activesAdapter.notifyDataSetChanged();
            }
        });
        initActivesRecyclerView();
    }

    private void setEmptyData() {
        securitiesList.add(new PortfolioSecurities());
        securitiesList.add(new PortfolioSecurities());
        securitiesList.add(new PortfolioSecurities());
    }

    private void initActivesRecyclerView() {
        RecyclerView recyclerView = getActivity().findViewById(R.id.all_actives_in_portfolio_rv);
        activesAdapter = new PortfolioActivesAdapter(securitiesList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(activesAdapter);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int pos = parent.getChildAdapterPosition(view);
                outRect.bottom = 40;
                if (pos == 0){
                    outRect.top = 40;
                }
            }
        });
    }
}
