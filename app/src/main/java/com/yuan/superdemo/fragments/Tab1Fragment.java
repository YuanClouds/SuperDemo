package com.yuan.superdemo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuan.superdemo.R;
import com.yuan.superdemo.widgets.SearchView;

public class Tab1Fragment extends Fragment {

    private SearchView searchView;
    private TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab1, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView = (TextView) view.findViewById(R.id.show_tv);
        searchView = (SearchView) view.findViewById(R.id.search_view);
        searchView.setOnSearchInputListener(new SearchView.SearchInputListener() {
            @Override
            public void onSearch(CharSequence c) {
                textView.setText(c);
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.resetAnim();
            }
        });
    }

}
