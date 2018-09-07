package com.example.ypp.mylife.data;

import com.example.ypp.mylife.Utils.Logger;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Crystal on 2018/9/2
 */
public class DataListener extends SaveListener {
    @Override
    public void done(Object o, BmobException e) {
        if (e==null){
            Logger.d(o.toString());
        }else {
            Logger.e(e.getMessage());
        }
    }
}
