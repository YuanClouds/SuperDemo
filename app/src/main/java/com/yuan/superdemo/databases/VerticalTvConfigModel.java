package com.yuan.superdemo.databases;

import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * 为 VerticalAnimTextView 配置用
 * Created by siven on 2017/4/23.
 */

public class VerticalTvConfigModel {

    private int textColor;
    private int textSize;
    private int gravity;
    private String text;
    private FrameLayout.LayoutParams layoutParams;

    public VerticalTvConfigModel(int textColor, int textSize, int gravity, String text) {
        this.textColor = textColor;
        this.textSize = textSize;
        this.gravity = gravity;
        this.text = text;
    }

    public ViewGroup.LayoutParams getLayoutParams() {
        return layoutParams;
    }

    public void setLayoutParams(FrameLayout.LayoutParams layoutParams) {
        this.layoutParams = layoutParams;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getGravity() {
        return gravity;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
