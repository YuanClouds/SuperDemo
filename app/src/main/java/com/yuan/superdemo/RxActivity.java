package com.yuan.superdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.yuan.superdemo.server.ServicesApi;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 1.登录接口， 对返回结果 判断 是否合法
 * 2.合法 继续调用 获取用户信息接口， 保存，判断是不是医生/药师
 * 3.如果是医生 药师，继续调用  签名接口
 */

public class RxActivity extends AppCompatActivity {

    private ServicesApi servicesApi;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);

        initRetrofit();

        editText = (EditText) findViewById(R.id.edittext);

        RxTextView.textChanges(editText)
                .map(new Func1<CharSequence, String>() {
                    @Override
                    public String call(CharSequence charSequence) {
                        return charSequence.toString();
                    }
                })
                .flatMap(new Func1<String, Observable<?>>() {
                    @Override
                    public Observable<?> call(String s) {
                        if (s.length() > 0) {
                            return loginRequest();
                        }else{
                            return null;
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object o) {

                    }
                })
        ;


    }

    private void initRetrofit(){
        Retrofit.Builder retrofitBuilder =new Retrofit.Builder();
        retrofitBuilder.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = retrofitBuilder.build();
        servicesApi = retrofit.create(ServicesApi.class);
    }
    
    public void post(){
        Observable
                .concat(loginRequest(),getUserInfoRequest())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());


    }

    public Observable loginRequest(){
       return servicesApi.postTest("login", null)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable getUserInfoRequest(){
        return servicesApi.postTest("getUserInfo", null)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable getSignInfoRequest(){
        return servicesApi.postTest("getSignInfo", null)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
