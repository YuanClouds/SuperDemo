package com.yuan.superdemo.widgets;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.EditText;

/**
 * 类似IOS搜索效果
 * Created by qiuh on 2016/11/8.
 */

public class IOSSearchEditText extends EditText implements View.OnFocusChangeListener {

    private float mOffset;
    private float mHintWidth = -1;

    public IOSSearchEditText(Context context) {
        super(context);
    }

    public IOSSearchEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IOSSearchEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 外部监听输入法变化调用
     */
    public void closeIme() {
        if (hasFocus() && hasNoContent()) clearFocus();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setOnFocusChangeListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (mHintWidth > 0) {
            if (hasFocus) {
                focusAnim();
            } else {
                unfocusAnim();
            }
        }
    }

    private boolean hasNoContent() {
        return false;
    }


    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        boolean flag = super.onKeyPreIme(keyCode, event);
        if (keyCode == KeyEvent.KEYCODE_BACK && hasNoContent()) {
            if (hasFocus()) clearFocus();
        }
        return flag;
    }

    //
    private void unfocusAnim() {
        ValueAnimator animator = ValueAnimator.ofFloat(-mHintWidth, 0);
        animator.setDuration(300);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mOffset = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }

    private void focusAnim() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, -mHintWidth);
        animator.setDuration(300);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mOffset = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        transCanvas(canvas);
        super.onDraw(canvas);
    }

    private void transCanvas(Canvas canvas) {
        if (mHintWidth < 0) {
            CharSequence hint = getHint();
            float textwidth = getPaint().measureText(hint, 0, hint.length());
            float drawablewidth = 0F;
            Drawable[] drawables = getCompoundDrawables();
            Drawable drawableLeft = drawables[0];
            if (drawableLeft != null) {
                drawablewidth = getCompoundDrawablePadding() + drawableLeft.getIntrinsicWidth();
            }
            mHintWidth = (getWidth() - drawablewidth - textwidth) / 2;
        }
        canvas.translate(mHintWidth + mOffset, 0);
    }
}
