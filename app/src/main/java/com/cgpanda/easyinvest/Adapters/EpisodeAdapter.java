package com.cgpanda.easyinvest.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.cgpanda.easyinvest.Entity.Episode;
import com.cgpanda.easyinvest.R;

import java.util.Comparator;
import java.util.List;

public class EpisodeAdapter extends PagerAdapter {

    private Context context;
    private List<Episode> episodeList;

    public EpisodeAdapter(Context context, List<Episode> episodeList) {
        this.context = context;
        this.episodeList = episodeList;

    }

    @Override
    public int getCount() {
        return episodeList.size();
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.episode_layout, container, false);

        ImageView imageView = view.findViewById(R.id.episode_image);
        TextView textView = view.findViewById(R.id.episode_text);

        Glide.with(context)
                .load(episodeList.get(position).getEpisode_image())
                .into(imageView);

        textView.setText(episodeList.get(position).getEpisode_text());

        container.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


}
