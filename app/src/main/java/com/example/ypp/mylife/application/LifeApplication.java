package com.example.ypp.mylife.application;

import android.app.Application;

import com.example.ypp.mylife.data.Constants;
import com.lzy.okgo.OkGo;

import cn.bmob.v3.Bmob;

/**
 * Created by Crystal on 2018/6/9
 */

public class LifeApplication extends Application {
    public static LifeApplication mLifeApplication;

    @Override
    public void onCreate() {
        super.onCreate();

        mLifeApplication=this;

        Bmob.initialize(getContext(),Constants.APPLICATION_ID);

        OkGo.getInstance().init(this);
    }


    public static LifeApplication getContext() {
        return mLifeApplication;
    }
}
