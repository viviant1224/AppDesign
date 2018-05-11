package com.viviant.earngold.mvp.bmobtest.contract;

import com.viviant.earngold.base.BaseModel;
import com.viviant.earngold.base.BasePresenter;
import com.viviant.earngold.base.BaseView;
import com.viviant.earngold.bean.Banner;

import java.util.List;

import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by weiwei.huang on 2017/8/9.
 */

public interface BmobTestContract {

    interface View extends BaseView {
        void onLoad();
        void onLoaded();
        void onSucceed(String msg);
        void onFail(String err);
        //返回顶部
        void scrolltoTop();
        String getUsername();
        String getUid();
        String getAddress();
        String getUpdateAddress();
        String getUpdateUsername();
        void onLoadBannerList(List<Banner> bannerList);
    }

    interface Model extends BaseModel {
//        Observable<List<Meizi>> getMeiziList(int page);

        void addOnePerson(String username, String address, SaveListener listener);
        void updateOnePerson(String uid, String username, String address, UpdateListener listener);
        void deleteOnePerson(String username, String address, SaveListener listener);
        void getOnePerson(String username, String address, SaveListener listener);
        void uploadFile(String username, String address, SaveListener listener);
        List<Banner> getBannerList();
    }

    abstract class Presenter extends BasePresenter<View, Model> {
//        public abstract void getMeiziList(int page);

        public abstract void addOnePerson();
        public abstract void updateOnePerson();
        public abstract void deleteOnePerson();
        public abstract void getOnePerson();
        public abstract void uploadFile();
        public abstract void getBannerList();
    }
}
