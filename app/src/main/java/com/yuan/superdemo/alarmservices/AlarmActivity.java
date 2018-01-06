package com.yuan.superdemo.alarmservices;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.util.Log;

import com.yuan.superdemo.R;

/**
 * 测试 AlarmService 闹钟服务，模拟心跳
 */
public class AlarmActivity extends AppCompatActivity {

    private final String TAG = "AlarmActivity";

    private int PING_TIME_STAMP = 30 * 1000;//ping时间戳
    private int PONG_TIME_STAMP = 10 * 1000;//pong时间戳

    public static final String ACTION_HEADER = "SNACK";
    public static final String PING_ALARM = ".PING_ALARM";   //  ping服务器闹钟BroadcastReceiver的Action
    public static final String PONG_TIMEOUT_ALARM = ".PONG_TIMEOUT_ALARM";    //  判断连接超时的闹钟BroadcastReceiver的Action

    // intent
    private PendingIntent mPongTimeoutAlarmPendIntent;
    private PendingIntent mPingAlarmPendIntent;
    private Intent mPingAlarmIntent = new Intent(ACTION_HEADER+PING_ALARM);
    private Intent mPongTimeoutAlarmIntent = new Intent(ACTION_HEADER+PONG_TIMEOUT_ALARM);

    // BroadcastReceiver
    private BroadcastReceiver mPongTimeoutAlarmReceiver = new PongTimeoutAlarmReceiver();
    private BroadcastReceiver mPingAlarmReceiver = new PingAlarmReceiver();

    // handler作为模拟请求
    private int HANDLER_REQUEST_CODE = 1000;
    private int HANDLER_RESPONSE_CODE = 1001;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == HANDLER_REQUEST_CODE){

                handler.sendEmptyMessageDelayed(HANDLER_RESPONSE_CODE,1500);

            }else if (msg.what == HANDLER_RESPONSE_CODE){

                Log.i(TAG,"请求成功，客户端心跳活跃状态，准备取消超时闹钟...");
                cacelPongService();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        initPendIntent();
        registerService();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mPingAlarmReceiver);
        unregisterReceiver(mPongTimeoutAlarmReceiver);
    }

    private void initPendIntent(){
        // 闹钟服务intent
        mPingAlarmPendIntent = PendingIntent.getBroadcast(
                this, 0, mPingAlarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        mPongTimeoutAlarmPendIntent = PendingIntent.getBroadcast(
                this, 0, mPongTimeoutAlarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        registerReceiver(mPingAlarmReceiver, new IntentFilter(
                ACTION_HEADER + PING_ALARM));//ping服务器广播接收者
        registerReceiver(mPongTimeoutAlarmReceiver, new IntentFilter(
                ACTION_HEADER + PONG_TIMEOUT_ALARM)); // 注册pong超时的广播接收器
    }

    private void registerService(){
        registerPingService();
        registerPongService();
        Log.i(TAG,getTime());
    }

    // 注册两个闹钟服务，pong闹钟会比ping闹钟慢3秒作为检测时间
    private void registerPingService(){
        //开始时间
        AlarmManager mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {// api 19以上后 闹钟服务将不准确
            mAlarmManager.setExact(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+PING_TIME_STAMP,mPingAlarmPendIntent);
        }else {
            mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), PING_TIME_STAMP, mPingAlarmPendIntent);
        }
    }

    private void registerPongService(){
        AlarmManager mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        mAlarmManager.set( AlarmManager.RTC_WAKEUP,System.currentTimeMillis() + PING_TIME_STAMP + PONG_TIME_STAMP,mPongTimeoutAlarmPendIntent);
    }

    private void cacelPingService(){
        AlarmManager mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        mAlarmManager.cancel(mPingAlarmPendIntent);
    }

    private void cacelPongService(){
        AlarmManager mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        mAlarmManager.cancel(mPongTimeoutAlarmPendIntent);
    }

    /**
     * 发送ping请求的广播接收器
     */
    private class PingAlarmReceiver extends BroadcastReceiver {
        public void onReceive(Context ctx, Intent i) {
            Log.i(TAG,"闹钟执行 "+getTime()+" ，收到ping广播，开始发心跳...");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                registerPingService();
            }
                handler.sendEmptyMessage(HANDLER_REQUEST_CODE);
        }
    }

    /**
     * pong超时的广播接收器
     */
    private class PongTimeoutAlarmReceiver extends BroadcastReceiver {
        public void onReceive(Context ctx, Intent i) {
            Log.i(TAG,"闹钟执行 "+getTime()+" ，收到pong广播，客户端已经挂啦，准备释放资源，登出账号...");
            cacelPingService();
            cacelPongService();
        }
    }

    private String getTime(){
        Time time = new Time("GMT+8");
        time.setToNow();
        int year = time.year;
        int month = time.month;
        int day = time.monthDay;
        int minute = time.minute;
        int hour = time.hour;
        int sec = time.second;
        return "当前时间为：" + year +
                "年 " + month +
                "月 " + day +
                "日 " + hour +
                "时 " + minute +
                "分 " + sec +
                "秒";
    }


}
