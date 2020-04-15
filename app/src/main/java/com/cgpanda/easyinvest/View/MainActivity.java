package com.cgpanda.easyinvest.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import com.cgpanda.easyinvest.Adapters.QuotesAdapter;
import com.cgpanda.easyinvest.Adapters.StoryAdapter;
import com.cgpanda.easyinvest.Entity.Equity;
import com.cgpanda.easyinvest.Entity.Story;
import com.cgpanda.easyinvest.R;
import com.cgpanda.easyinvest.ViewModel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel viewModel;

    private StoryAdapter storyAdapter;
    private QuotesAdapter quotesAdapter;
    private List<Story> storyList = new ArrayList<>();
    private List<Equity> equityList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO сделать пустые истории в recycler view пока идет загрузка с сервера
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



        equityList.add(new Equity(1, "Amazon Inc", "Amzn"));
        equityList.add(new Equity(2, "Инд. S&P 500", "S&P 500"));
        equityList.add(new Equity(6, "Samsung Electronics Co Ltd", "smsn"));
        equityList.add(new Equity(3, "Netflix Inc", "NFLX"));
        equityList.add(new Equity(4, "Инд. МосБиржи", "Imoex"));
        equityList.add(new Equity(5, "Яндекс", "Yndx"));

        initQuotesRecyclerView();

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
                outRect.right = 60;
            }
        });
    }

    private void initQuotesRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.quotes_rv);
        quotesAdapter = new QuotesAdapter(this, equityList);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(quotesAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }


}
