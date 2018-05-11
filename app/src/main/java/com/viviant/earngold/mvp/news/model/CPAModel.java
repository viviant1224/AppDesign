package com.viviant.earngold.mvp.news.model;

import com.viviant.earngold.api.Api;
import com.viviant.earngold.api.HostType;
import com.viviant.earngold.bean.GirlData;
import com.viviant.earngold.bean.Meizi;
import com.viviant.earngold.mvp.news.contract.CPAContract;
import com.viviant.earngold.baserx.RxSchedulers;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by weiwei.huang on 2017/8/9.
 */

public class CPAModel implements CPAContract.Model {

    @Override
    public Observable<List<Meizi>> getMeiziList(int page) {
//        return ApiEngine.getInstance().getApiService().getMeiziList(page)
        return Api.getDefault(HostType.GANK_GIRL_PHOTO).getMeiziList(Api.getCacheControl(), page)
                .map(new Func1<GirlData, List<Meizi>>() {
                    @Override
                    public List<Meizi> call(GirlData girlData) {
                        return girlData.getResults();
                    }
                })
                .compose(RxSchedulers.<List<Meizi>>switchThread());
    }
}
