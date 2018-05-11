package com.viviant.earngold.mvp.selfmedia.model;

//import com.viviant.earngold.api.ApiEngine;
import com.viviant.earngold.api.Api;
import com.viviant.earngold.api.HostType;
import com.viviant.earngold.bean.Gank;
import com.viviant.earngold.bean.GirlData;
import com.viviant.earngold.bean.Meizi;
import com.viviant.earngold.mvp.selfmedia.contract.SelfMediaMainContract;
import com.viviant.earngold.baserx.RxSchedulers;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by weiwei.huang on 2017/8/9.
 */

public class SelfMediaMainModel implements SelfMediaMainContract.Model {

    @Override
    public Observable<Gank> getGank() {
//        return ApiEngine.getInstance().getApiService()
//                .getGank("1")
//                .compose(RxSchedulers.<Gank>switchThread());


//        return Api.getDefault(HostType.GANK_GIRL_PHOTO).getMeiziList(Api.getCacheControl(), "1")
//                .map(new Func1<GirlData, List<Meizi>>() {
//                    @Override
//                    public List<Meizi> call(GirlData girlData) {
//                        return girlData.getResults();
//                    }
//                })
//                .compose(RxSchedulers.<List<Meizi>>switchThread());
        return null;
    }
}
