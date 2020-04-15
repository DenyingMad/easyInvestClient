package com.cgpanda.easyinvest.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.cgpanda.easyinvest.Adapters.EpisodeAdapter;
import com.cgpanda.easyinvest.Entity.Episode;
import com.cgpanda.easyinvest.R;
import com.cgpanda.easyinvest.ViewModel.EpisodeViewModel;
import com.cgpanda.easyinvest.ViewModel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class EpisodeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private EpisodeAdapter adapter;
    private List<Episode> episodeList = new ArrayList<>();

    private EpisodeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode);
        Intent intent = getIntent();
        String selectedStory = intent.getStringExtra("story");
        viewModel = ViewModelProviders.of(this).get(EpisodeViewModel.class);
        viewModel.init();
        viewModel.getEpisodes(selectedStory).observe(this, episodes -> {
            episodeList.clear();
            episodeList.addAll(episodes);
            adapter.notifyDataSetChanged();
        });
        initViewPager();
    }

    public void initViewPager(){

        adapter = new EpisodeAdapter(this, episodeList);
        viewPager = findViewById(R.id.episode_vp);
        viewPager.setAdapter(adapter);
    }



}
