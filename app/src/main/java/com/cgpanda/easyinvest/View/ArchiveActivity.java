package com.cgpanda.easyinvest.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.cgpanda.easyinvest.Adapters.ArchiveAdapter;
import com.cgpanda.easyinvest.Entity.Category;
import com.cgpanda.easyinvest.Entity.Story;
import com.cgpanda.easyinvest.R;
import com.cgpanda.easyinvest.ViewModel.ArchiveViewModel;

import java.util.ArrayList;
import java.util.List;

public class ArchiveActivity extends AppCompatActivity {

    private List<Story> storyList = new ArrayList<>();
    private List<Category> categoryList = new ArrayList<>();
    private ArchiveAdapter adapter;
    private ArchiveViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);
        initRecyclerView();

        viewModel = ViewModelProviders.of(this).get(ArchiveViewModel.class);
        viewModel.init();

        viewModel.getAllCategories().observe(this, categories -> {
            categoryList.clear();
            categoryList.addAll(categories);
            adapter.notifyDataSetChanged();
        });


    }

    public void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.parent_category_rv);
        adapter = new ArchiveAdapter(categoryList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

}
