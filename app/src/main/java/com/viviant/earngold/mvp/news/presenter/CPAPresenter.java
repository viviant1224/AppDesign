package com.viviant.earngold.mvp.news.presenter;

import android.util.Log;

import com.viviant.earngold.AppConstant;
import com.viviant.earngold.bean.Meizi;
import com.viviant.earngold.mvp.news.contract.CPAContract;
import com.viviant.earngold.mvp.news.model.CPAModel;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by weiwei.huang on 2017/8/9.
 */

public class CPAPresenter extends CPAContract.Presenter {


    public CPAPresenter(CPAContract.View view) {
        mView = view;
        mModel = new CPAModel();
    }

    @Override
    public void onStart() {
        super.onStart();
        //监听返回顶部动作
        mRxManage.on(AppConstant.LIST_TO_TOP, new Action1<Object>() {
            @Override
            public void call(Object o) {
                mView.scrolltoTop();
            }
        });
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
