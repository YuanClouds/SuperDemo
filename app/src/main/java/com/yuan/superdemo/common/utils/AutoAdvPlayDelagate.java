package com.yuan.superdemo.common.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.yuan.superdemo.common.models.AdvMessageModel;
import com.yuan.superdemo.widgets.AutoMessageTextView;
import com.yuan.superdemo.widgets.HorizalAnimTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 委托执行公告播放
 * Created by siven on 2017/4/24.
 */

public class AutoAdvPlayDelagate {

    private final String TAG = "AutoAdvPlayDelagate";

    private Context mContext;
    private AutoMessageTextView advTextView;

    private int curIndex = 0;
    private List<AdvMessageModel> messageModelList = new ArrayList<>();

    private Handler handler;

    public AutoAdvPlayDelagate(Context mContext, AutoMessageTextView advTextView) {
        this.mContext = mContext;
        this.advTextView = advTextView;
        init();
    }

    public static void delegate(Context context, AutoMessageTextView autoAdvTextView) {
        new AutoAdvPlayDelagate(context,autoAdvTextView);
    }

    private void initHandler(){
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                curIndex ++;
                if (curIndex >= messageModelList.size()) curIndex = 0;
                start(curIndex);
            }
        };
    }

    private void init(){

        initHandler();

        // 获取消息列表
        for (int i = 0;i<3;i++){
            String content = i+" -- content content content ";
            for (int j =0;j<i+5;j++){
                content +=" content"+j +" ";
            }
            AdvMessageModel advMessageModel = new AdvMessageModel("[title" +i+"]",content+ " -- " + i);
            messageModelList.add(advMessageModel);
        }

        advTextView.setAnimationCallBack(new HorizalAnimTextView.AnimationCallBack() {
            @Override
            public void onComplete() {
                Log.d(TAG,"移动结束，发送定时三秒切换下一条公告，下一条列表为: " + curIndex);
                test();
            }
        });

        start(0);
    }

    private void test(){
        handler.sendEmptyMessageDelayed(1000,3 * 1000);
    }

    private void start(int index){

        if (index >= messageModelList.size()){
            return;
        }

        curIndex = index;
        advTextView.setMessage(messageModelList.get(curIndex));
    }
}
