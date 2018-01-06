package com.yuan.superdemo;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yuan.superdemo.common.utils.AutoAdvPlayDelagate;
import com.yuan.superdemo.widgets.AutoMessageTextView;
import com.yuan.superdemo.widgets.HorizalAnimTextView;
import com.yuan.superdemo.widgets.VerticalAnimTextView;

import java.util.ArrayList;

public class AutoTextViewActivity extends AppCompatActivity {

    private VerticalAnimTextView autoTextView;
    private HorizalAnimTextView horizalAnimTextView;

    private VerticalAnimTextView vl_tv_two;
    private AutoMessageTextView advTextView;

    private int position = 0;
    private ArrayList<String> titleList;

    private Button button0,button1,button2,button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_text_view);

        // button
        button0 = (Button) findViewById(R.id.button);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);

        autoTextView = (VerticalAnimTextView) findViewById(R.id.auto_text);
        autoTextView.setEnableScroll(false);
        titleList = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            titleList.add(i + "");
        }

        startVerticalTv();

        horizalAnimTextView = (HorizalAnimTextView) findViewById(R.id.horizal_tv);
        vl_tv_two = (VerticalAnimTextView) findViewById(R.id.vl_tv_two);
        advTextView = (AutoMessageTextView) findViewById(R.id.autoadv_tv);

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVerticalTv();
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                horizalAnimTextView.startPlay();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                advTextView.test("我是公告栏");
                AutoAdvPlayDelagate.delegate(AutoTextViewActivity.this,advTextView);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                vl_tv_two.startScorllAnimation();
            }
        });

    }

    public void startVerticalTv(){
        if (position >= titleList.size()) position = 0;
        autoTextView.setText(titleList.get(position));

        position++;
    }

    @Override
    protected void onResume() {
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();
    }
}
