package com.example.ypp.mylife;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ypp.mylife.Utils.Logger;
import com.example.ypp.mylife.data.Constants;
import com.example.ypp.mylife.data.UserInfo;
import com.example.ypp.mylife.network.bean.XIaoshuo;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserInfo userInfo=new UserInfo("","","","","",1,"");
//        userInfo.save(new DataListener());
//        userInfo.save(new SaveListener<String>() {
//            @Override
//            public void done(String s, BmobException e) {
//                Logger.d("saasasa");
//            }
//        });
        getWeatherInfo();
    }

    private void getWeatherInfo(){
        OkGo.<String>post(Constants.xiaoshuo)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Logger.e(response.body());
                        Gson gson=new Gson();
                        XIaoshuo xIaoshuo=gson.fromJson(response.body(),XIaoshuo.class);
                    }
                });

        OkGo.<String>post(Constants.INTERFACE_WEATHER)
                .tag(this)
                .params("city","南京")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Logger.e(response.body());
                    }
                });
    }
}
