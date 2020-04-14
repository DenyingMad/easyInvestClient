package com.cgpanda.easyinvest.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.cgpanda.easyinvest.Adapters.EpisodeAdapter;
import com.cgpanda.easyinvest.Entity.Episode;
import com.cgpanda.easyinvest.R;
import com.cgpanda.easyinvest.ViewModel.StoryViewModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.lang.Comparable;
import java.util.List;

public class EpisodeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private EpisodeAdapter adapter;
    private List<Episode> episodeList = new ArrayList<>();

    private StoryViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode);
        Intent intent = getIntent();
        String selectedStory = intent.getStringExtra("story");
        viewModel = ViewModelProviders.of(this).get(StoryViewModel.class);
        viewModel.init();
        viewModel.getEpisodes(selectedStory).observe(this, new Observer<List<Episode>>() {
            @Override
            public void onChanged(List<Episode> episodes) {
                episodeList.clear();
                episodeList.addAll(episodes);


                adapter.notifyDataSetChanged();
            }
        });

        initViewPager();
    }

    public void initViewPager(){

        adapter = new EpisodeAdapter(this, episodeList);
        viewPager = findViewById(R.id.episode_vp);
        viewPager.setAdapter(adapter);
    }



}
