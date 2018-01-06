package com.yuan.superdemo.widgets;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.nfc.Tag;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import static android.R.attr.type;

/**
 * 支持速度调整的跑马灯
 * Created by siven on 2017/4/21.
 */

public class HorizalAnimTextView extends TextView{

    private final String TAG = getClass().getSimpleName();

    private Context mContext;

    // info internal
    // 这是一条为了测试速度存在的字符串
    private String speedText =
            "一二三四五六" +
                    "一二三四五六" +
                    "一二三四五六" +
                    "一二三四五六" +
                    "一二三四五六" +
                    "一二三四五六" +
                    "一二三四五六" +
                    "一二三四五六" +
                    "一二三四五六" +
                    "一二三四五六";

    private float textWidth; // 文字长度
    private float textSpeed; // 文字滚动速度 V = 字长/10S
    private String curText;

    private float scrollEndX;
    private float scrollStartX;
    private float initX; // 初始化x坐标

    private boolean drawed = false;

    // animation
    private ValueAnimator mValueAnimator;
    private int duration = 10 * 1000;// 10秒播完

    // callback
    private AnimationCallBack animationCallBack;
    public void setAnimationCallBack(AnimationCallBack animationCallBack) {
        this.animationCallBack = animationCallBack;
    }

    public HorizalAnimTextView(Context context) {
        this(context,null);
    }

    public HorizalAnimTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    private void init(){
        this.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        this.setSingleLine(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!drawed) {
            drawed = true;
            measureTextLength();
            measureScrollValue();
        }
    }

    /**
     * 设置当前内容
     * @param content
     */
    public void setContent(String content){
        curText = content;
        setVisibility(INVISIBLE);
        updateScrollValue();
    }

    /**
     * 根据内容，重新初始化滚动距离
     */
    private void updateScrollValue(){
        measureTextLength();
        scrollEndX = textWidth + (-scrollStartX);
        Log.d(TAG,"scrollEndX" + scrollEndX + " textWidth " +textWidth +" "+ "scrollStartX " + (scrollStartX) + " initX " +initX);
    }

    /**
     * 测量出文字总长度
     */
    private void measureTextLength(){
        textWidth = getCharacterWidth(curText);
        int testWidth = getCharacterWidth(speedText);
        textSpeed =  (testWidth * 1.0f) / duration;
//        Log.d(TAG,"测量文字长度为: " + textWidth + " 测量移动速度为: " + textSpeed);
    }

    /**
     * 计算移动起点、终点
     */
    private void measureScrollValue(){
        initX = getX();
        scrollStartX = -(initX + getMeasuredWidth());
        scrollTo((int) scrollStartX, 0);
//        scrollEndX = textWidth + (-scrollStartX) - initX  + 100;  // 减去当前控件相对位置x  100 为偏移量，可固定;
        scrollEndX = textWidth + (-scrollStartX);
//        Log.d(TAG,"scrollEndX" + scrollEndX + " textWidth " +textWidth +" "+ "scrollStartX " + (scrollStartX) + " initX " +initX);
    }

    /**
     * 根据文字，策略长度
     * @param text
     * @return
     */
    private int getCharacterWidth(String text) {
        if (null == text || "".equals(text)){
            return 0;
        }
        Paint paint = new Paint();
        paint.setTextSize( getTextSize() );
        int text_width = (int) paint.measureText(text);// 得到总体长度
        return text_width;
    }

    // ---- 动画逻辑
    /**
     * 开始播放公告
     */
    public void startPlay(){
        if (mValueAnimator!= null){
            stopPlay();// 运行中先取消
        }

        justPlaying(0, (int) scrollEndX);
    }

    /**
     * 停止播放公告
     */
    public void stopPlay(){
        if (mValueAnimator!= null){
            // 运行中先取消
            if (mValueAnimator.isRunning()){
                mValueAnimator.cancel();
            }
        }
    }

    private void justPlaying(int startx,int endx){

        long durationNow = (long) (textWidth / textSpeed);
//        Log.d(TAG,"开启移动动画 " + startx + " -> " +endx + " 动画时间为: " +durationNow);

        mValueAnimator = ValueAnimator.ofInt(startx,endx);
        mValueAnimator.setInterpolator(new LinearInterpolator()); // 匀速插值器
        mValueAnimator.setDuration(durationNow * 2).start();

        mValueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                setText(curText);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (animationCallBack != null){
                    animationCallBack.onComplete();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });

        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                if (animation.getCurrentPlayTime() > 100){ // 延时100毫秒
                    if (getVisibility() == INVISIBLE)
                        setVisibility(VISIBLE);
                }

                int x = (int) animation.getAnimatedValue();
                float move = scrollStartX + x;
//                Log.d(TAG,"移动 -> "+getScrollX() + " x " + x +"");
                scrollTo((int) move,0);
            }
        });

        mValueAnimator.start();
    }

    //---- 轮播完成回调
    public interface AnimationCallBack{
        public void onComplete();
    }
}
