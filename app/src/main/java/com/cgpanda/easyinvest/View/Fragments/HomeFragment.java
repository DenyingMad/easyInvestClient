package com.cgpanda.easyinvest.View.Fragments;

import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cgpanda.easyinvest.Adapters.NewsAdapter;
import com.cgpanda.easyinvest.Adapters.QuotesAdapter;
import com.cgpanda.easyinvest.Adapters.StoryAdapter;
import com.cgpanda.easyinvest.Entity.Equity;
import com.cgpanda.easyinvest.Entity.News;
import com.cgpanda.easyinvest.Entity.Story;
import com.cgpanda.easyinvest.R;
import com.cgpanda.easyinvest.ViewModel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel viewModel;
    private StoryAdapter storyAdapter;
    private QuotesAdapter quotesAdapter;
    private NewsAdapter newsAdapter;
    private List<Story> storyList = new ArrayList<>();
    private List<Equity> equityList = new ArrayList<>();
    private List<News> newsList = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        viewModel.init();

        initDefaultStoriesData();
        //Подписка на избранные истории
        viewModel.getFeaturedStories().observe(this, stories -> {
            storyList.clear();
            storyList.addAll(stories);
            storyList.add(new Story(-1, "Больше историй", "https://cdn.pixabay.com/photo/2014/05/25/22/31/icon-354006_960_720.png"));
            storyAdapter.notifyDataSetChanged();
        });

        equityList.add(new Equity(0, " ", " "));
        equityList.add(new Equity(0, " ", " "));
        equityList.add(new Equity(0, " ", " "));
        equityList.add(new Equity(0, " ", " "));
        equityList.add(new Equity(0, " ", " "));
        equityList.add(new Equity(0, " ", " "));

        //Подписка на избранные активы
        viewModel.getQuotes().observe(this, equities -> {
            equityList.clear();
            equityList.addAll(equities);
            quotesAdapter.notifyDataSetChanged();
        });


        newsList.add(new News(0, "", ""));
        newsList.add(new News(0, "", ""));

        //Подписка на новости
        viewModel.getNews().observe(this, news -> {
            newsList.clear();
            newsList.addAll(news);
            newsAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initStoryRecyclerView();
        initQuotesRecyclerView();
        initNewsRecyclerView();
    }

    private void initStoryRecyclerView(){
        RecyclerView recyclerView = getView().findViewById(R.id.story_recycler_view);
        storyAdapter = new StoryAdapter(getContext(), storyList);
        recyclerView.setAdapter(storyAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
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
        RecyclerView recyclerView = getView().findViewById(R.id.quotes_rv);
        quotesAdapter = new QuotesAdapter(getContext(), equityList);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.hasFixedSize();
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(quotesAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    private void initNewsRecyclerView(){
        //TODO Новость должна состоять из картинки и заголовка
        RecyclerView recyclerView = getView().findViewById(R.id.news_rv);
        newsAdapter = new NewsAdapter(getContext(), newsList);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.hasFixedSize();
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(newsAdapter);
    }

    private void initDefaultStoriesData(){
        storyList.add(new Story(0, "", ""));
        storyList.add(new Story(0, "", ""));
        storyList.add(new Story(0, "", ""));
        storyList.add(new Story(0, "", ""));
        storyList.add(new Story(0, "", ""));
    }
}
