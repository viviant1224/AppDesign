package com.viviant.earngold.mvp.wallpaper.presenter;

import android.util.Log;

import com.viviant.earngold.bean.Gank;
import com.viviant.earngold.bean.Meizi;
import com.viviant.earngold.mvp.wallpaper.contract.WallpaperContract;
import com.viviant.earngold.mvp.wallpaper.model.WallpaperModel;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by weiwei.huang on 2017/8/9.
 */

public class WallpaperPresenter extends WallpaperContract.Presenter {
    public WallpaperPresenter(WallpaperContract.View view) {
        mView = view;
        mModel = new WallpaperModel();
    }

    @Override
    public void getMeiziList(int page) {
        Subscription subscribe = mModel.getMeiziList(page)
                .subscribe(new Subscriber<List<Meizi>>() {

                    @Override
                    public void onStart() {
                        mView.onLoad();
                    }

                    @Override
                    public void onCompleted() {
                        mView.onLoaded();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onFail(e.getMessage());
                        onCompleted();
                        Log.d("weiwei", "onError" + e.getMessage());
                    }

                    @Override
                    public void onNext(List<Meizi> meizi) {
                        mView.onSucceed(meizi);
                        Log.d("weiwei", "onSucceed");
                    }
                });
        addSubscribe(subscribe);
    }
}
