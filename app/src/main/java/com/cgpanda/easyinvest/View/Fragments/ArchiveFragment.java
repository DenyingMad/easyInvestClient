package com.cgpanda.easyinvest.View.Fragments;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.cgpanda.easyinvest.Adapters.BlogAdapter;
import com.cgpanda.easyinvest.Entity.Article;
import com.cgpanda.easyinvest.R;
import com.cgpanda.easyinvest.View.ArchiveActivity;
import com.cgpanda.easyinvest.ViewModel.BlogViewModel;

import java.util.ArrayList;
import java.util.List;


public class ArchiveFragment extends Fragment {

    private static final String TAG = "ArchiveFragment";
    private BlogAdapter adapter;
    private List<Article> articleList = new ArrayList<>();
    private BlogViewModel viewModel;
    private int articleSort = 0;
    private int pageNumber;
    private ProgressBar progressBar;
    private CardView goToArchiveCV;
    private RelativeLayout quoteLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(BlogViewModel.class);
        viewModel.init(pageNumber, articleSort);
        viewModel.getCurrentArticles().observe(this, articles -> {
            articleList.clear();
            articleList.addAll(articles);
            adapter.notifyDataSetChanged();
        });
        viewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean)
                    showProgressBar();
                else{
                    hideProgressBar();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_archive, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        progressBar = getView().findViewById(R.id.progressBar);
        goToArchiveCV = getView().findViewById(R.id.go_to_archive_card);
        quoteLayout = getView().findViewById(R.id.quote_layout);
        pageNumber = 0;
        initBlogRecyclerView();
        adapter.setOnBottomReachedListener(position -> {
            Log.d(TAG, "onBottomReached: end of list: " + pageNumber);
            //TODO загрузка данных с сервера
            viewModel.getNewArticles(pageNumber + 1, articleSort);
            pageNumber++;
        });
        onGoToArchive();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //TODO restore instance
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //TODO save instance
    }

    private void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
    }
    private void hideProgressBar(){
        progressBar.setVisibility(View.GONE);
    }

    private void initBlogRecyclerView(){
        adapter = new BlogAdapter(getContext(), articleList);
        RecyclerView recyclerView = getView().findViewById(R.id.blog_articles_rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = 0;
                outRect.bottom = 50;
                if (parent.getChildAdapterPosition(view) == 0){
                    outRect.top = 50;
                }
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setOnFlingListener(new RecyclerView.OnFlingListener() {
            @Override
            public boolean onFling(int velocityX, int velocityY) {
                //Log.d(TAG, "onFling: velocityX: " + velocityX + " velocityY: " + velocityY);
                if (velocityY > 4000){
                    goToArchiveCV.setVisibility(View.GONE);
                    quoteLayout.setVisibility(View.GONE);
                } else if (velocityY < -4000){
                    goToArchiveCV.setVisibility(View.VISIBLE);
                    quoteLayout.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
    }

    private void onGoToArchive(){
        goToArchiveCV.setOnClickListener(v -> {
            Intent i = new Intent(getContext(), ArchiveActivity.class);
            startActivity(i);
        });
    }



}
