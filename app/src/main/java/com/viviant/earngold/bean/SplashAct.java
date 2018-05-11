package com.viviant.earngold.bean;


import cn.bmob.v3.BmobObject;

/**
 * Created by weiwei.huang on 2017/8/25.
 */

public class SplashAct extends BmobObject {

    private String imageUrl;
    private String clickUrl;



    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setClickUrl(String clickUrl) {

        this.clickUrl = clickUrl;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public String getClickUrl() {
        return clickUrl;
    }


}
