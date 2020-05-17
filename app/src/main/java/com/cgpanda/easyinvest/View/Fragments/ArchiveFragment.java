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
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.cgpanda.easyinvest.Adapters.BlogAdapter;
import com.cgpanda.easyinvest.Entity.Article;
import com.cgpanda.easyinvest.Entity.Quote;
import com.cgpanda.easyinvest.R;
import com.cgpanda.easyinvest.View.ArchiveActivity;
import com.cgpanda.easyinvest.ViewModel.BlogViewModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class ArchiveFragment extends Fragment {

    private static final String TAG = "ArchiveFragment";
    private BlogAdapter adapter;
    private List<Article> articleList = new ArrayList<>();
    private BlogViewModel viewModel;
    private int articleSort;
    private int pageNumber = 0;
    private ProgressBar progressBar;
    private CardView goToArchiveCV;
    private RelativeLayout quoteLayout;
    private Spinner selectSort;
    private Boolean isFirstLoad = true;
    TextView quoteText, quoteAuthor;

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
        viewModel.getIsLoading().observe(this, aBoolean -> {
            if(aBoolean)
                showProgressBar();
            else{
                hideProgressBar();
            }
        });
        viewModel.getQuote().observe(this, quote -> {
            quoteText.setText(quote.getText());
            quoteAuthor.setText(quote.getAuthor());
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
        selectSort = getView().findViewById(R.id.spinner);
        quoteText = getView().findViewById(R.id.quote_text);
        quoteAuthor = getView().findViewById(R.id.quote_author);

        // Слушаем изменения способа сортировки статей
        selectSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected: " + parent.getItemAtPosition(position).toString());
                // Определение выбранной сортировки:
                // 0 - сотрировка по актуальности;
                // 1 - по дате, начиная с новых;
                // -1 - под дате, начиная со старых
                switch (position){
                    case 0:
                        articleSort = 0;
                        break;
                    case 1:
                        articleSort = 1;
                        break;
                    case 2:
                        articleSort = -1;
                        break;
                }

                // При первом открытии страницы блога мы подписываемся на изменение списка статей,
                // поэтому нужно избежать вызова тех же самых данных отсюда
                if (!isFirstLoad) {
                    pageNumber = -1;
                    loadNewArticles();
                }
                isFirstLoad = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        initBlogRecyclerView();
        onGoToArchive(); // Кнопка перехода к списку всех статей
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
        RecyclerView recyclerView = getView().findViewById(R.id.blog_articles_rv);

        adapter = new BlogAdapter(getContext(), articleList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = 0;
                outRect.bottom = 50;
                if (parent.getChildAdapterPosition(view) == 0)
                    outRect.top = 50;
            }
        });
        setRecyclerViewListeners(recyclerView);
    }

    private void setRecyclerViewListeners(RecyclerView recyclerView){
        adapter.setOnBottomReachedListener(position -> {

            loadNewArticles();
            Log.d(TAG, "onBottomReached: end of list: " + pageNumber);
        });
        recyclerView.setOnFlingListener(new RecyclerView.OnFlingListener() {
            @Override
            public boolean onFling(int velocityX, int velocityY) {
                if (velocityY > 2800){
                    goToArchiveCV.setVisibility(View.GONE);
                    quoteLayout.setVisibility(View.GONE);
                } else if (velocityY < -8000){
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

    private void loadNewArticles(){
        pageNumber++;
        viewModel.getNewArticles(pageNumber, articleSort);
    }

}
