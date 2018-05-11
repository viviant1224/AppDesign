package com.viviant.earngold.mvp.bmobtest.model;

import com.viviant.earngold.bean.Banner;
import com.viviant.earngold.bean.Person;
import com.viviant.earngold.mvp.bmobtest.contract.BmobTestContract;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by weiwei.huang on 2017/8/9.
 */

public class BmobTestModel implements BmobTestContract.Model {



    @Override
    public void addOnePerson(String username, String address, SaveListener listener) {
        Person p2 = new Person();
        p2.setName(username);
        p2.setAddress(address);
        p2.save(listener);
    }

    @Override
    public void deleteOnePerson(String username, String address, SaveListener listener) {

    }

    @Override
    public void updateOnePerson(String uid, String username, String address, UpdateListener listener) {
        Person p2 = new Person();
        p2.setName(username);
        p2.setAddress(address);
        p2.update(uid, listener);
    }

    @Override
    public void uploadFile(String username, String address, SaveListener listener) {

    }

    @Override
    public void getOnePerson(String username, String address, SaveListener listener) {

    }


    @Override
    public List<Banner> getBannerList() {
        List<Banner> banners = new ArrayList<Banner>();

        banners.add(new Banner("标题1",
                "http://img2.3lian.com/2014/c7/25/d/40.jpg"));
        banners.add(new Banner("标题2",
                "http://img2.3lian.com/2014/c7/25/d/41.jpg"));
        banners.add(new Banner("标题3",
                "http://imgsrc.baidu.com/forum/pic/item/b64543a98226cffc8872e00cb9014a90f603ea30.jpg"));
        banners.add(new Banner("标题4",
                "http://imgsrc.baidu.com/forum/pic/item/261bee0a19d8bc3e6db92913828ba61eaad345d4.jpg"));
        return banners;
    }
}
