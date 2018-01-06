package com.yuan.superdemo.common.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuan.superdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuan on 2016/10/19.
 * Detail
 */


public abstract class TabFragment extends Fragment{

    private ViewPager viewPager;
    private List<ViewPageInfo> fragList;
    protected FragmentStatePagerAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.tab_frag_layout, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);

        if (mAdapter == null){
            fragList = new ArrayList<>();
            addSubViewTab();
            mAdapter = new FragmentStatePagerAdapter(getFragmentManager()) {
                @Override
                public Fragment getItem(int position) {
                    return fragList.get(position).fragment;
                }

                @Override
                public int getCount() {
                    return fragList.size();
                }

                @Override
                public CharSequence getPageTitle(int position) {
                    return fragList.get(position).tag;
                }


            };
            if (viewPager !=null) {
                viewPager.setAdapter(mAdapter);
                loadFinishView(viewPager, mAdapter);
            }

        }else{
            if (viewPager !=null) {
                viewPager.setAdapter(mAdapter);
                loadFinishView(viewPager, mAdapter);
            }
        }
    }

    public FragmentStatePagerAdapter getmAdapter() {
        return mAdapter;
    }

    public abstract void addSubViewTab();

    public abstract void loadFinishView(ViewPager viewPager, FragmentStatePagerAdapter mAdapter);

    public void addTab(String tag, Class<? extends Fragment> fragment){
        fragList.add(new ViewPageInfo(tag, Fragment.instantiate(getActivity(), fragment.getName())));
    }

    /**
     * ViewPageInformation
     */
    public static class ViewPageInfo {
        public String tag;
        public View view;
        public Fragment fragment;

        public ViewPageInfo(String tag, Fragment fragment){
            this.tag = tag;
            this.fragment = fragment;
        }
    }

}
