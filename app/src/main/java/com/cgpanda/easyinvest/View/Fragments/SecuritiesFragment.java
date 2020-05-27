package com.cgpanda.easyinvest.View.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.cgpanda.easyinvest.Adapters.StockQuotes.SecuritiesPageAdapter;
import com.cgpanda.easyinvest.R;
import com.cgpanda.easyinvest.View.Fragments.securities.CommonStocksFragment;
import com.google.android.material.tabs.TabLayout;

public class SecuritiesFragment extends Fragment {

    private ViewPager viewPager;
    private SecuritiesPageAdapter securitiesPageAdapter;

    private static final String TAG = "SecuritiesFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "sec onCreate: ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "sec onCreateView: ");
        return inflater.inflate(R.layout.fragment_securities, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "sec onViewCreated: ");
        initUi();
        setUpTabs();
    }

    private void setUpTabs(){
        Log.d(TAG, "sec setUpTabs: ");
        securitiesPageAdapter = new SecuritiesPageAdapter(getFragmentManager());

        SecuritiesPageAdapter adapter = new SecuritiesPageAdapter(getFragmentManager());
        adapter.addFragment(new CommonStocksFragment(), "Акции");

        viewPager.setAdapter(adapter);

        TabLayout tabLayout = getActivity().findViewById(R.id.securities_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_AUTO);
    }

    private void initUi(){
        Log.d(TAG, "sec initUi: ");
        viewPager = getActivity().findViewById(R.id.securities_list_container);
    }
}
