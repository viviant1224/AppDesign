package com.viviant.earngold.mvp.daynicegoods.presenter;

import android.util.Log;

import com.viviant.earngold.AppConstant;
import com.viviant.earngold.bean.ArticleBean;
import com.viviant.earngold.bean.Meizi;
import com.viviant.earngold.bean.Person;
import com.viviant.earngold.mvp.daynicegoods.contract.DayNiceGoodsMainContract;
import com.viviant.earngold.mvp.daynicegoods.model.DayNiceGoodsMainModel;
import com.viviant.earngold.util.CommonUtils;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by weiwei.huang on 2017/8/9.
 */

public class DayNiceGoodsMainPresenter extends DayNiceGoodsMainContract.Presenter {
    public DayNiceGoodsMainPresenter(DayNiceGoodsMainContract.View view) {
        mView = view;
        mModel = new DayNiceGoodsMainModel();
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
    public void getDayArticle() {
        Subscription subscribe = mModel.getDayArticle()
                .subscribe(new Subscriber<ArticleBean>() {

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
                    public void onNext(ArticleBean articleBean) {
                        mView.onSucceed(articleBean);
                        Log.d("weiwei", "onSucceed" + articleBean.getTitle());
                    }
                });
        addSubscribe(subscribe);
    }

    @Override
    public void addOnePerson() {


        mModel.addOnePerson(mView.getUsername(), mView.getAddress(), new SaveListener<String>() {
            @Override
            public void done(String objectId,BmobException e) {
                if(e==null){
//                    mView.onSucceed("添加数据成功，返回objectId为："+objectId);
                }else{
                    mView.onFail("创建数据失败：" + e.getMessage());
                }
            }
        });

    }


}
