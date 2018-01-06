package com.yuan.superdemo.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;

/**
 * Created by Yuan on 2016/11/15.
 * Detail share by umeny
 */

public class SocialShareKit {

    private static UMShareAPI umShareAPI;

    private Context context;
    private SocialShareAction action;

    public SocialShareKit(Context context, SocialShareAction action) {
        this.context = context;
        this.action = action;
    }

    public static void init(Context context){
        // 测试key
        PlatformConfig.setWeixin("wx3693b037a24aa153","wx3693b037a24aa153");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        umShareAPI = UMShareAPI.get(context);
    }

    // QQ 回调必须在Activity调用这个方法
    public static void onActivityResult(int requestCode, int resultCode, Intent data){
        if (umShareAPI != null)
        umShareAPI.onActivityResult(requestCode, resultCode, data);
    }

    // 单一分享
    public void share(UMShareListener listener,SHARE_MEDIA platform){
        this.action.setPlatform(platform)
                .setCallback(listener)
                .share();
    }

    // 多个分享，官方面板
    public void shareDefault(SocialShareListener listener,SHARE_MEDIA ...platforms){
        this.action
                .setDisplayList(platforms)
                .setCallback(listener);

        this.action.open();
    }

    // 多个分享，dialog展示
    public void shareByDialog(SocialShareListener listener,SHARE_MEDIA ...platforms){
        this.action
                .setCallback(listener);

        this.action.openByDialog(context,listener,platforms);
    }

    // 多个分享，pop展示
    public void shareByPop(SocialShareListener listener,SHARE_MEDIA ...platforms){
        this.action
                .setCallback(listener);

        this.action.openByPop(context,listener,platforms);
    }

    public static class Builder{

        private SocialShareAction action;
        private Context context;

        // text for share
        private String shareTitle;
        private String content;
        private String description;// 一般配合link的描述

        // image for share
        private UMImage umImage;
        private String shareImageURL;
        private int shareImageRes;

        // media url for share, default : http://app.mi.com/details?id=com.hori.smartcommunity&ref=search
        private String shareLink = "http://app.mi.com/details?id=com.hori.smartcommunity&ref=search";

        private UMVideo umVideo;
        private String shareVideoURL;

        public Builder(Context context) {
            this.context = context;
        }


        public Builder setShareImage(String shareImageURL) {
            this.shareImageURL = shareImageURL;
            umImage = new UMImage(this.context,shareImageURL);
            return this;
        }

        public Builder setShareImage(int shareImageRes) {
            this.shareImageRes = shareImageRes;
            umImage = new UMImage(this.context,shareImageRes);
            return this;
        }

        public Builder setShareLink(String shareLink) {
            this.shareLink = shareLink;
            return this;
        }

        public Builder setShareTitle(String shareTitle) {
            this.shareTitle = shareTitle;
            return this;
        }

        public Builder setShareVideoURL(String shareVideoURL) {
            this.shareVideoURL = shareVideoURL;
            umVideo = new UMVideo(shareVideoURL);
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public SocialShareKit build(){

            action =   new SocialShareAction((Activity) context);

            if (shareTitle != null){
                action.withTitle(shareTitle);
            }

            if (content != null){
                action.withText(content);
            }

            if (shareLink != null){
                action.withTargetUrl(shareLink);
            }

            // 只能存在一项
            if (umImage != null){
                if (description != null){
                    umImage.setDescription(description);
                }
                action.withMedia(umImage);
            }
            else if (umVideo != null){
                if (description != null){
                    umVideo.setDescription(description);
                }
                action.withMedia(umVideo);
            }

            return new SocialShareKit(context,action);
        }
    }

}
