package com.viviant.earngold.mvp.main.contract;

import com.viviant.earngold.base.BaseModel;
import com.viviant.earngold.base.BasePresenter;
import com.viviant.earngold.base.BaseView;
import com.viviant.earngold.bean.Gank;

import rx.Observable;

/**
 * Created by weiwei.huang on 2017/8/9.
 */

public interface BSMainContract {
    interface View extends BaseView {
        void showDialog();
        void onSucceed(Gank data);
        void onFail(String err);
        void hideDialog();
    }
    interface Model extends BaseModel {
        Observable<Gank> getGank();
    }
    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void getGank();
    }
}
