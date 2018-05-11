package com.viviant.earngold.mvp.daynicegoods.contract;

import com.viviant.earngold.base.BaseModel;
import com.viviant.earngold.base.BasePresenter;
import com.viviant.earngold.base.BaseView;
import com.viviant.earngold.bean.ArticleBean;
import com.viviant.earngold.bean.Meizi;
import com.viviant.earngold.bean.Person;

import java.util.List;

import cn.bmob.v3.listener.SaveListener;
import rx.Observable;

/**
 * Created by weiwei.huang on 2017/8/9.
 */

public interface DayNiceGoodsMainContract {

    interface View extends BaseView {
        void onLoad();
        void onLoaded();
        void onSucceed(ArticleBean articleBean);
        void onFail(String err);
        //返回顶部
        void scrolltoTop();

        String getUsername();
        String getAddress();
    }

    interface Model extends BaseModel {
//        Observable<List<Meizi>> getMeiziList(int page);

        Observable<ArticleBean> getDayArticle();

        void addOnePerson(String username, String address, SaveListener listener);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
//        public abstract void getMeiziList(int page);

        public abstract void addOnePerson();

        public abstract void getDayArticle();
    }
}
