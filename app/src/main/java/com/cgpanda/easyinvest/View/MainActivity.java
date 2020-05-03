package com.cgpanda.easyinvest.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.cgpanda.easyinvest.View.Fragments.ArchiveFragment;
import com.cgpanda.easyinvest.View.Fragments.EquityFragment;
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
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()){
                case R.id.home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.news:
                    selectedFragment = new NewsFragment();
                    break;
                case R.id.equities:
                    selectedFragment = new EquityFragment();
                    break;
                case R.id.stories:
                    selectedFragment = new ArchiveFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
    };
}
