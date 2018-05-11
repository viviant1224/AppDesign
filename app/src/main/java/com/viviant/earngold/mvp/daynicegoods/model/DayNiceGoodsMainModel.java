package com.viviant.earngold.mvp.daynicegoods.model;

import android.util.Log;

import com.viviant.earngold.api.Api;
import com.viviant.earngold.api.HostType;
import com.viviant.earngold.baserx.RxSchedulers;
import com.viviant.earngold.bean.ArticleBean;
import com.viviant.earngold.bean.Person;
import com.viviant.earngold.mvp.daynicegoods.contract.DayNiceGoodsMainContract;

import java.util.Map;

import cn.bmob.v3.listener.SaveListener;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by weiwei.huang on 2017/8/9.
 */

public class DayNiceGoodsMainModel implements DayNiceGoodsMainContract.Model {

    @Override
    public Observable<ArticleBean> getDayArticle() {
        return Api.getDefault(HostType.DAY_ONE_ARTICLE).getDayArticle(Api.getCacheControl())
                .map(new Func1<Map<String, ArticleBean>, ArticleBean>() {
                    @Override
                    public ArticleBean call(Map<String, ArticleBean> map) {
                        ArticleBean articleBean = map.get("data");
                        return articleBean;
                    }
                })
                .compose(RxSchedulers.<ArticleBean>switchThread());
    }


    @Override
    public void addOnePerson(String username, String address, SaveListener listener) {
        Person p2 = new Person();
        p2.setName(username);
        p2.setAddress(address);
        p2.save(listener);
    }
}
