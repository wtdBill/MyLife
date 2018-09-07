package com.example.ypp.mylife;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ypp.mylife.Utils.Logger;
import com.example.ypp.mylife.data.Constants;
import com.example.ypp.mylife.data.DataListener;
import com.example.ypp.mylife.data.UserInfo;
import com.example.ypp.mylife.network.NetService;
import com.example.ypp.mylife.network.RetrofitHelper;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserInfo userInfo=new UserInfo("","","","","",1,"");
//        userInfo.save(new DataListener());
        userInfo.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                Logger.d("saasasa");
            }
        });
        getWeatherInfo();
    }

    private void getWeatherInfo(){
        RetrofitHelper.getStringApi(NetService.class).getWeatherInfo(Constants.INTERFACE_WEATHER,"南京市")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        Logger.e(s);
                    }
                });
    }
}
