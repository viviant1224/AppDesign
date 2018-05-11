package com.viviant.earngold.mvp.video.model;


import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.viviant.earngold.MyApplication;
import com.viviant.earngold.api.Api;
import com.viviant.earngold.api.HostType;
import com.viviant.earngold.api.RequestCallback;
import com.viviant.earngold.baserx.RxSchedulers;
import com.viviant.earngold.bean.NbaVideoData;
import com.viviant.earngold.bean.NbaVideoId;
import com.viviant.earngold.bean.NbaVideoIdData;
import com.viviant.earngold.bean.NewsItem;
import com.viviant.earngold.bean.VideoInfo;
import com.viviant.earngold.mvp.video.contract.VideoContract;
import com.viviant.earngold.util.ACache;
import com.viviant.earngold.util.JsonParser;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by weiwei.huang on 2017/8/9.
 */

public class VideoModel implements VideoContract.Model {

    @Override
    public Observable<List<NbaVideoId>> getNbaVideoId(String column) {
        return Api.getDefault(HostType.TECENT_URL).getNbaVideoId(Api.getCacheControl(), column)
                .map(new Func1<NbaVideoIdData, List<NbaVideoId>>() {
                    @Override
                    public List<NbaVideoId> call(NbaVideoIdData nbaVideoIdData) {
                        return nbaVideoIdData.getData();
                    }
                })
                .compose(RxSchedulers.<List<NbaVideoId>>switchThread());
    }

    /**
     * 根据索引获取新闻列表
     *
     * @param articleIds 索引值。多个索引以“,”分隔
     * @param isRefresh  是否重新请求数据
//     * @param cbk
     */
    public void getNbaVideo(String newsType, String articleIds, boolean isRefresh, final RequestCallback<NewsItem> cbk) {
        final String key = "getNewsItem" + articleIds;
        final ACache cache = ACache.get(MyApplication.getContext());
        Object obj = cache.getAsObject(key);
        if (obj != null && !isRefresh) {
            NewsItem newsItem = (NewsItem) obj;
            cbk.onSuccess(newsItem);
            return;
        }

        Call<JsonObject> call = Api.getDefault(HostType.TECENT_URL).getNbaVideo(newsType, articleIds);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response != null && !TextUtils.isEmpty(response.body().toString())) {
                    String jsonStr = response.body().toString();
                    NewsItem newsItem = JsonParser.parseNewsItem(jsonStr);
                    cbk.onSuccess(newsItem);
                    cache.put(key, newsItem);
//                    LogUtils.d("resp:" + jsonStr);
                } else {
                    cbk.onFailure("获取数据失败");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                cbk.onFailure(t.getMessage());
            }
        });

    }


    public static void getVideoRealUrls(String vid, final RequestCallback<VideoInfo> cbk) {
        Call<String> call = Api.getDefault(HostType.TECENT_URL_1).getVideoRealUrls(vid);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response != null && !TextUtils.isEmpty(response.body())) {
                    String resp = response.body()
                            .replaceAll("QZOutputJson=", "")
                            .replaceAll(" ", "")
                            .replaceAll("\n", "");
                    if (resp.endsWith(";"))
                        resp = resp.substring(0, resp.length() - 1);
                    VideoInfo info = new Gson().fromJson(resp, VideoInfo.class);
                    cbk.onSuccess(info);

                } else {
                    cbk.onFailure("");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                cbk.onFailure(t.getMessage());
            }
        });
    }
}
