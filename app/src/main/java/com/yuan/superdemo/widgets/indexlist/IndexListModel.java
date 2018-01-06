package com.yuan.superdemo.widgets.indexlist;

/**
 * 测试model
 * Created by siven on 2017/8/8.
 */

public class IndexListModel {

    private String imgUrl;
    private String name;

    private String sortLetters; //显示数据拼音的首字母

    public IndexListModel() {
    }

    public IndexListModel(String imgUrl, String name, String sortLetters) {
        this.imgUrl = imgUrl;
        this.name = name;
        this.sortLetters = sortLetters;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
