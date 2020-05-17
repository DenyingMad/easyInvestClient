package com.cgpanda.easyinvest.View.Fragments;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.cgpanda.easyinvest.Adapters.PortfolioActivesAdapter;
import com.cgpanda.easyinvest.Entity.Securities.InstrumentTypes;
import com.cgpanda.easyinvest.Entity.Securities.Securities;
import com.cgpanda.easyinvest.R;

import java.util.ArrayList;
import java.util.List;

public class PortfolioActivesFragment extends Fragment {

    private PortfolioActivesAdapter activesAdapter;

    private List<Securities> securitiesList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_portfolio_actives_fragment, container, false);

        // todo Set up the recyclerview

        securitiesList.add(new Securities("Сбербанк","SBER", 183.85, 183.81, InstrumentTypes.CommonStock));
        securitiesList.add(new Securities("Татнфт 3ао","TATN", 514.2, 522.6, InstrumentTypes.CommonStock));
        securitiesList.add(new Securities("Yandex clA","YNDX", 2839.6, 2821.8, InstrumentTypes.CommonStock));
        securitiesList.add(new Securities("Аэрофлот","AFTL", 70.94, 72.16, InstrumentTypes.CommonStock));
        securitiesList.add(new Securities("ОФЗ 26233","", 103.6500, 183.81, InstrumentTypes.CommonStock));
        securitiesList.add(new Securities("ОФЗ 26236","", 183.85, 183.81, InstrumentTypes.CommonStock));
        securitiesList.add(new Securities("ОФЗ 26232","", 183.85, 183.81, InstrumentTypes.CommonStock));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initActivesRecyclerView();
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
                outRect.bottom = 50;
                if (pos == 0){
                    outRect.top = 50;
                }
            }
        });
    }
}
