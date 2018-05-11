/*
 * Copyright (c) 2016 咖枯 <kaku201313@163.com | 3772304@qq.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.viviant.earngold.api;

public class ApiConstants {
    public static final String SINA_PHOTO_HOST = "http://gank.io/api/";

    public static final String DAY_ONE_ARTICLE = "https://interface.meiriyiwen.com/";

    public static final String NETEAST_HOST = "https://c.m.163.com/";

    public static final String TECENT_URL_SERVER = "http://sportsnba.qq.com";

    public static final String TECENT_URL_SERVER_1 = "http://h5vv.video.qq.com";

    /**
     * 新闻id获取类型
     *
     * @param id 新闻id
     * @return 新闻类型
     */
//    public static String getType(String id) {
//        switch (id) {
//            case HEADLINE_ID:
//                return HEADLINE_TYPE;
//            case HOUSE_ID:
//                return HOUSE_TYPE;
//            default:
//                break;
//        }
//        return OTHER_TYPE;
//    }

    /**
     * 获取对应的host
     *
     * @param hostType host类型
     * @return host
     */
    public static String getHost(int hostType) {
        String host;
        switch (hostType) {
            case HostType.NETEASE_NEWS_VIDEO:
                host = "";
                break;
            case HostType.GANK_GIRL_PHOTO:
                host = SINA_PHOTO_HOST;
                break;
            case HostType.NEWS_DETAIL_HTML_PHOTO:
                host = "http://kaku.com/";
                break;
            case HostType.DAY_ONE_ARTICLE:
                host = DAY_ONE_ARTICLE;
                break;
            case HostType.NETEASE_NEWS:
                host = NETEAST_HOST;
                break;
            case HostType.TECENT_URL:
                host = TECENT_URL_SERVER;
                break;
            case HostType.TECENT_URL_1:
                host = TECENT_URL_SERVER_1;
                break;
            default:
                host = "";
                break;
        }
        return host;
    }
}
