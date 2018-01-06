package com.yuan.superdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.yuan.superdemo.widgets.indexlist.CharacterParser;
import com.yuan.superdemo.widgets.indexlist.IndexAdapter;
import com.yuan.superdemo.widgets.indexlist.IndexListModel;
import com.yuan.superdemo.widgets.indexlist.IndexableListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

public class IndexListActivity extends AppCompatActivity {

    private IndexableListView listview;
    private IndexAdapter mIndexAdapter;

    private View viewHeadler;

    private ArrayList<String> mItems = new ArrayList<>();
    private List<IndexListModel> data = new ArrayList<>();

    private CharacterParser mCharacterParser = new CharacterParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_list);

        initView();
    }

    private void initView() {

        listview = (IndexableListView) findViewById(R.id.lv_index_list);
        listview.setFastScrollEnabled(true); // 设置快速滑动

        viewHeadler = LayoutInflater.from(this).inflate(R.layout.layout_header_index_list,null);
        listview.addHeaderView(viewHeadler);

        mIndexAdapter = new IndexAdapter(this,data);
        listview.setAdapter(mIndexAdapter);

        mItems.add("阿里巴巴");
        mItems.add("Diary of a Wimpy Kid 6: Cabin Fever");
        mItems.add("Steve Jobs");
        mItems.add("Inheritance (The Inheritance Cycle)");
        mItems.add("11/22/63: A Novel");
        mItems.add("The Hunger Games");
        mItems.add("The LEGO Ideas Book");
        mItems.add("猫窝");
        mItems.add("Explosive Eighteen: A Stephanie Plum Novel");
        mItems.add("Catching Fire (The Second Book of the Hunger Games)");
        mItems.add("Elder Scrolls V: Skyrim: Prima Official Game Guide");
        mItems.add("Death Comes to Pemberley");
        mItems.add("Diary of a Wimpy Kid 6: Cabin Fever");
        mItems.add("Steve Jobs");
        mItems.add("Inheritance (The Inheritance Cycle)");
        mItems.add("11/22/63: A Novel");
        mItems.add("The Hunger Games");
        mItems.add("The LEGO Ideas Book");
        mItems.add("Explosive Eighteen: A Stephanie Plum Novel");
        mItems.add("Catching Fire (The Second Book of the Hunger Games)");
        mItems.add("Elder Scrolls V: Skyrim: Prima Official Game Guide");
        mItems.add("做作");
        mItems.add("m");
        mItems.add("wokao");
        mItems.add("阿源");

        Collections.sort(mItems); // 排序

        for (String name:mItems){
            IndexListModel model = new IndexListModel();
            model.setName(name);

            String c = mCharacterParser.getSelling(name).substring(0,1).toUpperCase();
            model.setSortLetters(c);

            data.add(model);
        }

        mIndexAdapter.notifyDataSetChanged();
    }



}
