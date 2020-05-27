package com.cgpanda.easyinvest.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.cgpanda.easyinvest.View.Fragments.ArchiveFragment;
import com.cgpanda.easyinvest.View.Fragments.SecuritiesFragment;
import com.cgpanda.easyinvest.View.Fragments.portfolio.PortfolioFragment;
import com.cgpanda.easyinvest.View.Fragments.HomeFragment;
import com.cgpanda.easyinvest.R;
import com.cgpanda.easyinvest.View.Fragments.NewsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_nav);
        bottomNavigation.setOnNavigationItemSelectedListener(navListener);

        if(savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        Fragment selectedFragment = null;
        switch (item.getItemId()){
            case R.id.home:
                selectedFragment = new HomeFragment();
                break;
            case R.id.news:
                selectedFragment = new NewsFragment();
                break;
            case R.id.portfolio:
                selectedFragment = new PortfolioFragment();
                break;
            case R.id.stories:
                selectedFragment = new ArchiveFragment();
                break;
            case R.id.securities:
                selectedFragment = new SecuritiesFragment();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
        return true;
    };
}
