package com.yuan.superdemo.widgets;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuan.superdemo.common.utils.DensityUtil;
import com.yuan.superdemo.R;

/**
 * Created by Yuan on 2016/11/8.
 * Detail 模仿ios输入框
 */

public class SearchView extends FrameLayout implements View.OnClickListener{

    private Context mContext;

    private RelativeLayout backGroundLayout;
    private EditText mEditText;
    private ImageView mImagShowView;

    // view id
    private int EDIT_ID = 1000;
    private int SHOW_ID = 1001;
    private int BG_ID = 1002;

    // view style
    private int backgroudColor = 0;
    private int drawableLeft = 0;
    private int drawableRight = 0;

    private int VIEW_WIDTH = 0;// 默认是屏幕宽度
    private int VIEW_HEIGHT = 45;//默认高度
    private int DEF_PADDING = 20;
    private float mEditDef_X = 0; // 初始化位置

    private boolean isEdit = false;//是否可编辑状态

    // ValueAnimator
    private ValueAnimator inputAnimator;
    private ValueAnimator resetAnimator;

    private SearchInputListener searchInputListener;

    public SearchView(Context context) {
        super(context);
        init(context,null);
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs){

        this.mContext = context;

        backgroudColor = Color.LTGRAY;
        drawableLeft = R.drawable.icon_my_search;
        drawableRight = R.drawable.icon_service;

        if (attrs != null){
            //..
        }

        WindowManager manager = ((Activity)context).getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        VIEW_WIDTH = outMetrics.widthPixels;
        VIEW_HEIGHT = DensityUtil.dip2px(context,VIEW_HEIGHT);

        setBackgroundColor(backgroudColor);
        setPadding(DEF_PADDING,DEF_PADDING,DEF_PADDING,DEF_PADDING);

        initLayout(context);

    }

    private void initLayout(Context context){
        backGroundLayout = new RelativeLayout(context);
        backGroundLayout.setId(BG_ID);
        backGroundLayout.setBackgroundColor(Color.WHITE);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,VIEW_HEIGHT);
        backGroundLayout.setLayoutParams(params);

        backGroundLayout.addView(getDeleteView(context));
        backGroundLayout.addView(getEditView(context));

        backGroundLayout.setOnClickListener(this);

        addView(backGroundLayout);
    }

    private View getEditView(Context context){

        mEditText = new EditText(context);
        mEditText.setId(EDIT_ID);
        mEditText.setSingleLine();
        mEditText.setBackgroundColor(Color.WHITE);
        mEditText.setGravity(Gravity.CENTER_VERTICAL);
        Drawable drawable= getResources().getDrawable(drawableLeft);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());

        mEditText.setCompoundDrawables(drawable,null,null,null);
        mEditText.setHint("搜索");

        defalutLayoutState();
        setEditAction();
        return mEditText;
    }

    private View getDeleteView(Context context){
        mImagShowView = new ImageView(context);
        mImagShowView.setId(SHOW_ID);
        mImagShowView.setPadding(DEF_PADDING,DEF_PADDING,DEF_PADDING,DEF_PADDING);
        mImagShowView.setImageResource(drawableRight);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.addRule(RelativeLayout.CENTER_VERTICAL);

        mImagShowView.setLayoutParams(params);
        mImagShowView.setVisibility(INVISIBLE);
        mImagShowView.setEnabled(false);

        mImagShowView.setOnClickListener(this);

        return mImagShowView;
    }

    private void setEditAction(){
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0){
                    mImagShowView.setVisibility(VISIBLE);
                    mImagShowView.setEnabled(true);
                }else{
                    mImagShowView.setVisibility(INVISIBLE);
                    mImagShowView.setEnabled(false);
                }

                if (searchInputListener != null){
                    searchInputListener.onSearch(mEditText.getText().toString());
                }
            }
        });


        mEditText.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                } else {
                    resetAnim();
                }
            }
        });

        mEditText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    InputMethodManager inputMethodManager = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(inputMethodManager.isActive()){
                        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    Log.i("yuan","进行搜索");
                    if (searchInputListener != null){
                        searchInputListener.onSearch(mEditText.getText().toString());
                    }
                }
                return false;
            }
        });

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed,left,top,right,bottom);
    }

    //-------------- animation for view-----------------------------------------------------------

    private void inputAnim(){

        if (inputAnimator != null && inputAnimator.isRunning()){
            return ;
        }

        if (mEditDef_X == 0)
            mEditDef_X = mEditText.getX();
        float endX = getPaddingLeft() * 1.0f;
        inputAnimator =  ValueAnimator.ofFloat(mEditDef_X,endX)
                .setDuration(500);
        inputAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                mEditText.setX(mEditDef_X);
                inputLayoutState();
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });

        inputAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mEditText.setX((Float) animation.getAnimatedValue());
            }
        }) ;
        inputAnimator.start();

    }

    public void resetAnim(){
        final float startX = mEditText.getX();
        float endX = mEditDef_X;

        if (resetAnimator != null && resetAnimator.isRunning()){
            return ;
        }

        resetAnimator =  ValueAnimator.ofFloat(startX,endX)
                .setDuration(500);
        resetAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                mEditText.setX(startX);
                resetState();
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });

        resetAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mEditText.setX((Float) animation.getAnimatedValue());
            }
        }) ;
        resetAnimator.start();

    }

    //-------------- statr for view----------------------------------------------------------------

    private void resetState(){
        mEditText.setText("");
        defalutLayoutState();
    }

    private void defalutLayoutState(){
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);

        mEditText.setLayoutParams(params);
        isEdit = false;
        mEditText.setEnabled(isEdit);

        mImagShowView.setVisibility(INVISIBLE);
    }

    private void inputLayoutState(){

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.LEFT_OF,mImagShowView.getId());

        mEditText.setLayoutParams(params);
        isEdit = true;
        mEditText.setEnabled(isEdit);
        mEditText.setFocusable(true);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == BG_ID || v.getId() == EDIT_ID) {
            if (!isEdit)
                inputAnim();
        }else if (v.getId() == SHOW_ID){
            mEditText.setText("");
        }
    }

    //-------------- To listener something when doing input ------------------------------------

    public void setOnSearchInputListener(SearchInputListener searchInputListener) {
        this.searchInputListener = searchInputListener;
    }

    public interface SearchInputListener{
        public void onSearch(CharSequence c);
    }
}
