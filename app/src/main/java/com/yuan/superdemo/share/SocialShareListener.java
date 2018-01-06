package com.yuan.superdemo.share;

import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Created by Yuan on 2016/11/15.
 * Detail 添加还没触发时回调onReady
 */

public interface SocialShareListener extends UMShareListener{

    public void onReady(SHARE_MEDIA platform);

}
