package com.viviant.earngold.mvp.skill.contract;

import com.viviant.earngold.base.BaseModel;
import com.viviant.earngold.base.BasePresenter;
import com.viviant.earngold.base.BaseView;
import com.viviant.earngold.bean.NewsSummary;

import java.util.List;

import rx.Observable;

/**
 * Created by weiwei.huang on 2017/8/9.
 */

public interface SkillContract {
    interface View extends BaseView {
        void onLoad();
        void onLoaded();
//        void onLoadMore();
        void onSucceed(List<NewsSummary> data);
        void onFail(String err);
        //返回顶部
        void scrolltoTop();
    }
    interface Model extends BaseModel {
//        Observable<List<Meizi>> getMeiziList(int page);
        //请求获取新闻
        Observable <List<NewsSummary>> getNewsListData(String type, final String id, int startPage);
    }
    abstract class Presenter extends BasePresenter<View, Model> {
//        public abstract void getMeiziList(int page);
        public abstract void getNewsListData(String type, final String id, int startPage);
    }
}
