package com.yuan.superdemo.fragments.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yuan.superdemo.R;
import com.yuan.superdemo.widgets.HorizontalListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuan on 2016/11/19.
 * Detail
 */

public class MainAdapter extends BaseAdapter{

    private Context context;
    private List<String> types = new ArrayList<>();

    // 子适配器数据源
    private List<String> productionData = new ArrayList<>();
    private List<String> serviceData = new ArrayList<>();

    public MainAdapter(Context context, List<String> types) {
        this.context = context;
        this.types = types;
    }

    public void setProductionDate(List<String> productionDate) {
        this.productionData = productionDate;
        notifyDataSetChanged();
    }

    public void setServiceDate(List<String> serviceDate) {
        this.serviceData = serviceDate;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return types.size();
    }

    @Override
    public Object getItem(int position) {
        return types.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.view_main_adapter_layout,null);

            holder = new ViewHolder();
            holder.myListview  = (HorizontalListView) convertView.findViewById(R.id.hori_listview);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        if(convertView != null){
            bindView(convertView,holder,position);
        }

        return convertView;
    }

    private void  bindView(View convertView,ViewHolder holder,int position){
        if (types.size() <=0 ) return ;

        if (types.get(position).equals("production")){
            if (holder.myListview.getAdapter() == null)
            holder.myListview.setAdapter(new HorizontalAdapter(context,productionData));
            return ;
        }

        if (types.get(position).equals("service")){
            if (holder.myListview.getAdapter() == null)
                holder.myListview.setAdapter(new HorizontalAdapter(context,serviceData));
            return ;
        }
    }

    class ViewHolder{
        private View view;
        public HorizontalListView myListview;

        public ViewHolder() {
        }

        public ViewHolder(View view) {
            this.view = view;
        }

        public View getView(int viewId){
            return this.view.findViewById(viewId);
        }
    }
}
