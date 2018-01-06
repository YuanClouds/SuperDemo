package com.yuan.superdemo.widgets;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuan.superdemo.R;

/**
 * Created by Yuan on 2016/10/19.
 * Detail 支持tab的toolbar
 */

public class TabToolBar extends Toolbar{

    private Context mContext;
    private TabLayout mTabLayout;
    private TabIndicatorView indicatorView;
    private TextView saveTv;
    private ViewPager viewPager;

    private static final int TAB_ID = 1000;
    private static final int TV_ID = 1001;

    public TabToolBar(Context context) {
        super(context);
        init(context);
    }

    public TabToolBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context mContext){
        this.mContext = mContext;
        setNavigationIcon(R.drawable.icon_title_back);
        setBackgroundColor(Color.WHITE);
        initContent();
    }

    private void initContent(){
        RelativeLayout contentLayout = new RelativeLayout(mContext);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        contentLayout.setLayoutParams(params);

        contentLayout.addView(initText("保存"));
        contentLayout.addView(initTab());
        addView(contentLayout);

    }

    private TextView initText(String c){
        saveTv = new TextView(mContext);
        saveTv.setId(TV_ID);
        saveTv.setText(c);
        saveTv.setTextSize(16);
        saveTv.setPadding(40,20,40,20);
        saveTv.setTextColor(Color.BLUE);
        saveTv.setGravity(Gravity.CENTER);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,1);
        saveTv.setLayoutParams(params);
        return saveTv;
    }

    private FrameLayout initTab(){
        FrameLayout fLayout = new FrameLayout(mContext);
        FrameLayout.LayoutParams fLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

        indicatorView = new TabIndicatorView(mContext);
        indicatorView.setLayoutParams(fLayoutParams);

        mTabLayout = new TabLayout(mContext);
        mTabLayout.setId(TAB_ID);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setTabTextColors(Color.BLACK,Color.WHITE);
//        mTabLayout.addTab(mTabLayout.newTab().setCustomView(R.layout.view_tabtool_nav));
//        mTabLayout.addTab(mTabLayout.newTab().setCustomView(R.layout.view_tabtool_nav));
//        mTabLayout.addTab(mTabLayout.newTab().setCustomView(R.layout.view_tabtool_nav));
//        mTabLayout.addTab(mTabLayout.newTab().setCustomView(R.layout.view_tabtool_nav));
        mTabLayout.setLayoutParams(fLayoutParams);

        fLayout.addView(indicatorView);
        fLayout.addView(mTabLayout);


        RelativeLayout.LayoutParams rParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rParams.addRule(RelativeLayout.LEFT_OF,TV_ID);
        fLayout.setLayoutParams(rParams);
        return fLayout;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        indicatorView.setViewPager(viewPager);
        indicatorView.setTabLayout(mTabLayout);
    }

    public TabLayout getmTabLayout() {
        return mTabLayout;
    }
}
