package com.yuan.superdemo.widgets.indexlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.yuan.superdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by siven on 2017/8/8.
 */

public class IndexAdapter extends BaseAdapter implements
        SectionIndexer {

    private Context context;
    private List<IndexListModel> data = new ArrayList<>();

    private String mSections = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public IndexAdapter(Context context, List<IndexListModel> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public IndexListModel getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        final IndexListModel mContent = data.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.layout_item_index, null);
            viewHolder.tvName = (TextView) view.findViewById(R.id.tv_list_index_name);
            viewHolder.tvLetter = (TextView) view.findViewById(R.id.tv_list_index_title);
            viewHolder.ivImage = (ImageView) view.findViewById(R.id.iv_list_index_img);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

      //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if(isShowLetter(position,getSectionForPosition(position))){
            viewHolder.tvLetter.setVisibility(View.VISIBLE);
            viewHolder.tvLetter.setText(mContent.getSortLetters());
        }else{
            viewHolder.tvLetter.setVisibility(View.GONE);
        }

        viewHolder.tvName.setText(this.data.get(position).getName());

        return view;

    }

    final static class ViewHolder {
        TextView tvLetter;
        TextView tvName;
        ImageView ivImage;
    }

    @Override
    public Object[] getSections() {
        String[] sections = new String[mSections.length()];
        for (int i = 0; i < mSections.length(); i++)
            sections[i] = String.valueOf(mSections.charAt(i));
        return sections;
    }

    @Override
    public int getPositionForSection(int section) {

        // 如果当前部分没有item，则之前的部分将被选择
        for (int i = section; i >= 0; i--) {
            for (int j = 0; j < getCount(); j++) {
                System.out.println(getCount());
                if (i == 0) { // #
                    // For numeric section 数字
                    for (int k = 0; k <= 9; k++) {// 1...9
                        // 字符串第一个字符与1~9之间的数字进行匹配
                        if (StringMatcher.match(
                                String.valueOf(getItem(j).getSortLetters()),
                                String.valueOf(k)))
                            return j;
                    }
                } else { // A~Z
                    if (StringMatcher.match(
                            String.valueOf(getItem(j).getSortLetters()),
                            String.valueOf(mSections.charAt(i))))
                        return j;
                }
            }
        }
        return 0;
    }

    private boolean isShowLetter(int position,int section){
        for (int i = 0; i < getCount(); i++) {
            String sortStr = data.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i == position;
            }
        }

        return false;
    }

    @Override
    public int getSectionForPosition(int position) {
        return data.get(position).getSortLetters().charAt(0);
    }
}
