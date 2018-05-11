package com.viviant.earngold.mvp.main.presenter;

import com.viviant.earngold.bean.Gank;
import com.viviant.earngold.mvp.main.contract.BSMainContract;
import com.viviant.earngold.mvp.main.model.BSMainModel;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by weiwei.huang on 2017/8/9.
 */

public class BSMainPresenter extends BSMainContract.Presenter {
    public BSMainPresenter(BSMainContract.View view) {
        mView = view;
        mModel = new BSMainModel();
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
