package com.viviant.earngold.mvp.video.presenter;

import android.util.Log;

import com.viviant.earngold.AppConstant;
import com.viviant.earngold.api.RequestCallback;
import com.viviant.earngold.bean.NbaVideoId;
import com.viviant.earngold.bean.NewsItem;
import com.viviant.earngold.bean.VideoInfo;
import com.viviant.earngold.mvp.video.contract.VideoContract;
import com.viviant.earngold.mvp.video.model.VideoModel;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by weiwei.huang on 2017/8/9.
 */

public class VideoPresenter extends VideoContract.Presenter {


    public VideoPresenter(VideoContract.View view) {
        mView = view;
        mModel = new VideoModel();
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
    public void getNbaVideoId(String column, final boolean isRefresh) {

        Subscription subscribe = mModel.getNbaVideoId(column)
                .subscribe(new Subscriber<List<NbaVideoId>>() {

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
                    public void onNext(List<NbaVideoId> data) {
                        mView.onSucceed(data, isRefresh);
                        Log.d("weiwei", " getNbaVideoId onSucceed");
                    }
                });
        addSubscribe(subscribe);
    }


//    @Override
//    public void getNbaVideo(String column, String articleIds) {
//        Subscription subscribe = mModel.getNbaVideo(column, articleIds)
//                .subscribe(new Subscriber<List<NewsItem.NewsItemBean>>() {
//
//                    @Override
//                    public void onStart() {
//                        mView.onLoad();
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                        mView.onLoaded();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        mView.onFail(e.getMessage());
//                        onCompleted();
//                        Log.d("weiwei", "onError" + e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(List<NewsItem.NewsItemBean> data) {
////                        mView.onSucceed(data, isRefresh);
//                        Log.d("weiwei", "onSucceed" + data.get(0).imgurl);
//                    }
//                });
//        addSubscribe(subscribe);
//    }


//    @Override
//    public void getNbaVideo(String newsType, String articleIds, boolean isRefresh) {
//        Subscription subscribe = mModel.getNbaVideo(newsType, articleIds, isRefresh)
//                .subscribe(new Subscriber<NewsItem>() {
//
//                    @Override
//                    public void onStart() {
//                        mView.onLoad();
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                        mView.onLoaded();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        mView.onFail(e.getMessage());
//                        onCompleted();
//                        Log.d("weiwei", "onError" + e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(NewsItem newsItem) {
//
////                        Log.d("weiwei", "news" + newsItem.data.get(0).imgurl);
////                        mView.onSucceed(data, isRefresh);
////                        Log.d("weiwei", "onSucceed" + data.get(0).imgurl);
//                    }
//                });
//        addSubscribe(subscribe);
//    }


    @Override
    public void getNbaVideo(String newsType, String articleIds, final boolean isRefresh) {
       mModel.getNbaVideo(newsType, articleIds, isRefresh, new RequestCallback<NewsItem>() {
           @Override
           public void onSuccess(NewsItem newsItem) {
               Log.d("weiwei", "newsss"+ newsItem.data.size());
               mView.onGetVideoSuccess(newsItem, isRefresh);
           }

           @Override
           public void onFailure(String message) {
               Log.d("weiwei", "newsss--"+ message);
               mView.onGetVideoFail(message);
           }
       });
    }

//    @Override
//    public void getVideoRealUrls(String vid) {
//        mModel.getVideoRealUrls(vid, new RequestCallback<VideoInfo>() {
//            @Override
//            public void onSuccess(VideoInfo videoInfo) {
//
//            }
//
//            @Override
//            public void onFailure(String message) {
//
//            }
//        });
//    }
}
