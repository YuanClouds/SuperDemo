package com.yuan.superdemo.common.ui.fragments.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yuan.superdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuan on 2016/11/19.
 * Detail
 */

public class HorizontalAdapter extends BaseAdapter{

    private Context context;
    private List<String> data = new ArrayList<>();

    public HorizontalAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.view_main_adapter_content,null);
    }
}
