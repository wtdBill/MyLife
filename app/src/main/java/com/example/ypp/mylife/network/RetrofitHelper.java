package com.example.ypp.mylife.network;

import com.example.ypp.mylife.Utils.SPUtils;
import com.example.ypp.mylife.Utils.Utils;
import com.example.ypp.mylife.application.LifeApplication;
import com.example.ypp.mylife.converter.StringConverterFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by Crystal on 2018/6/10
 */

public class RetrofitHelper {

    private static OkHttpClient mOkHttpClient;

    static {
        initOkHttpClient();
    }


    /**
     * 根据传入的baseUrl，和api创建retrofit
     */
    private static <T> T createApi(Class<T> clazz) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.baidu.com")
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(clazz);
    }

    /**
     * 创建转化成string的retrofit
     */
    private static <T> T createApiString(Class<T> clazz) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.baidu.com")
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(StringConverterFactory.create())
                .build();

        return retrofit.create(clazz);
    }


    private static void initOkHttpClient(){
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (mOkHttpClient==null){
            Cache cache=new Cache(new File(LifeApplication.getContext().getCacheDir(),"HttpCache"),1024*1024*10);
            mOkHttpClient=new OkHttpClient.Builder()
                    .cache(cache)
                    .addInterceptor(interceptor)
                    .addNetworkInterceptor(new CacheInterceptor())
                    .retryOnConnectionFailure(true)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30,TimeUnit.SECONDS)
                    .readTimeout(20,TimeUnit.SECONDS)
                    .addNetworkInterceptor(new CacheInterceptor())
                    .addInterceptor(new ParameterInterceptor())
                    .build();

        }
    }

    private static class CacheInterceptor implements Interceptor{

        @Override
        public Response intercept(Chain chain) throws IOException {
            int maxAge=0;
            int maxStale=60*60*24;
            Request request=chain.request();
            if (Utils.isNetworkAvailable(LifeApplication.getContext())){
                request=request.newBuilder()
                        .cacheControl(CacheControl.FORCE_NETWORK)
                        .build();
            }else {
                request=request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response response=chain.proceed(request);
            if (Utils.isNetworkAvailable(LifeApplication.getContext())){
                response=response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            }else {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    }

    /**
     * 添加参数拦截器，添加默认参数
     */
    private static class ParameterInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request oldRequest = chain.request();
            String sign = "";
            String nwtime = String.valueOf(System.currentTimeMillis() / 1000);
            String city = "";
            // 添加新的参数
            HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                    .newBuilder()
                    .scheme(oldRequest.url().scheme())
                    .host(oldRequest.url().host())
                    .addQueryParameter("sign", sign)
                    .addQueryParameter("nwtime", nwtime)
                    .addQueryParameter("os", "android")
                    .addQueryParameter("appcode", "diary")
                    .addQueryParameter("channel", "vivo")
                    .addQueryParameter("userid", SPUtils.getUserId())
                    .addQueryParameter("areainfo", city);
            // 新的请求
            Request newRequest = oldRequest.newBuilder()
                    .method(oldRequest.method(), oldRequest.body())
                    .url(authorizedUrlBuilder.build())
                    .removeHeader("User-Agent")
                    .build();
            return chain.proceed(newRequest);
        }
    }

    public static NetService getNetApi(){
        return createApi(NetService.class);
    }

    public static <T> T getStringApi(Class<T> clazz) {
        return createApiString(clazz);
    }

}
