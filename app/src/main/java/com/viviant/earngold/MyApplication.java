package com.viviant.earngold;

import android.app.Application;

import com.tencent.bugly.Bugly;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

/**
 * Created by weiwei.huang on 2017/8/9.
 */

public class MyApplication extends Application{
    private static MyApplication mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Bugly.init(getApplicationContext(), "4812f9f009", false);

        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        BmobConfig config =new BmobConfig.Builder(this)
        //设置appkey
        .setApplicationId("714a0c9b4c0cff18276044048d81b351")
        //请求超时时间（单位为秒）：默认15s
        .setConnectTimeout(30)
        //文件分片上传时每片的大小（单位字节），默认512*1024
        .setUploadBlockSize(1024*1024)
        //文件的过期时间(单位为秒)：默认1800s
        .setFileExpiration(2500)
        .build();
        Bmob.initialize(config);
    }

    public static MyApplication getContext() {
        return mContext;
    }

}
