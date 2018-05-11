package com.viviant.earngold.mvp.wallpaper.contract;

import com.viviant.earngold.base.BaseModel;
import com.viviant.earngold.base.BasePresenter;
import com.viviant.earngold.base.BaseView;
import com.viviant.earngold.bean.Gank;
import com.viviant.earngold.bean.Meizi;

import java.util.List;

import rx.Observable;

/**
 * Created by weiwei.huang on 2017/8/9.
 */

public interface WallpaperContract {
    interface View extends BaseView {
        void onLoad();
        void onLoaded();
        //        void onLoadMore();
        void onSucceed(List<Meizi> data);
        void onFail(String err);
        //返回顶部
        void scrolltoTop();
    }
    interface Model extends BaseModel {
        Observable<List<Meizi>> getMeiziList(int page);
    }
    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void getMeiziList(int page);
    }
}
