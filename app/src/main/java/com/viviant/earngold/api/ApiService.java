package com.viviant.earngold.api;

import com.google.gson.JsonObject;
import com.viviant.earngold.bean.ArticleBean;
import com.viviant.earngold.bean.Gank;
import com.viviant.earngold.bean.GirlData;
import com.viviant.earngold.bean.NbaVideoData;
import com.viviant.earngold.bean.NbaVideoIdData;
import com.viviant.earngold.bean.NewsSummary;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by weiwei.huang on 2017/8/9.
 */

public interface ApiService {
    String BASE_URL="http://gank.io/";

    @GET("api/data/Android/10/{page}")
    Observable<Gank> getGank(@Path("page") String page);

    @GET("data/福利/10/{page}")
    Observable<GirlData> getMeiziList(
            @Header("Cache-Control") String cacheControl,
            @Path("page") int page);


    @GET("article/today?dev=1")
    Observable<Map<String , ArticleBean>> getDayArticle(
            @Header("Cache-Control") String cacheControl);


    @GET("nc/article/{type}/{id}/{startPage}-20.html")
    Observable<Map<String, List<NewsSummary>>> getNewsList(
            @Header("Cache-Control") String cacheControl,
            @Path("type") String type, @Path("id") String id,
            @Path("startPage") int startPage);

    @GET("/news/index")
    Observable<NbaVideoIdData> getNbaVideoId(
            @Header("Cache-Control") String cacheControl,
            @Query("column") String column);

//    @GET("/news/item")
//    Observable<retrofit2.Response<String>> getNbaVideo(
////            @Header("Cache-Control") String cacheControl,
//            @Query("column") String column,
//            @Query("articleIds") String articleIds);


    @GET("/news/item")
    Call<JsonObject> getNbaVideo(@Query("column") String column, @Query("articleIds") String articleIds);


    /**
     * 最新方法
     * http://h5vv.video.qq.com/getinfo?callback=tvp_request_getinfo_callback_340380&platform=11001&charge=0&otype=json&ehost=http%3A%2F%2Fv.qq.com&sphls=0&sb=1&nocache=0&_rnd=1474896074003&vids=m0022ect1qs&defaultfmt=auto&&_qv_rmt=CTWS8OLbA17867igt=&_qv_rmt2=x6oMojAw144904luQ=&sdtfrom=v3010&callback=tvp_request_getinfo_callback_
     *
     * @param vids
     * @return
     */
    @GET("getinfo?platform=11001&charge=0&otype=json")
    Call<String> getVideoRealUrls(@Query("vids") String vids);

}


