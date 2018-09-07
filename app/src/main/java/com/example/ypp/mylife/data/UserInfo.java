package com.example.ypp.mylife.data;

import cn.bmob.v3.BmobObject;

/**
 * Created by Crystal on 2018/9/2
 */
public class UserInfo extends BmobObject {
    private String userId;
    private String userName;
    private String userPhone;
    private String channel;
    private String userSex;
    private int age;
    private String pwd;

    public UserInfo(String userId,String userName,String userPhone,String channel,String userSex,int age,String pwd){
        this.userId=userId;
        this.userName=userName;
        this.userPhone=userPhone;
        this.channel=channel;
        this.userSex=userSex;
        this.age=age;
        this.pwd=pwd;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
