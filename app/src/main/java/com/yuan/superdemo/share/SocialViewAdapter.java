package com.yuan.superdemo.share;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yuan.superdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuan on 2016/11/15.
 * Detail 分享 adapter
 */

public class SocialViewAdapter extends BaseAdapter{

    private Context mContext;
    private List<SHARE_MEDIA> data = new ArrayList<>();

    public SocialViewAdapter(Context mContext, List<SHARE_MEDIA> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
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

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.view_socialize_item,null);
            viewHolder = new ViewHolder();

            convertView = bindView(position,viewHolder,convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(convertView != null){
            convertView = bindView(position,viewHolder,convertView);
        }

        return convertView;
    }

    private View bindView(int position,ViewHolder viewHolder,View convertView){

        viewHolder.iconIv = (ImageView) convertView.findViewById(R.id.share_btn_icon);
        viewHolder.nameTv = (TextView) convertView.findViewById(R.id.share_btn_txt);

        if (data.get(position).equals(SHARE_MEDIA.WEIXIN)){

            viewHolder.iconIv.setImageResource(R.drawable.ic_wechat);
            viewHolder.nameTv.setText("微信好友");
            return convertView;
        }

        if (data.get(position).equals(SHARE_MEDIA.WEIXIN_CIRCLE)){
            viewHolder.iconIv.setImageResource(R.drawable.ic_moments);
            viewHolder.nameTv.setText("微信朋友圈");
            return convertView;
        }

        if (data.get(position).equals(SHARE_MEDIA.QQ)){
            viewHolder.iconIv.setImageResource(R.drawable.ic_qq);
            viewHolder.nameTv.setText("QQ好友");
            return convertView;
        }

        if (data.get(position).equals(SHARE_MEDIA.SMS)){
            viewHolder.iconIv.setImageResource(R.drawable.ic_qq);
            viewHolder.nameTv.setText("短信");
            return convertView;
        }

        return convertView;
    }

    class ViewHolder{
        private ImageView iconIv;
        private TextView nameTv;
    }
}
