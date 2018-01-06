package com.yuan.superdemo.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yuan.superdemo.R;
import com.yuan.superdemo.databases.VerticalTvConfigModel;
import com.yuan.superdemo.common.models.AdvMessageModel;

/**
 * 1、支持上下滚动 左右滚动Texview
 * 2、支持 title : content
 *
 * 适用广告广播通知
 * Created by siven on 2017/4/22.
 */

public class AutoMessageTextView extends LinearLayout{

    //View
    private VerticalAnimTextView titleTv;
    private HorizalAnimTextView contentTv;
    private ImageView imageView;

    //attr 基本属性
    private float titleTextSize = 12f;
    private int titleTextColor = Color.BLACK;
    private float contentTextSize = 12f;
    private int contentTextColor = Color.BLACK;
    private int imageSrc = -1;

    private Context mContext;
    private HorizalAnimTextView.AnimationCallBack animationCallBack;
    public void setAnimationCallBack(HorizalAnimTextView.AnimationCallBack animationCallBack) {
        this.animationCallBack = animationCallBack;
        contentTv.setAnimationCallBack(this.animationCallBack);
    }

    public AutoMessageTextView(Context context) {
        this(context,null);
    }

    public AutoMessageTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs){
        this.mContext = context;
        setOrientation(HORIZONTAL);

        LinearLayout mContainer = new LinearLayout(mContext);
        LinearLayout.LayoutParams mContainerLp = new LayoutParams(LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        mContainer.setOrientation(HORIZONTAL);
        mContainer.setGravity(Gravity.CENTER_VERTICAL);
        mContainer.setLayoutParams(mContainerLp);
        addView(mContainer);

        if (attrs != null){
            TypedArray t = mContext.obtainStyledAttributes(attrs,
                    R.styleable.autoMessageTextView, 0, 0);
            titleTextSize = t.getDimension(R.styleable.autoMessageTextView_autoTitleTextSize,12f);
            titleTextColor = t.getColor(R.styleable.autoMessageTextView_autoTitleTextColor,Color.BLACK);

            contentTextSize = t.getDimension(R.styleable.autoMessageTextView_autoContentTextSize,12f);
            contentTextColor = t.getColor(R.styleable.autoMessageTextView_autoContentTextColor,Color.BLACK);
            imageSrc = t.getResourceId(R.styleable.autoMessageTextView_autoImage,-1);
        }

        LinearLayout.LayoutParams imgLp =  new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        imgLp.setMargins(50,0,50,0);
        imageView = new ImageView(mContext);
        imageView.setLayoutParams(imgLp);
        imageView.setBackgroundColor(Color.YELLOW);
        mContainer.addView(imageView);
        if (imageSrc != -1){
            imageView.setImageResource(imageSrc);
        }else{
            imageView.setVisibility(GONE);
        }

        VerticalTvConfigModel configTitle = new VerticalTvConfigModel(titleTextColor, (int) titleTextSize, Gravity.CENTER,"");
        configTitle.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        titleTv = new VerticalAnimTextView(this.mContext,configTitle,false);
        LinearLayout.LayoutParams titleLp = new LayoutParams(LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        titleLp.setMargins(0,0,50,0);
        titleTv.setLayoutParams(titleLp);

        LinearLayout.LayoutParams contentLp = new LayoutParams(LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        contentTv = new HorizalAnimTextView(mContext);
        contentTv.setLayoutParams(contentLp);
        contentTv.setTextSize(contentTextSize);
        contentTv.setTextColor(contentTextColor);

        mContainer.addView(titleTv);
        mContainer.addView(contentTv);

    }

    public void setMessage(AdvMessageModel advMessageModel){
        titleTv.setText(advMessageModel.getTitle());
        contentTv.setContent(advMessageModel.getContent());

        contentTv.startPlay();
    }
}
