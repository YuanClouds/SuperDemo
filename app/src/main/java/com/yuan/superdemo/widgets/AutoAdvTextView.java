package com.yuan.superdemo.widgets;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.yuan.superdemo.databases.VerticalTvConfigModel;
import com.yuan.superdemo.common.models.AdvMessageModel;

/**
 * 1、支持上下滚动 左右滚动Texview
 * 2、支持 title : content
 *
 * 适用广告广播通知
 * Created by siven on 2017/4/22.
 */

public class AutoAdvTextView extends LinearLayout{

    private Context mContext;

    //View
    private VerticalAnimTextView titleTv;
    private VerticalAnimTextView contentTV;

    // callback
    private MessagePlayListener messagePlayListener;
    public void setMessagePlayListener(MessagePlayListener messagePlayListener) {
        this.messagePlayListener = messagePlayListener;
    }

    public AutoAdvTextView(Context context) {
        this(context,null);
    }

    public AutoAdvTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs){
        this.mContext = context;
        setOrientation(HORIZONTAL);

        LinearLayout mContainer = new LinearLayout(mContext);
        LinearLayout.LayoutParams mContainerLp = new LayoutParams(LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        mContainer.setOrientation(HORIZONTAL);
        mContainer.setBackgroundColor(Color.YELLOW);
        mContainer.setLayoutParams(mContainerLp);
        addView(mContainer);

        if (attrs != null){

        }

        VerticalTvConfigModel configTitle = new VerticalTvConfigModel(Color.RED,18, Gravity.CENTER,"");
        configTitle.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        titleTv = new VerticalAnimTextView(this.mContext,configTitle,false);
        LinearLayout.LayoutParams titleLp = new LayoutParams(LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        titleTv.setLayoutParams(titleLp);

        LinearLayout.LayoutParams contentLp = new LayoutParams(LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        VerticalTvConfigModel configContent = new VerticalTvConfigModel(Color.RED,18, Gravity.LEFT,"");
        contentTV = new VerticalAnimTextView(this.mContext, configContent, true, new HorizalAnimTextView.AnimationCallBack() {
            @Override
            public void onComplete() {
                if (messagePlayListener != null){
                    messagePlayListener.onPlayComplete();
                }
            }
        });
        contentTV.setLayoutParams(contentLp);

        mContainer.addView(titleTv);
        mContainer.addView(contentTV);

    }

    public void start(){

    }

    public void stop(){
        contentTV.stopScorllAnimation();
    }

    public void setTitleText(String title){
        this.titleTv.setText(title);
    }

    public void setMessage(AdvMessageModel advMessageModel){

        titleTv.setText(advMessageModel.getTitle());
        contentTV.setText(advMessageModel.getContent());
    }

    // callback
    public interface MessagePlayListener{
        public void onPlayComplete();
    }

}
