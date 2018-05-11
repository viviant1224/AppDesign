package com.viviant.earngold.mvp.selfmedia.presenter;

import com.viviant.earngold.bean.Gank;
import com.viviant.earngold.mvp.selfmedia.contract.SelfMediaMainContract;
import com.viviant.earngold.mvp.selfmedia.model.SelfMediaMainModel;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by weiwei.huang on 2017/8/9.
 */

public class SelfMediaMainPresenter extends SelfMediaMainContract.Presenter {
    public SelfMediaMainPresenter(SelfMediaMainContract.View view) {
        mView = view;
        mModel = new SelfMediaMainModel();
    }

    @Override
    public void getGank() {
        Subscription subscribe = mModel.getGank()
                .subscribe(new Subscriber<Gank>() {

                    @Override
                    public void onStart() {
                        mView.showDialog();
                    }

                    @Override
                    public void onCompleted() {
                        mView.hideDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onFail(e.getMessage());
                        onCompleted();
                    }

                    @Override
                    public void onNext(Gank gank) {
                        mView.onSucceed(gank);
                    }
                });
        addSubscribe(subscribe);
    }
}
