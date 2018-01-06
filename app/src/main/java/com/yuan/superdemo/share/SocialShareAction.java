package com.yuan.superdemo.share;

import android.app.Activity;
import android.content.Context;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yuan.superdemo.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Yuan on 2016/11/15.
 * Detail 这里添加特殊的面板要求
 */

public class SocialShareAction extends ShareAction{

    public SocialShareAction(Activity activity) {
        super(activity);
    }

    // 官方默认面板
    @Override
    public void open() {
        super.open();
    }

    public void openByDialog(Context context, final SocialShareListener listener, SHARE_MEDIA...platforms){
        if (platforms.length >0){
            List<SHARE_MEDIA> platList = Arrays.asList(platforms);

            final SocialShareDialog dialog = new SocialShareDialog(context, R.style.MyDialogStyle,
                    platList);
            dialog.show(new SocialShareItemListener() {
                @Override
                public void onItemOnclickAction(SHARE_MEDIA platform) {
                    listener.onReady(platform);
                    setPlatform(platform)
                            .share();

                    dialog.dismiss();
                }
            });
        }
    }

    public void openByPop(Context context, final SocialShareListener listener,SHARE_MEDIA...platforms){
        if (platforms.length >0){

            List<SHARE_MEDIA> platList = Arrays.asList(platforms);

            final SocialPopLayout popLayout = new SocialPopLayout(context,platList);
            popLayout.show(new SocialShareItemListener() {
                @Override
                public void onItemOnclickAction(SHARE_MEDIA platform) {
                    listener.onReady(platform);
                    setPlatform(platform)
                            .share();

                    popLayout.dismiss();
                }
            });
        }
    }
}
