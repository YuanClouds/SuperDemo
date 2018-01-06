package com.yuan.superdemo.databases.db.managers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.yuan.superdemo.databases.db.CarDao;
import com.yuan.superdemo.databases.db.UserDao;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseOpenHelper;

/**
 * Created by Yuan on 2016/12/10.
 * Detail 数据库创建 升级管理
 */

public class MyDatabaseOpenHelper extends DatabaseOpenHelper {

    private final String TAG = "MyDatabaseOpenHelper";

    public MyDatabaseOpenHelper(Context context, String name) {
        super(context, name, ManagerMaster.SCHEMA_VERSION);
    }

    public MyDatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory,  ManagerMaster.SCHEMA_VERSION);
    }

    @Override
    public void onCreate(Database db) {
        super.onCreate(db);
        ManagerMaster.createAllTables(db,false);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        Log.i(TAG,"[-onUpgrade-] "+ oldVersion +" -> " +newVersion);

        if (newVersion == 2){
            // 数据库版本2 增加car表，user表新增sex字段

//            db.beginTransaction();

            // 新增Car表
            CarDao.createTable(db,false);

            db.execSQL("alter table " + UserDao.TABLENAME + " add column sex TEXT NULL");

//            db.endTransaction();

        }

    }
}
