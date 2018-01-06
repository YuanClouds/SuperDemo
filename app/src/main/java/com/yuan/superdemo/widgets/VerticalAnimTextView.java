package com.yuan.superdemo.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.yuan.superdemo.R;
import com.yuan.superdemo.databases.VerticalTvConfigModel;

/**
 * 1、支持上下滚动 左右滚动Texview
 * Created by siven on 2017/4/20.
 */

public class VerticalAnimTextView extends TextSwitcher {

    private final String TAG = getClass().getSimpleName();

    private Context mContext;
    private TextView mTextView;
    private VerticalTvConfigModel configModel;

    // value
    private int viewHeight;
    private int textColor = Color.BLACK;
    private float textSize = 12f;
    private String text="";
    private int gravity = Gravity.CENTER;
    private FrameLayout.LayoutParams mLayoutParams
            = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT );

    // enable
    private boolean enableScroll = false;// 是否要支持跑马灯
    public void setEnableScroll(boolean enableScroll) {
        this.enableScroll = enableScroll;
    }

    // callback
    private HorizalAnimTextView.AnimationCallBack animationCallBack;

    public VerticalAnimTextView(Context context, boolean enableScroll) {
        this(context);
        this.enableScroll = enableScroll;
    }

    public VerticalAnimTextView(Context context, VerticalTvConfigModel configModel, boolean enableScroll) {
        this(context,configModel,enableScroll,null);
    }

    public VerticalAnimTextView(Context context, VerticalTvConfigModel configModel, boolean enableScroll, HorizalAnimTextView.AnimationCallBack animationCallBack) {
        super(context);
        this.configModel = configModel;
        this.mContext = context;
        this.enableScroll = enableScroll;
        this.animationCallBack = animationCallBack;
        initView(null);
    }

    public VerticalAnimTextView(Context context) {
        this(context, null);
    }

    public VerticalAnimTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {

        if (attrs != null) {
            // 属性
            TypedArray t = mContext.obtainStyledAttributes(attrs,
                    R.styleable.verticalTextView, 0, 0);
            textSize = t.getDimension(R.styleable.verticalTextView_textSize,12f);
            textColor = t.getColor(R.styleable.verticalTextView_textColor,Color.BLACK);
            text = t.getString(R.styleable.verticalTextView_text);
            gravity = t.getInteger(R.styleable.verticalTextView_tv_gravity,Gravity.CENTER);
            enableScroll = t.getBoolean(R.styleable.verticalTextView_scrollable,false);

        }

        if (configModel != null){
            textSize = configModel.getTextSize();
            textColor = configModel.getTextColor();
            text = configModel.getText();
            gravity = configModel.getGravity();

            if (configModel.getLayoutParams() != null)
                mLayoutParams = (FrameLayout.LayoutParams) configModel.getLayoutParams();
        }

        this.setFactory(new ViewFactory() {
            @Override
            public View makeView() {
                mTextView = new TextView(mContext);
                mTextView.setLayoutParams(mLayoutParams);
                mTextView.setTextColor(textColor);
                mTextView.setSingleLine(true);
                mTextView.setTextSize(textSize);
                mTextView.setGravity(gravity);
                mTextView.setText(text);
                return mTextView;
            }
        });

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewHeight = getHeight();
        setInAnimation(createAnimationByIn());
        setOutAnimation(createAnimationByOut());
    }

    // 位移 + 透明图动画
    private Animation createAnimationByIn(){
        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateAnimation = new TranslateAnimation(0,0,viewHeight,0f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f,1.0f);

        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setDuration(400);
        animationSet.setFillAfter(true);
        return  animationSet;
    }

    public void stopScorllAnimation(){
        HorizalAnimTextView view = (HorizalAnimTextView) getCurrentView();
        view.stopPlay();
    }

    // 位移 + 透明图动画
    private Animation createAnimationByOut(){
        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateAnimation = new TranslateAnimation(0,0,0,-viewHeight);

        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f,0.0f);

        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setDuration(400);
        animationSet.setFillAfter(true);
        return  animationSet;
    }

    //------ 基本属性设置

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public void setText(String text) {
        this.text = text;
        super.setText(text);
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

}

