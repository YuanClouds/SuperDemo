package com.yuan.superdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yuan.superdemo.common.adapters.IndexTagAdapter;
import com.yuan.superdemo.common.models.TagModel;
import com.yuan.superdemo.databases.DbActivity;
import com.yuan.superdemo.downloads.DownLoadActivity;
import com.yuan.superdemo.share.SocialShareKit;
import com.yuan.superdemo.share.SocialShareListener;
import com.yuan.superdemo.lambda.LambdaActivity;
import com.yuan.superdemo.routers.RouterActivity;
import com.yuan.superdemo.uploads.UploadActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private IndexTagAdapter mIndexTagAdapter;
    private RecyclerView mRecyclerView;

    private void init(){
        List<TagModel> list = new ArrayList<>();

        list.add(getIndexMode("TabTool", "2018-01-01","自定义tabtool,配合viewpage滑动而滑动标签", v->startActivity(TabToolBarActivity.class)));
        list.add(getIndexMode("上传实现", "2018-01-01", "利用retrofit实现上传",v->startActivity(UploadActivity.class)));
        list.add(getIndexMode("下载实现", "2018-01-01","利用retrofit实现下载", v->startActivity(DownLoadActivity.class)));
        list.add(getIndexMode("第三方分享实现", "2018-01-01","友盟分享二次封装", v->share()));
        list.add(getIndexMode("多样式Listview", "2018-01-01","多样式listview实现", v->startActivity(ListActivity.class)));
        list.add(getIndexMode("数据库(greedao)", "2018-01-01","greedao应用", v->startActivity(DbActivity.class)));
        list.add(getIndexMode("Router实现", "2018-01-01", "自己设计自己的路由，实现组件化",v->startActivity(RouterActivity.class)));
        list.add(getIndexMode("自动滚动广告实现", "2018-01-01","自动滚动广告条，支持左右、上下滚动", v->startActivity(AutoTextViewActivity.class)));
        list.add(getIndexMode("字母索引listview", "2018-01-01", "可以通过字母自动定位到位置的listview",v->startActivity(IndexListActivity.class)));
        list.add(getIndexMode("java8之lambda表达式", "2018-01-01", "java8特性回顾，lambda表达式",v->startActivity(LambdaActivity.class)));

        mIndexTagAdapter.setData(list);
    }

    private TagModel getIndexMode(String tag,String time,String describe,View.OnClickListener listener){
        TagModel tagModel = new TagModel(time,tag,describe,listener);
        return tagModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIndexTagAdapter = new IndexTagAdapter(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mIndexTagAdapter);

        init();
    }

    public void startActivity( Class<?> cls){
        startActivity(new Intent(MainActivity.this,cls));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // QQ回调必须添加这个
        SocialShareKit.onActivityResult(requestCode, resultCode, data);
    }

    public void share(){

        SocialShareKit socialShareKit =
                new SocialShareKit.Builder(MainActivity.this)
                        .setShareTitle("I am Title")
                        .setContent("I am Content")
                        .setShareImage("http://img.25pp.com/uploadfile/app/icon/20160413/1460517230841424.jpg")
                        .build();

        socialShareKit.shareByPop(new SocialShareListener() {
            @Override
            public void onResult(SHARE_MEDIA share_media) {
                Toast.makeText(MainActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                Toast.makeText(MainActivity.this, "分享失败 " + throwable.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {
                Toast.makeText(MainActivity.this, "分享取消", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onReady(SHARE_MEDIA platform) {
                Log.i("yuan",platform.toString() +" !!!!!!!");
            }
        },SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SMS, SHARE_MEDIA.QQ);
    }
}
