package com.yuan.superdemo.common.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuan.superdemo.R;
import com.yuan.superdemo.common.interfaces.IAdapterDataProvider;
import com.yuan.superdemo.common.models.TagModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页适配器
 * Created by siven on 2018/1/6.
 */

public class IndexTagAdapter extends RecyclerView.Adapter<IndexTagAdapter.ViewHolder> implements IAdapterDataProvider<TagModel> {

    List<TagModel> modelList = new ArrayList<>();
    Context mContext;

    public IndexTagAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public IndexTagAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_index_layout,null));
    }

    @Override
    public void onBindViewHolder(IndexTagAdapter.ViewHolder holder, int position) {
        TagModel model = modelList.get(position);
        holder.tvTitle.setText(model.tag);
        holder.tvDescribe.setText(model.describe);
        holder.tvTime.setText(model.time);
        holder.itemView.setOnClickListener(model.listener);
    }

    @Override
    public int getItemCount() {
        return checkNUll()?0:modelList.size();
    }

    @Override
    public boolean checkNUll() {
        return modelList == null;
    }

    @Override
    public void addData(TagModel data) {
        if (!checkNUll()){
            modelList.add(data);
        }
        notifyItemInserted(modelList.size()-1);
    }

    @Override
    public void addAllData(List<TagModel> datas) {
        if (!checkNUll()){
            modelList.addAll(datas);
        }
        notifyDataSetChanged();
    }

    @Override
    public void setData(List<TagModel> datas) {
        if (!checkNUll()){
            modelList.clear();
            modelList.addAll(datas);
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvTitle;
        public TextView tvDescribe;
        public TextView tvTime;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_index_item_title);
            tvDescribe = itemView.findViewById(R.id.tv_index_item_describe);
            tvTime = itemView.findViewById(R.id.tv_index_item_time);
        }
    }
}
