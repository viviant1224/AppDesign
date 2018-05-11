package com.viviant.earngold.base;

import com.viviant.earngold.baserx.RxManager;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by weiwei.huang on 2017/8/9.
 */

public class BasePresenter <V extends BaseView, M extends BaseModel>{
    protected V mView;
    protected M mModel;
    public RxManager mRxManage = new RxManager();
    private CompositeSubscription mCompositeSubscription;
    protected void addSubscribe(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
        this.onStart();
    }
    public void onStart(){
    }
    public void unSubscribe() {
        if (mView != null) {
            mView = null;
        }
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.clear();
        }
    }

    public void onDestroy() {
        mRxManage.clear();
    }
}
