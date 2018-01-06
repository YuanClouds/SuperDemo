package com.yuan.superdemo.routers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yuan.superdemo.R;

import yuan.codelib.CoreTest;

public class RouterActivity extends AppCompatActivity {

    private final String TAG = "RouterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_router);

        CoreTest.test();
    }
}
