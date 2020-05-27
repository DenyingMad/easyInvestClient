package com.cgpanda.easyinvest.View.Fragments.portfolio;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cgpanda.easyinvest.Adapters.Portfolio.PortfolioPageAdapter;
import com.cgpanda.easyinvest.Entity.PortfolioSecurities.PortfolioSecurities;
import com.cgpanda.easyinvest.R;
import com.cgpanda.easyinvest.ViewModel.PortfolioViewModel;
import com.google.android.material.tabs.TabLayout;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

public class PortfolioFragment extends Fragment {
    private static final String TAG = "PortfolioFagment";

    private PortfolioViewModel viewModel;

    private SharedPreferences preferences;

    private ProgressBar portfolioProgressBar;
    private LinearLayout emptyPortfolioLayout;
    private LinearLayout portfolioLayout;
    private ViewPager viewPager;
    private Boolean firstSetUp = true;

    private PortfolioPageAdapter portfolioPageAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = Objects.requireNonNull(this.getActivity()).getSharedPreferences("com.cgpanda.easyinvest", Context.MODE_PRIVATE);
        viewModel = ViewModelProviders.of(this).get(PortfolioViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_portfolio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();

        viewModel.init(preferences.getString(getString(R.string.shared_pref_api_key) + "_" + getString(R.string.shared_pref_active_email), "null"));

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), this::showLoading);

        viewModel.getUserPortfolio().observe(getViewLifecycleOwner(), userPortfolio -> {
            if (userPortfolio.getId() == -1 || userPortfolio.getPortfolioSecuritiesList() == null){
                emptyPortfolioLayout.setVisibility(View.VISIBLE);
            } else{
                if (firstSetUp) {
                    setUpPortfolio();
                }
                portfolioLayout.setVisibility(View.VISIBLE);
                countPortfolioPrice(userPortfolio.getPortfolioSecuritiesList());
                firstSetUp = false;
            }
        });
    }

    private void setUpPortfolio(){
        portfolioPageAdapter = new PortfolioPageAdapter(getFragmentManager());
        viewPager = getActivity().findViewById(R.id.portfolio_tabs_container_vp);

        PortfolioPageAdapter adapter = new PortfolioPageAdapter(getFragmentManager());
        adapter.addFragment(new PortfolioActivesFragment(), "Все активы");
        adapter.addFragment(new PortfolioAnalyzeFragment(), "Тех.анализ");
        adapter.addFragment(new PortfolioPredictionFragment(), "Прогнозы");
        adapter.addFragment(new PortfolioDividendsFragment(), "Выплаты");
        adapter.addFragment(new PortfolioNewsFragment(), "Новости");

        viewPager.setAdapter(adapter);

        TabLayout tabLayout = getActivity().findViewById(R.id.portfolio_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    private void countPortfolioPrice(List<PortfolioSecurities> portfolioSecuritiesList){
        TextView totalPriceTv = getActivity().findViewById(R.id.portfolio_full_price);
        TextView totalChangeTv = getActivity().findViewById(R.id.portfolio_change);
        BigDecimal currentTotalPrice = new BigDecimal("0");
        BigDecimal lastTotalPrice = new BigDecimal("0");
        BigDecimal changePercent;
        BigDecimal changeValue;

        for (PortfolioSecurities portfolioSecurities : portfolioSecuritiesList){
            currentTotalPrice = currentTotalPrice.add(portfolioSecurities.getSecurities().getPortfolioSecuritiesMarketData().get(0).getLast().multiply(BigDecimal.valueOf(portfolioSecurities.getAmount())));
            lastTotalPrice = lastTotalPrice.add(portfolioSecurities.getSecurities().getPortfolioSecuritiesStaticData().get(0).getPrevPrice().multiply(BigDecimal.valueOf(portfolioSecurities.getAmount())));
        }

        String totalPrice = currentTotalPrice.stripTrailingZeros().toPlainString() + "₽";

        changeValue = currentTotalPrice.subtract(lastTotalPrice);
        changePercent = currentTotalPrice.divide(lastTotalPrice, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)).subtract(BigDecimal.valueOf(100));

        String totalChange = changePercent.stripTrailingZeros().toPlainString() + "% (" + changeValue.stripTrailingZeros().toPlainString() + "₽)";

        switch (currentTotalPrice.subtract(lastTotalPrice).signum()){
            case -1:
                // negative
                totalChangeTv.setTextColor(getResources().getColor(R.color.accentRed));
                break;
            case 0:
                // neural
                totalChangeTv.setTextColor(getResources().getColor(R.color.neutral));
                break;
            case 1:
                //positive
                totalChange = "+" + changePercent.stripTrailingZeros().toPlainString() + "% (+" + changeValue.stripTrailingZeros().toPlainString() + "₽)";
                totalChangeTv.setTextColor(getResources().getColor(R.color.accentGreen));
                break;
            default:
                break;
        }
        totalPriceTv.setText(totalPrice);
        totalChangeTv.setText(totalChange);
    }

    private void showLoading(boolean isLoading){
        if (isLoading){
            portfolioProgressBar.setVisibility(View.VISIBLE);
        } else {
            portfolioProgressBar.setVisibility(View.GONE);
        }
    }

    private void initUI(){
        portfolioProgressBar = this.getActivity().findViewById(R.id.portfolio_loading_pb);
        emptyPortfolioLayout = this.getActivity().findViewById(R.id.empty_portfolio_layout);
        portfolioLayout = this.getActivity().findViewById(R.id.portfolio_layout);
    }
}
