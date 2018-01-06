package com.yuan.superdemo.share;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yuan.superdemo.R;

import java.util.List;

/**
 * Created by Yuan on 2016/11/15.
 * Detail Share for Dialog
 */

public class SocialShareDialog extends Dialog{

    private List<SHARE_MEDIA> platforms;

    private Context mContext;
    private GridView mGridView;
    private SocialViewAdapter mSocialViewAdapter;

    // listener
    private SocialShareItemListener mSocialShareItemListener;

    public SocialShareDialog(Context context, int themeResId, List<SHARE_MEDIA> platforms) {
        super(context, themeResId);
        this.platforms =  platforms;
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_social_share_layout);

        mGridView = (GridView) findViewById(R.id.dialog_share_gridview);
        mSocialViewAdapter = new SocialViewAdapter(this.mContext,platforms);
        mGridView.setAdapter(mSocialViewAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mSocialShareItemListener != null){
                    mSocialShareItemListener.onItemOnclickAction(platforms.get(position));
                }
            }
        });
    }

    public void setOnSocialShareItemListener(SocialShareItemListener mSocialShareItemListener) {
        this.mSocialShareItemListener = mSocialShareItemListener;
    }

    public void show(SocialShareItemListener mSocialShareItemListener) {
        this.mSocialShareItemListener = mSocialShareItemListener;
        this.show();
    }
}
