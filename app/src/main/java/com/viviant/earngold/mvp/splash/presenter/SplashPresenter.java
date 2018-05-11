package com.viviant.earngold.mvp.splash.presenter;

import android.util.Log;

import com.viviant.earngold.bean.SplashAct;
import com.viviant.earngold.mvp.splash.contract.SplashContract;
import com.viviant.earngold.mvp.splash.model.SplashModel;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by weiwei.huang on 2017/8/9.
 */

public class SplashPresenter extends SplashContract.Presenter {

    public SplashPresenter(SplashContract.View view) {
        mView = view;
        mModel = new SplashModel();
    }

    @Override
    public void getSplashImage() {
        mModel.getSplashImage(new QueryListener<SplashAct>() {
            @Override
            public void done(SplashAct object,BmobException e) {
                if(e == null){
                    mView.onSucceed(object);
                    Log.d("weiwei", "pp" + object.getCreatedAt());
                }else{
                    Log.d("weiwei", "eee" + e.getMessage());
                    mView.onFail(e.getMessage());
                }
            }
        });
    }

    @Override
    public void addSplashImage() {
        mModel.addSplashImage(mView.getImageUrl(), mView.getClickUrl(), new SaveListener<String>() {
            @Override
            public void done(String objectId,BmobException e) {
                if(e==null){
//                    mView.onSucceed("添加数据成功，返回objectId为："+objectId);
                }else{
                    mView.onFail("创建数据失败：" + e.getMessage());
                }
            }
        });
    }
}
