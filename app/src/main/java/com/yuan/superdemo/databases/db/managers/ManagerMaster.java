package com.yuan.superdemo.databases.db.managers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.yuan.superdemo.databases.db.CarDao;
import com.yuan.superdemo.databases.db.DaoMaster;
import com.yuan.superdemo.databases.db.DaoSession;
import com.yuan.superdemo.databases.db.UserDao;

import org.greenrobot.greendao.AbstractDaoMaster;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.identityscope.IdentityScopeType;

/**
 * Created by Yuan on 2016/12/10.
 * Detail 数据库dao管理器
 */

public class ManagerMaster extends AbstractDaoMaster {

    public static final int SCHEMA_VERSION = 2;

    public static void createAllTables(Database db, boolean ifNotExists) {
        UserDao.createTable(db, ifNotExists);
        CarDao.createTable(db,ifNotExists);
    }

    public static void dropAllTables(Database db, boolean ifExists) {
        UserDao.dropTable(db, ifExists);
        CarDao.dropTable(db, ifExists);
    }

    public static DaoSession newDevSession(Context context, String name) {
        Database db = new DaoMaster.DevOpenHelper(context, name).getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        return daoMaster.newSession();
    }

    public ManagerMaster(SQLiteDatabase db) {
        this(new StandardDatabase(db));
    }

    public ManagerMaster(Database db) {
        super(db, SCHEMA_VERSION);
        registerDaoClass(UserDao.class);
        registerDaoClass(CarDao.class);
    }

    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }

    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }


}
