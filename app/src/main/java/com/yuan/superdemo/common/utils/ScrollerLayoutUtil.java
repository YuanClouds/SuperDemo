package com.yuan.superdemo.common.utils;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.ListView;

import com.yuan.superdemo.R;

/**
 * Created by Yuan on 2016/12/2.
 * Detail For listview by backgroud of titleView
 */

public class ScrollerLayoutUtil {

    // View
    private ListView mListview;
    private View titleView;

    // value
    private int titleHeight = 0;// title高度
    private int beforTop = 0;
    private int dy = 0;// 移动距离dy

    // style
    private final int TITLE_BG_COLOR = R.color.colorAccent;

    // flag
    private boolean canDoing = true;//是否在可计算范围内

    public ScrollerLayoutUtil(ListView mListview, View titleView) {
        this.mListview = mListview;
        this.titleView = titleView;
        init();
    }

    public static void delegate(ListView mListview, View view) {
        new ScrollerLayoutUtil(mListview,view);
    }

    private void init() {

        if (mListview == null || titleView == null) return ;

        measureTitleHight();

        mListview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView listView, int scrollState) {
                // TODO Auto-generated method stub
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:// 是当屏幕停止滚动时
                        if (listView.getFirstVisiblePosition() == 0 && dy == 0) {
                            setTransState();
                        }

                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                if (check(firstVisibleItem)) {

                    ViewGroup headerView = (ViewGroup) view.getChildAt(0);
                    if (headerView == null) return;
                    int fristHeaderH = headerView.getChildAt(0).getHeight();
                    int curTop = view.getChildAt(1).getTop();

                    if (beforTop != 0) {
                        int ddy = beforTop - curTop;
                        dy += ddy;
                    }

                    beforTop = curTop;

                    int alpha = (int) (dy * (255f / (fristHeaderH - titleHeight)));
                    setBackground(alpha);
                }
            }
        });
    }

    private void setBackground(int alpha){

        if (alpha > 255){
            alpha = 255;
            canDoing = false;
        }

        if (alpha <=0){
            setTransState();
        }else{
            setNormalState(alpha);
        }
    }

    private boolean check(int visibleItem){
        if (visibleItem == 0 ) canDoing = true;
        return canDoing;
    }

    private void measureTitleHight(){
        final ViewTreeObserver vto = titleView.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                vto.removeOnPreDrawListener(this);
                titleHeight = titleView.getMeasuredHeight();
                return true;
            }
        });
    }

    private void setNormalState(int alpha){
        titleView.setBackgroundResource(TITLE_BG_COLOR);
        titleView.getBackground().setAlpha(alpha);
    }

    private void setTransState(){
        titleView.setBackgroundResource(Color.TRANSPARENT);
    }

}
