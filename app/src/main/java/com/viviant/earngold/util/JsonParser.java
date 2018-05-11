package com.viviant.earngold.util;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.viviant.earngold.bean.Base;
import com.viviant.earngold.bean.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author yuyh.
 * @date 16/6/4.
 */
public class JsonParser {


    public static String parseBase(Base base, String jsonStr) {
        JSONObject jsonObj = JSON.parseObject(jsonStr);
        String data = "{}";
        for (Map.Entry<String, Object> entry : jsonObj.entrySet()) {
            if (entry.getKey().equals("code")) {
                base.code = Integer.parseInt(entry.getValue().toString());
            } else if (entry.getKey().equals("version")) {
                base.version = entry.getValue().toString();
            } else {
                data = entry.getValue().toString();
            }
        }
        return data;
    }

    public static NewsItem parseNewsItem(String jsonStr) {
        NewsItem newsItem = new NewsItem();
        JSONObject data = JSON.parseObject(JsonParser.parseBase(newsItem, jsonStr)); // articleIds=    NullPoint
        List<NewsItem.NewsItemBean> list = new ArrayList<NewsItem.NewsItemBean>();
        //Set<String> keySet = data.keySet();
        for (Map.Entry<String, Object> item : data.entrySet()) {
            Gson gson = new Gson();
            NewsItem.NewsItemBean bean = gson.fromJson(item.getValue().toString(), NewsItem.NewsItemBean.class);
            bean.index = item.getKey();
            list.add(bean);
        }
        // 由于fastjson获取出来的entrySet是乱序的  所以这边重新排序
        Collections.sort(list, new Comparator<NewsItem.NewsItemBean>() {
            @Override
            public int compare(NewsItem.NewsItemBean lhs, NewsItem.NewsItemBean rhs) {
                return rhs.index.compareTo(lhs.index);
            }
        });
        newsItem.data = list;
        return newsItem;
    }
}
