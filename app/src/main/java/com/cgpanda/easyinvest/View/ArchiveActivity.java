package com.cgpanda.easyinvest.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import com.cgpanda.easyinvest.Adapters.StaggeredStoryAdapter;
import com.cgpanda.easyinvest.Entity.Story;
import com.cgpanda.easyinvest.R;
import com.cgpanda.easyinvest.ViewModel.ArchiveViewModel;
import com.cgpanda.easyinvest.ViewModel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class ArchiveActivity extends AppCompatActivity {

    private List<Story> storyList = new ArrayList<>();

    private StaggeredStoryAdapter adapter;
    private RecyclerView recyclerView;

    private ArchiveViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);
        viewModel = ViewModelProviders.of(this).get(ArchiveViewModel.class);
        viewModel.init();
        LiveData<List<Story>> data = viewModel.getStories();
        data.observe(this, stories -> {
            storyList.clear();
            storyList.addAll(stories);
            adapter.notifyDataSetChanged();
        });

        initRecyclerView();
    }

    private void initRecyclerView(){
        recyclerView = findViewById(R.id.staggered_recycler_view);
        adapter = new StaggeredStoryAdapter(this, storyList);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.right = 40;
                outRect.bottom = 40;
                outRect.left = 40;
                outRect.top = 40;
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
