package com.yuan.superdemo.common.models;

/**
 * Created by siven on 2017/4/24.
 */

public class AdvMessageModel {

    private String title;
    private String content;

    public AdvMessageModel(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public AdvMessageModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
