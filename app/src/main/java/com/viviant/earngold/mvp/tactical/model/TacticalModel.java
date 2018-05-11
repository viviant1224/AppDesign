package com.viviant.earngold.mvp.tactical.model;

import com.viviant.earngold.api.Api;
import com.viviant.earngold.api.HostType;
import com.viviant.earngold.baserx.RxSchedulers;
import com.viviant.earngold.bean.NewsSummary;
import com.viviant.earngold.mvp.tactical.contract.TacticalContract;
import com.viviant.earngold.util.TimeUtil;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by weiwei.huang on 2017/8/9.
 */

public class TacticalModel implements TacticalContract.Model {

//    @Override
//    public Observable<List<Meizi>> getMeiziList(int page) {
////        return ApiEngine.getInstance().getApiService().getMeiziList(page)
//        return Api.getDefault(HostType.GANK_GIRL_PHOTO).getMeiziList(Api.getCacheControl(), page)
//                .map(new Func1<GirlData, List<Meizi>>() {
//                    @Override
//                    public List<Meizi> call(GirlData girlData) {
//                        return girlData.getResults();
//                    }
//                })
//                .compose(RxSchedulers.<List<Meizi>>switchThread());
//    }


    /**
     * 获取新闻列表
     * @param type
     * @param id
     * @param startPage
     * @return
     */
    @Override
    public Observable<List<NewsSummary>> getNewsListData(final String type, final String id, final int startPage) {
        return Api.getDefault(HostType.NETEASE_NEWS).getNewsList(Api.getCacheControl(),type, id, startPage)
                .flatMap(new Func1<Map<String, List<NewsSummary>>, Observable<NewsSummary>>() {
                    @Override
                    public Observable<NewsSummary> call(Map<String, List<NewsSummary>> map) {
//                        if (id.endsWith(ApiConstants.HOUSE_ID)) {
//                            // 房产实际上针对地区的它的id与返回key不同
//                            return Observable.from(map.get("北京"));
//                        }
                        return Observable.from(map.get(id));
                    }
                })
                //转化时间
                .map(new Func1<NewsSummary, NewsSummary>() {
                    @Override
                    public NewsSummary call(NewsSummary newsSummary) {
                        String ptime = TimeUtil.formatDate(newsSummary.getPtime());
                        newsSummary.setPtime(ptime);
                        return newsSummary;
                    }
                })
                .distinct()//去重
                .toSortedList(new Func2<NewsSummary, NewsSummary, Integer>() {
                    @Override
                    public Integer call(NewsSummary newsSummary, NewsSummary newsSummary2) {
                        return newsSummary2.getPtime().compareTo(newsSummary.getPtime());
                    }
                })
                //声明线程调度
                .compose(RxSchedulers.<List<NewsSummary>>switchThread());
    }
}
