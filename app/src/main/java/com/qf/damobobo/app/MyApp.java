package com.qf.damobobo.app;

import android.app.Application;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by TimiZhuo on 2016/12/6.
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initOkhttp();
    }

    private void initOkhttp() {
        OkHttpClient client=new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10,TimeUnit.SECONDS)
                .build();
        OkHttpUtils.initClient(client);
    }
}
