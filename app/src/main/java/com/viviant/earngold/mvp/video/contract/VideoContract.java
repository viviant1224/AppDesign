package com.viviant.earngold.mvp.video.contract;

import com.viviant.earngold.api.RequestCallback;
import com.viviant.earngold.base.BaseModel;
import com.viviant.earngold.base.BasePresenter;
import com.viviant.earngold.base.BaseView;
import com.viviant.earngold.bean.NbaVideoId;
import com.viviant.earngold.bean.NewsItem;
import com.viviant.earngold.bean.VideoInfo;

import java.util.List;

import rx.Observable;

/**
 * Created by weiwei.huang on 2017/8/9.
 */

public interface VideoContract {
    interface View extends BaseView {
        void onLoad();
        void onLoaded();
        void onSucceed(List<NbaVideoId> data, boolean isRefresh);
        void onFail(String err);
        //返回顶部
        void scrolltoTop();

        void onGetVideoSuccess(NewsItem newsItem, boolean isRefresh);
        void onGetVideoFail(String error);
    }
    interface Model extends BaseModel {
        Observable<List<NbaVideoId>> getNbaVideoId(String column);
        void getNbaVideo(String newsType, String articleIds, boolean isRefresh, final RequestCallback<NewsItem> cbk);
    }
    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void getNbaVideoId(String column, boolean isRefresh);
        public abstract void getNbaVideo(String newsType, String articleIds, boolean isRefresh);
    }
}
