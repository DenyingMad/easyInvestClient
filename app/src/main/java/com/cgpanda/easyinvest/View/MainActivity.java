package com.cgpanda.easyinvest.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ViewSwitcher;

import com.cgpanda.easyinvest.Adapters.NewsAdapter;
import com.cgpanda.easyinvest.Adapters.QuotesAdapter;
import com.cgpanda.easyinvest.Adapters.StoryAdapter;
import com.cgpanda.easyinvest.Entity.Equity;
import com.cgpanda.easyinvest.Entity.News;
import com.cgpanda.easyinvest.Entity.Story;
import com.cgpanda.easyinvest.R;
import com.cgpanda.easyinvest.ViewModel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel viewModel;

    private StoryAdapter storyAdapter;
    private QuotesAdapter quotesAdapter;
    private NewsAdapter newsAdapter;
    private List<Story> storyList = new ArrayList<>();
    private List<Equity> equityList = new ArrayList<>();
    private List<News> newsList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        storyList.add(new Story(0, "", ""));
        storyList.add(new Story(0, "", ""));
        storyList.add(new Story(0, "", ""));


        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        viewModel.init();
        LiveData<List<Story>> data = viewModel.getStories();
        data.observe(this, stories -> {
            storyList.clear();
            storyList.addAll(stories);
            storyAdapter.notifyDataSetChanged();
        });
        initStoryRecyclerView();


        equityList.add(new Equity(0, " ", " "));
        equityList.add(new Equity(0, " ", " "));
        equityList.add(new Equity(0, " ", " "));
        equityList.add(new Equity(0, " ", " "));
        equityList.add(new Equity(0, " ", " "));
        equityList.add(new Equity(0, " ", " "));

        viewModel.getQuotes().observe(this, equities -> {
            equityList.clear();
            equityList.addAll(equities);
            quotesAdapter.notifyDataSetChanged();
        });

        initQuotesRecyclerView();


        newsList.add(new News(0, "", ""));
        newsList.add(new News(0, "", ""));

        initNewsRecyclerView();
        viewModel.getNews().observe(this, news -> {
            newsList.clear();
            newsList.addAll(news);
            newsAdapter.notifyDataSetChanged();
        });

    }

    public void goToArchive(View v){
        Intent intent = new Intent(this, ArchiveActivity.class);
        startActivity(intent);
    }


    private void initStoryRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.story_recycler_view);
        storyAdapter = new StoryAdapter(this, storyList);
        recyclerView.setAdapter(storyAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                if (parent.getChildAdapterPosition(view) == 0)
                    outRect.left = 60;
                if (parent.getChildAdapterPosition(view) == 3)
                    outRect.right = 60;
                outRect.right = 40;
            }
        });
    }

    private void initQuotesRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.quotes_rv);
        quotesAdapter = new QuotesAdapter(this, equityList);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(quotesAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void initNewsRecyclerView(){
        //TODO Новость должна состоять из картинки и заголовка
        RecyclerView recyclerView = findViewById(R.id.news_rv);
        newsAdapter = new NewsAdapter(this, newsList);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(newsAdapter);
    }


}
