package com.cgpanda.easyinvest.View.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cgpanda.easyinvest.Adapters.ArchiveAdapter;
import com.cgpanda.easyinvest.Entity.Category;
import com.cgpanda.easyinvest.R;
import com.cgpanda.easyinvest.View.ArchiveActivity;
import com.cgpanda.easyinvest.ViewModel.ArchiveViewModel;

import java.util.ArrayList;
import java.util.List;


public class ArchiveFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_archive, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        Button showMoreStories = getView().findViewById(R.id.more_stories_btn);
//        showMoreStories.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getContext(), ArchiveActivity.class);
//                startActivity(i);
//            }
//        });
    }


}
