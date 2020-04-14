package com.cgpanda.easyinvest.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.cgpanda.easyinvest.Adapters.StoryAdapter;
import com.cgpanda.easyinvest.Entity.Story;
import com.cgpanda.easyinvest.R;
import com.cgpanda.easyinvest.ViewModel.StoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private StoryViewModel storyViewModel;

    private StoryAdapter storyAdapter;
    List<Story> storyList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        storyViewModel = ViewModelProviders.of(this).get(StoryViewModel.class);
        storyViewModel.init();
        storyViewModel.getStories().observe(this, new Observer<List<Story>>() {
            @Override
            public void onChanged(@Nullable List<Story> stories) {
                storyList.clear();
                storyList.addAll(stories);
                storyAdapter.notifyDataSetChanged();
            }
        });

        initRecyclerView();
    }


    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.story_recycler_view);
        storyAdapter = new StoryAdapter(this, storyList);
        recyclerView.setAdapter(storyAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
    }
}
