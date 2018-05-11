package com.viviant.earngold.mvp.skill.presenter;

import android.util.Log;

import com.viviant.earngold.AppConstant;
import com.viviant.earngold.bean.NewsSummary;
import com.viviant.earngold.mvp.skill.contract.SkillContract;
import com.viviant.earngold.mvp.skill.model.SkillModel;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by weiwei.huang on 2017/8/9.
 */

public class SkillPresenter extends SkillContract.Presenter {


    public SkillPresenter(SkillContract.View view) {
        mView = view;
        mModel = new SkillModel();
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
    public void getNewsListData(String type, String id, int startPage) {
//
//        mRxManage.add(mModel.getNewsListData(type,id,startPage).subscribe(new RxSubscriber<List<NewsSummary>>(mContext,false) {
//            @Override
//            public void onStart() {
//                super.onStart();
//                mView.showLoading(mContext.getString(R.string.loading));
//            }
//
//            @Override
//            protected void _onNext(List<NewsSummary> newsSummaries) {
//                mView.returnNewsListData(newsSummaries);
//                mView.stopLoading();
//            }
//
//            @Override
//            protected void _onError(String message) {
//                mView.showErrorTip(message);
//            }
//        }));

        Subscription subscribe = mModel.getNewsListData(type,id,startPage)
                .subscribe(new Subscriber<List<NewsSummary>>() {

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
                    public void onNext(List<NewsSummary> newsSummaries) {
                        mView.onSucceed(newsSummaries);
                        Log.d("weiwei", "onSucceed");
                    }
                });
        addSubscribe(subscribe);
    }
}
