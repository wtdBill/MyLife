package com.example.ypp.mylife.Utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.example.ypp.mylife.R;
import com.example.ypp.mylife.application.LifeApplication;
import com.example.ypp.mylife.data.UserInfo;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Crystal on 2018/6/10
 */

public class Utils {
    public static int getVersionCode(Context mCOntext){
        if (mCOntext==null){
            mCOntext= LifeApplication.getContext();
        }
        PackageManager packageManager=mCOntext.getPackageManager();
        PackageInfo packageInfo=null;
        try {
            packageInfo=packageManager.getPackageInfo(mCOntext.getPackageName(),0);
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
        int code=packageInfo.versionCode;
        return code;
    }
    /**
     * 检查是否有网络
     */
    public static boolean isNetworkAvailable(Context context) {

        NetworkInfo info = getNetworkInfo(context);
        return info != null && info.isAvailable();
    }

    private static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    /**
     * 登录操作
     * @param phone
     * @param pwd
     */
    private static void login(String phone, final String pwd){
        BmobQuery<UserInfo>query=new BmobQuery<>();
        query.addWhereEqualTo("phone",phone);
        query.findObjects(new FindListener<UserInfo>() {
            @Override
            public void done(List<UserInfo> list, BmobException e) {
                if (list.size()==1){
                    if (TextUtils.equals(list.get(0).getPwd(),MD5Utils.encode(pwd))){
                        // TODO: 2018/9/2 登录成功
                        ToastUtils.show(R.string.login_success);
                    }else {
                        ToastUtils.show(R.string.pwd_error);
                    }
                }else if (list.size()==0){
                    ToastUtils.show(R.string.user_not_exists);
                }
            }
        });
    }
}
