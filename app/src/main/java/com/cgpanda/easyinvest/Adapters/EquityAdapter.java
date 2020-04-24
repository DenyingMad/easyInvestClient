package com.cgpanda.easyinvest.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.cgpanda.easyinvest.R;

import java.util.List;

public class EquityAdapter extends PagerAdapter {

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = null;
        view = LayoutInflater.from(container.getContext()).inflate(R.layout.chart_layout, container, false);
        TextView tv = view.findViewById(R.id.debug_text);

        switch (position){
            case 0:
                tv.setText("График");
                break;
            case 1:
                tv.setText("Обзор");
                break;
            case 2:
                tv.setText("Анализ");
                break;
            case 3:
                tv.setText("Новости");
                break;
        }
        container.addView(view, 0);
        return view;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
