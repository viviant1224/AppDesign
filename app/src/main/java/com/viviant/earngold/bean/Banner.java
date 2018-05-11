package com.viviant.earngold.bean;

/**
 * Created by weiwei.huang on 2017/8/30.
 */

public class Banner {
    private String url;
    private String title;

    public Banner(String title, String url) {
        this.url = url;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
