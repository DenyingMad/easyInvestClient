package com.cgpanda.easyinvest.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cgpanda.easyinvest.Adapters.Portfolio.PortfolioPageAdapter;
import com.cgpanda.easyinvest.Entity.Securities.CommonStock.CommonStock;
import com.cgpanda.easyinvest.R;
import com.cgpanda.easyinvest.View.Fragments.portfolio.PortfolioActivesFragment;
import com.cgpanda.easyinvest.View.Fragments.portfolio.PortfolioAnalyzeFragment;
import com.cgpanda.easyinvest.View.Fragments.portfolio.PortfolioDividendsFragment;
import com.cgpanda.easyinvest.View.Fragments.portfolio.PortfolioNewsFragment;
import com.cgpanda.easyinvest.View.Fragments.portfolio.PortfolioPredictionFragment;
import com.cgpanda.easyinvest.View.Fragments.securities.SecurityTabChartFragment;
import com.cgpanda.easyinvest.View.Fragments.securities.SecurityTabOverviewFragment;
import com.cgpanda.easyinvest.ViewModel.SecuritiesViewModel;
import com.google.android.material.tabs.TabLayout;

public class CommonStockActivity extends AppCompatActivity {

    private SecuritiesViewModel securitiesViewModel;
    private CommonStock.StaticData staticData;
    private CommonStock.MarketData marketData;

    private ProgressBar progressBar;
    private LinearLayout linearLayout;
    private TextView shortName, secid, price, change, arrow;
    private Boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equity);

        setUpUI();

        securitiesViewModel = ViewModelProviders.of(this).get(SecuritiesViewModel.class);
        securitiesViewModel.init();

        securitiesViewModel.getIsLoading().observe(this, this::setProgressBar);

        securitiesViewModel.observeCommonStock().observe(this, commonStock -> {
            if (isFirst){
                linearLayout.setVisibility(View.VISIBLE);
                setUpTabs();
            }
            staticData = commonStock.getStaticDataList().get(0);
            marketData = commonStock.getMarketDataList().get(0);

            setUpData();
        });

        Intent i = getIntent();
        CommonStock.StaticData staticData = i.getParcelableExtra("static_data");
        String market = i.getStringExtra("market");

        securitiesViewModel.getCommonStock(market, staticData.getBoardid(), staticData.getSecid());

    }

    private void setUpData(){
        shortName.setText(staticData.getShortName());
        secid.setText(staticData.getSecid());
        price.setText(marketData.getLast() + "₽");

        switch (marketData.getLastChange().signum()){
            case 0:
                change.setText(marketData.getLastChangePrcnt() + "% (" + marketData.getLastChange() + "₽)" );
                change.setTextColor(getResources().getColor(R.color.neutral));
                arrow.setText("");
                break;
            case 1:
                change.setText("+" + marketData.getLastChangePrcnt() + "% (+" + marketData.getLastChange() + "₽)" );
                change.setTextColor(getResources().getColor(R.color.accentGreen));
                arrow.setText(getString(R.string.up_arrow));
                arrow.setTextColor(getResources().getColor(R.color.accentGreen));
                break;
            case -1:
                change.setText(marketData.getLastChangePrcnt() + "% (" + marketData.getLastChange() + "₽)" );
                change.setTextColor(getResources().getColor(R.color.accentRed));
                arrow.setText(getString(R.string.bottom_arrow));
                arrow.setTextColor(getResources().getColor(R.color.accentRed));
                break;
            default:
                break;
        }
    }
    private void setUpTabs(){
        PortfolioPageAdapter portfolioPageAdapter = new PortfolioPageAdapter(getSupportFragmentManager());
        ViewPager tabsPager = findViewById(R.id.security_tabs_container_vp);

        PortfolioPageAdapter adapter = new PortfolioPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new SecurityTabChartFragment(), "График");
        adapter.addFragment(new SecurityTabOverviewFragment(), "Обзор");
        adapter.addFragment(new PortfolioAnalyzeFragment(), "Тех.Анализ");
        adapter.addFragment(new PortfolioPredictionFragment(), "Прогнозы");
        adapter.addFragment(new PortfolioDividendsFragment(), "Выплаты"); // todo Заменить
        adapter.addFragment(new PortfolioNewsFragment(), "Новости");

        tabsPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.security_tabs);
        tabLayout.setupWithViewPager(tabsPager);
        tabLayout.setTabMode(TabLayout.MODE_AUTO);
    }

    private void setProgressBar(Boolean isLoading){
        if (isLoading){
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void setUpUI(){
        progressBar = findViewById(R.id.activity_equity_pb);
        linearLayout = findViewById(R.id.activity_equity_layout);
        shortName = findViewById(R.id.security_short_name);
        secid = findViewById(R.id.security_secid);
        price = findViewById(R.id.security_price);
        change = findViewById(R.id.dynamics);
        arrow = findViewById(R.id.dynamics_arrow);
    }
}