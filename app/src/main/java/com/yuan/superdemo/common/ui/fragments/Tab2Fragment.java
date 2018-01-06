package com.yuan.superdemo.common.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yuan.superdemo.R;
import com.yuan.superdemo.common.ui.fragments.adapters.MainAdapter;

import java.util.ArrayList;
import java.util.List;

public class Tab2Fragment extends Fragment {

    private final static int HANDLER_VIEW_HEADER = 1000;
    private final static int HANDLER_VIEW_FUCTION = 1001;
    private final static int HANDLER_VIEW_ADV = 1002;
    private final static int HANDLER_VIEW_PRODUCTION = 1003;
    private final static int HANDLER_VIEWSERVICE = 1004;

    private ListView listView;
    private MainAdapter mainAdapter;

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

    public Tab2Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testAction();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);
        listView = (ListView) view.findViewById(R.id.listview);
        mainAdapter = new MainAdapter(getActivity(),types);
        listView.setAdapter(mainAdapter);
        return view;
    }

    private void initData(){}

    public void testAction(){
        handler.sendEmptyMessageDelayed(HANDLER_VIEW_HEADER,2000);
    }

    public void addHeaderView(){
        Log.i("yuan","加载广告1！");
        heanderView = new LinearLayout(getActivity());
        heanderView.setOrientation(LinearLayout.VERTICAL);
        heanderView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView headerTextView = new TextView(getActivity());
        headerTextView.setText("I am adv1");
        headerTextView.setBackgroundColor(Color.GRAY);
        headerTextView.setGravity(Gravity.CENTER);
        headerTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,200));

        heanderView.addView(headerTextView);

        handler.sendEmptyMessageDelayed(HANDLER_VIEW_FUCTION,2000);
    }

    public void addHeaderFuction(){
        Log.i("yuan","加载功能！");
        TextView headerTextView = new TextView(getActivity());
        headerTextView.setText("I am Fuchtion");
        headerTextView.setBackgroundColor(Color.GREEN);
        headerTextView.setGravity(Gravity.CENTER);
        headerTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,200));

        heanderView.addView(headerTextView);

        handler.sendEmptyMessageDelayed(HANDLER_VIEW_ADV,2000);
    }

    public void addHeaderAdv(){
        Log.i("yuan","加载图片广告2！");
        TextView headerTextView = new TextView(getActivity());
        headerTextView.setText("I am adv2");
        headerTextView.setBackgroundColor(Color.BLUE);
        headerTextView.setGravity(Gravity.CENTER);
        headerTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,200));

        heanderView.addView(headerTextView);
        listView.addHeaderView(heanderView);
        mainAdapter.notifyDataSetChanged();

        handler.sendEmptyMessageDelayed(HANDLER_VIEW_PRODUCTION,2000);
    }

    public void addProduction(){
        types.add("production");
        mainAdapter.setProductionDate(productionData);
        handler.sendEmptyMessageDelayed(HANDLER_VIEWSERVICE,2000);
    }

    public void addService(){
        types.add("service");
        mainAdapter.setServiceDate(serviceData);
    }

}
