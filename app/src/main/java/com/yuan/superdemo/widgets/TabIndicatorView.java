package com.yuan.superdemo.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

/**
 * Created by Yuan on 2016/10/19.
 * Detail 指示器 for TabLayout
 */

public class TabIndicatorView extends View implements TabLayout.OnTabSelectedListener,ViewPager.OnPageChangeListener {

    private final String TAG = "TabIndicatorView";

    private Context mContext;

    //View
    private TabLayout tabLayout;
    private ViewPager viewPager;

    //Tool
    private Paint paint;
    private Path path;
    private int VIEW_WIDTH = 1000;
    private int VIEW_HEIGHT = 800;
    private int CIRCLE_RADIUS = 0;

    //point 测试 举行
    private int left = 0;
    private int right = 0;
    private int top = 0;
    private int bottom = 0;
    private int center = 0;

    public TabIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public TabIndicatorView(Context context) {
        super(context);
        init(context,null);
    }

    public void setTabLayout(TabLayout tabLayout) {
        this.tabLayout = tabLayout;
        disPlayTabLayout();
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        this.viewPager.setOnPageChangeListener(this);
    }

    private void init(Context context, AttributeSet attrs){
        this.mContext = context;

        if (attrs != null){
            // attrs...
        }

        path = new Path();

        paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(1);
        paint.setAntiAlias(true);

        disPlayTabLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawRect(left,0,right,VIEW_HEIGHT,paint);
        path.reset();
        int l = right - left;

        canvas.drawCircle(left + l/4,center,CIRCLE_RADIUS,paint);
        canvas.drawCircle(right - l/4,center,CIRCLE_RADIUS,paint);

        path.moveTo(left + l/4,center +CIRCLE_RADIUS);
        path.lineTo(right - l/4,center +CIRCLE_RADIUS);
        path.lineTo(right - l/4,center-CIRCLE_RADIUS);
        path.lineTo(left + l/4,center-CIRCLE_RADIUS);
        path.close();

        canvas.drawPath(path,paint);

        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getRealWidthMeasureSpec(widthMeasureSpec),getRealHeightMeasureSpec(heightMeasureSpec));
    }

    /**
     * for measure width
     * @param widthMeasureSpec
     * @return
     */
    private int  getRealWidthMeasureSpec(int widthMeasureSpec){
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        if (mode == MeasureSpec.AT_MOST){
//            VIEW_WIDTH = widthSize;
        }else if (mode == MeasureSpec.EXACTLY){
            VIEW_WIDTH = widthSize;
        }else if (mode == MeasureSpec.UNSPECIFIED){
        }
        return (int) VIEW_WIDTH;
    }

    /**
     * for measure height
     * @param heightMeasureSpec
     * @return
     */
    private int  getRealHeightMeasureSpec(int heightMeasureSpec){

        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (mode == MeasureSpec.AT_MOST){
//            VIEW_HEIGHT = heightSize;
        }else if (mode == MeasureSpec.EXACTLY){
            VIEW_HEIGHT = heightSize;
        }else if (mode == MeasureSpec.UNSPECIFIED){
        }
        return (int) VIEW_HEIGHT;
    }

    private void disPlayTabLayout(){
        if (tabLayout != null){

            tabLayout.setSelectedTabIndicatorColor(Color.TRANSPARENT);
            tabLayout.setOnTabSelectedListener(this);

            tabLayout.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
                    if (tabLayout.getScrollX() != getScrollX())
                        scrollTo(tabLayout.getScrollX(), tabLayout.getScrollY());
                }
            });

            ViewCompat.setElevation(this, ViewCompat.getElevation(tabLayout));
            tabLayout.post(new Runnable() {
                @Override
                public void run() {
                    if (tabLayout.getTabCount() > 0)
                        onTabSelected(tabLayout.getTabAt(0));

                }
            });

            for(int tab = 0; tab < tabLayout.getTabCount() ; tab++){
                View tabView = getTabViewByPosition(tab);
                tabView.setBackgroundResource(0);
            }
        }
    }

    private View getTabViewByPosition(int position){
        if(tabLayout != null && tabLayout.getTabCount() > 0) {
            ViewGroup tabStrip = (ViewGroup) tabLayout.getChildAt(0);
            return tabStrip != null ? tabStrip.getChildAt(position) : null;
        }

        return null;
    }

    private void invalidatePosition(int position, float positionOffset){
         View curTabView = getTabViewByPosition(position);

        left = right = 0;

        Log.i(TAG,"tabview pading " + curTabView.getPaddingLeft() + " "+curTabView.getPaddingRight()  + " "+ curTabView.getPaddingTop() + " "+curTabView.getPaddingBottom());

        if (positionOffset >0 && position < tabLayout.getTabCount() - 1){
            View nextTabView = getTabViewByPosition(position + 1);
            left += (int) ( nextTabView.getLeft() * positionOffset + curTabView.getLeft() * (1.f - positionOffset) );
            right += (int) ( nextTabView.getRight() * positionOffset + curTabView.getRight() * (1.f - positionOffset) );
        }else {
            left = curTabView.getLeft();
            right = curTabView.getRight();
        }
        left -= curTabView.getPaddingLeft()/2;
        right += curTabView.getPaddingRight()/2;
        center = VIEW_HEIGHT/2;
        CIRCLE_RADIUS = VIEW_HEIGHT/4;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
         if (tab.getPosition() != viewPager.getCurrentItem()){
             viewPager.setCurrentItem(tab.getPosition());
         }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.i(TAG,"position"+position + " positionOffset"+positionOffset);
        invalidatePosition(position,positionOffset);
        invalidate();
    }

    @Override
    public void onPageSelected(int position) {
        if(tabLayout.getSelectedTabPosition() != position)
            tabLayout.getTabAt(position).select();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
