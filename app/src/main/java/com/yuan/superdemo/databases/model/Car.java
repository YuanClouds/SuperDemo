package com.yuan.superdemo.databases.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by Yuan on 2016/12/9.
 * Detail 车表 3.0数据库新增
 */

@Entity
public class Car {

    @Id(autoincrement = true)
    private long id;

    @Property(nameInDb = "name")
    private String name;

    @Property(nameInDb = "engine")
    private String engine;

    public String getEngine() {
        return this.engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Generated(hash = 318995002)
    public Car(long id, String name, String engine) {
        this.id = id;
        this.name = name;
        this.engine = engine;
    }

    @Generated(hash = 625572433)
    public Car() {
    }

}
