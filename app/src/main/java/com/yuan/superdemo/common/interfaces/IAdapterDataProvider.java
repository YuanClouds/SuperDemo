package com.yuan.superdemo.common.interfaces;

import java.util.List;

/**
 * Created by siven on 2018/1/6.
 */

public interface IAdapterDataProvider<T> {

    public boolean checkNUll();
    public void addData(T data);
    public void addAllData(List<T> datas);
    public void setData(List<T> datas);
    //...
}
