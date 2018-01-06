package com.yuan.superdemo.databases.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Yuan on 2016/12/9.
 * Detail 创建用户表
 * 测试 2.0版本 增加字段 sex
 */

@Entity
public class User {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "account")
    @Index(unique = true) // 账号唯一
    private String account;

    @Property(nameInDb = "password")
    private String password;

    @Property(nameInDb = "nickname")
    private String nickname;

    @Property(nameInDb = "sex")
    private String sex;

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


    @Generated(hash = 816541772)
    public User(Long id, String account, String password, String nickname,
            String sex) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.nickname = nickname;
        this.sex = sex;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public User(String account, String password, String nickname) {
        this.account = account;
        this.password = password;
        this.nickname = nickname;
    }
}
