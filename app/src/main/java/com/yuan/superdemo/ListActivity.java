package com.yuan.superdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yuan.superdemo.common.utils.ScrollerLayoutUtil;
import com.yuan.superdemo.fragments.adapters.MainAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private final static int HANDLER_VIEW_HEADER = 1000;
    private final static int HANDLER_VIEW_FUCTION = 1001;
    private final static int HANDLER_VIEW_ADV = 1002;
    private final static int HANDLER_VIEW_PRODUCTION = 1003;
    private final static int HANDLER_VIEWSERVICE = 1004;

    private ListView listView;
    private MainAdapter mainAdapter;
    private View titleView;

    // header
    private LinearLayout heanderView;

    private List<String> types = new ArrayList<>();
    // 子适配器数据源
    private List<String> productionData = new ArrayList<>();
    private List<String> serviceData = new ArrayList<>();

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HANDLER_VIEW_HEADER:
                    addHeaderView();
                    break;
                case HANDLER_VIEW_FUCTION:
                    addHeaderFuction();
                    break;
                case HANDLER_VIEW_ADV:
                    addHeaderAdv();
                    break;
                case HANDLER_VIEW_PRODUCTION:
                    addProduction();
                    break;
                case HANDLER_VIEWSERVICE:
                    addService();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listView = (ListView) findViewById(R.id.listview);
        titleView = findViewById(R.id.test_title);
        mainAdapter = new MainAdapter(this,types);
        listView.setAdapter(mainAdapter);

        testAction();
    }

    private void initData(){}

    public void testAction(){
        handler.sendEmptyMessageDelayed(HANDLER_VIEW_HEADER,1000);
    }

    public void addHeaderView(){
        Log.i("yuan","加载广告1！");
        heanderView = new LinearLayout(this);
        heanderView.setOrientation(LinearLayout.VERTICAL);
        heanderView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView headerTextView = new TextView(this);
        headerTextView.setText("I am adv1");
        headerTextView.setBackgroundColor(Color.GRAY);
        headerTextView.setGravity(Gravity.CENTER);
        headerTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,200));

        heanderView.addView(headerTextView);

        handler.sendEmptyMessageDelayed(HANDLER_VIEW_FUCTION,1000);
    }

    public void addHeaderFuction(){
        Log.i("yuan","加载功能！");
        TextView headerTextView = new TextView(this);
        headerTextView.setText("I am Fuchtion");
        headerTextView.setBackgroundColor(Color.GREEN);
        headerTextView.setGravity(Gravity.CENTER);
        headerTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,200));

        heanderView.addView(headerTextView);

        handler.sendEmptyMessageDelayed(HANDLER_VIEW_ADV,200);
    }

    public void addHeaderAdv(){
        Log.i("yuan","加载图片广告2！");
        TextView headerTextView = new TextView(this);
        headerTextView.setText("I am adv2");
        headerTextView.setBackgroundColor(Color.BLUE);
        headerTextView.setGravity(Gravity.CENTER);
        headerTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,200));

        heanderView.addView(headerTextView);
        listView.addHeaderView(heanderView);
        mainAdapter.notifyDataSetChanged();

        handler.sendEmptyMessageDelayed(HANDLER_VIEW_PRODUCTION,200);
    }

    public void addProduction(){
        types.add("production");
        mainAdapter.setProductionDate(productionData);
        handler.sendEmptyMessageDelayed(HANDLER_VIEWSERVICE,200);
    }

    public void addService(){
        types.add("service");
        types.add("service");
        types.add("service");
        types.add("service");
        mainAdapter.setServiceDate(serviceData);

        ScrollerLayoutUtil.delegate(this.listView,titleView);
    }

}
