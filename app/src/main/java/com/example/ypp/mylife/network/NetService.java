package com.example.ypp.mylife.network;

import com.example.ypp.mylife.data.Constants;
import com.example.ypp.mylife.network.bean.WeatherInfo;


import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Crystal on 2018/9/2
 */
public interface NetService {

    //天气
    @FormUrlEncoded
    @POST
    Observable<String> getWeatherInfo(@Url String url,@Field("city") String cityName);
}
