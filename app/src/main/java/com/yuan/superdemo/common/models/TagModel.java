package com.yuan.superdemo.common.models;

import android.view.View;

/**
 * 首页事件绑定
 * Created by siven on 2018/1/6.
 */

public class TagModel {

    public String time;
    public String tag;
    public String describe;
    public View.OnClickListener listener;

    public TagModel(String time, String tag, String describe,View.OnClickListener listener) {
        this.time = time;
        this.tag = tag;
        this.describe = describe;
        this.listener = listener;
    }
}
