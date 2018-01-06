package com.yuan.superdemo.databases;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.yuan.superdemo.R;
import com.yuan.superdemo.databases.db.DaoSession;
import com.yuan.superdemo.databases.db.UserDao;
import com.yuan.superdemo.databases.db.managers.ManagerMaster;
import com.yuan.superdemo.databases.db.managers.MyDatabaseOpenHelper;
import com.yuan.superdemo.databases.model.User;

public class DbActivity extends AppCompatActivity {

    private final String TAG = "DbActivity";

    // db
    private MyDatabaseOpenHelper mDevOpenHelper;
    private ManagerMaster mDaoMaster;
    private DaoSession mDaoSession;

    private Button createBtn;
    private Button insertBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);

        createBtn = (Button) findViewById(R.id.db_btn_create);
        insertBtn = (Button) findViewById(R.id.db_btn_insert);

        initAction();

    }

    private void initAction(){

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDevOpenHelper = new MyDatabaseOpenHelper(getApplicationContext(), "wusy.db");
                mDaoMaster = new ManagerMaster(mDevOpenHelper.getWritableDb());
                mDaoSession = mDaoMaster.newSession();
            }
        });

        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDao dao =  mDaoSession.getUserDao();
                long num = dao.insert(new User("13751807504","88888","wsy"));

                Log.i(TAG," insertBtn num " + num );
            }
        });



    }
}
