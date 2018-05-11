package com.viviant.earngold.mvp.main.model;

//import com.viviant.earngold.api.ApiEngine;
import com.viviant.earngold.bean.Gank;
import com.viviant.earngold.mvp.main.contract.BSMainContract;

import rx.Observable;

/**
 * Created by weiwei.huang on 2017/8/9.
 */

public class BSMainModel implements BSMainContract.Model {

    @Override
    public Observable<Gank> getGank() {
//        return ApiEngine.getInstance().getApiService()
//                .getGank("1")
//                .compose(RxSchedulers.<Gank>switchThread());
        return null;
    }
}
