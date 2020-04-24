package com.cgpanda.easyinvest.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.cgpanda.easyinvest.Adapters.EquityAdapter;
import com.cgpanda.easyinvest.R;

public class EquityActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private EquityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equity);
        initViewPager();
    }

    private void initViewPager(){
        adapter = new EquityAdapter();
        viewPager = findViewById(R.id.equity_info_vp);
        viewPager.setAdapter(adapter);
    }
}
