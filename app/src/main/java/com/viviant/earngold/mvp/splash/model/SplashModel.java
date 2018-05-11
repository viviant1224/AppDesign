package com.viviant.earngold.mvp.splash.model;

import com.viviant.earngold.bean.SplashAct;
import com.viviant.earngold.mvp.splash.contract.SplashContract;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by weiwei.huang on 2017/8/9.
 */

public class SplashModel implements SplashContract.Model {

    @Override
    public void getSplashImage(QueryListener listener) {

        //查找Person表里面id为6b6c11c537的数据
        BmobQuery<SplashAct> bmobQuery = new BmobQuery<SplashAct>();
        bmobQuery.getObject("d39a183aec", listener);

    }

    @Override
    public void addSplashImage(String imageUrl, String clickUrl, SaveListener listener) {
        SplashAct splashAct = new SplashAct();
        splashAct.setImageUrl(imageUrl);
        splashAct.setClickUrl(clickUrl);
        splashAct.save(listener);
    }
}
