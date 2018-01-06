package com.yuan.superdemo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.yuan.superdemo.fragments.Tab1Fragment;
import com.yuan.superdemo.fragments.Tab2Fragment;
import com.yuan.superdemo.fragments.TabFragment;
import com.yuan.superdemo.widgets.TabToolBar;

public class TabToolBarActivity extends AppCompatActivity {

    TabToolBar tabToolBar;
    TabFragment tabFragment;
    ViewPager mViewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_tool_bar);

        tabToolBar = (TabToolBar) findViewById(R.id.toolbar);
        setSupportActionBar(tabToolBar);

        tabLayout = tabToolBar.getmTabLayout();
        initView();

    }

    private void initView(){
        tabFragment = new TabFragment() {
            @Override
            public void addSubViewTab() {
                addTab("下诊库",Tab1Fragment.class);
                addTab("选药店",Tab2Fragment.class);
                addTab("选医药",Tab1Fragment.class);
                addTab("注意事项",Tab1Fragment.class);
            }

            @Override
            public void loadFinishView(ViewPager viewPager, FragmentStatePagerAdapter mAdapter) {
                mViewPager = viewPager;
                tabToolBar.setViewPager(mViewPager);
                tabLayout.setupWithViewPager(mViewPager);
//                tabLayout.setTabsFromPagerAdapter(mAdapter);
            }

        };

        getSupportFragmentManager().beginTransaction().add(R.id.test_contanter,tabFragment).commit();

    }
}
