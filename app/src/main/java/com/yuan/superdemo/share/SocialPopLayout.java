package com.yuan.superdemo.share;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yuan.superdemo.R;

import java.util.List;

/**
 * Created by Yuan on 2016/11/15.
 * Detail 分享弹框 后期可以完善通用逻辑 for  recyclerview
 */

public class SocialPopLayout extends PopupWindow{

    // 主容器
    private Context context;
    private View mainView;
    private GridView mGridView;

    // listener
    private SocialShareItemListener mSocialShareItemListener;

    // data
    private List<SHARE_MEDIA> platforms;
    private SocialViewAdapter mSocialViewAdapter;

    public SocialPopLayout(Context context, List<SHARE_MEDIA> platforms) {
        super(context);
        this.platforms = platforms;

        create(context);
    }

    private void create(Context context){

        this.context = context;

        final RelativeLayout rootLayout = new RelativeLayout(context);
        rootLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        mainView = LayoutInflater.from(context).inflate(R.layout.pop_social_share_layout,rootLayout);
        mGridView = (GridView) mainView.findViewById(R.id.pop_share_gridview);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);

        this.setContentView(rootLayout);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.umeng_socialize_shareboard_animation);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);
        rootLayout.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = rootLayout.getChildAt(rootLayout.getChildCount()-1).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
        bindAction();
    }

    private void bindAction(){

        mSocialViewAdapter = new SocialViewAdapter(context,platforms);
        mGridView.setAdapter(mSocialViewAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mSocialShareItemListener != null){
                    mSocialShareItemListener.onItemOnclickAction(platforms.get(position));
                }
            }
        });

//        mainView.findViewById(R.id.share_wechat_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (platforms.contains(SHARE_MEDIA.WEIXIN) && mListener!=null)
//                    mListener.setitemOnclickAction(SHARE_MEDIA.WEIXIN);
//            }
//        });
//
//        mainView.findViewById(R.id.share_circle_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (platforms.contains(SHARE_MEDIA.WEIXIN_CIRCLE) && mListener!=null)
//                    mListener.setitemOnclickAction(SHARE_MEDIA.WEIXIN_CIRCLE);
//            }
//        });
//
//        mainView.findViewById(R.id.share_qq_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (platforms.contains(SHARE_MEDIA.QQ) && mListener!=null)
//                    mListener.setitemOnclickAction(SHARE_MEDIA.QQ);
//            }
//        });



    }

    public void show(SocialShareItemListener listener){
        this.mSocialShareItemListener = listener;
        this.showAtLocation(((Activity)context).getWindow().getDecorView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //底部显示
    }

}
