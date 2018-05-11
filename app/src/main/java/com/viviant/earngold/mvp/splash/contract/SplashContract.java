package com.viviant.earngold.mvp.splash.contract;

import com.viviant.earngold.base.BaseModel;
import com.viviant.earngold.base.BasePresenter;
import com.viviant.earngold.base.BaseView;
import com.viviant.earngold.bean.SplashAct;

import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by weiwei.huang on 2017/8/9.
 */

public interface SplashContract {

    interface View extends BaseView {
        void onSucceed(SplashAct splashAct);
        void onFail(String err);
        String getImageUrl();
        String getClickUrl();
    }

    interface Model extends BaseModel {
        void addSplashImage(String imageUrl, String clickUrl, SaveListener listener);
        void getSplashImage(QueryListener listener);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void getSplashImage();
        public abstract void addSplashImage();
    }
}
