package com.viviant.earngold.mvp.bmobtest.presenter;

import com.viviant.earngold.AppConstant;
import com.viviant.earngold.mvp.bmobtest.contract.BmobTestContract;
import com.viviant.earngold.mvp.bmobtest.model.BmobTestModel;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import rx.functions.Action1;

/**
 * Created by weiwei.huang on 2017/8/9.
 */

public class BmobTestPresenter extends BmobTestContract.Presenter {
    public BmobTestPresenter(BmobTestContract.View view) {
        mView = view;
        mModel = new BmobTestModel();
    }

    @Override
    public void onStart() {
        super.onStart();
        //监听返回顶部动作
        mRxManage.on(AppConstant.LIST_TO_TOP, new Action1<Object>() {
            @Override
            public void call(Object o) {
                mView.scrolltoTop();
            }
        });
    }

    @Override
    public void addOnePerson() {
        mModel.addOnePerson(mView.getUsername(), mView.getAddress(), new SaveListener<String>() {
            @Override
            public void done(String objectId,BmobException e) {
                if(e==null){
                    mView.onSucceed("添加数据成功，返回objectId为："+objectId);
                }else{
                    mView.onFail("创建数据失败：" + e.getMessage());
                }
            }
        });
    }

    @Override
    public void uploadFile() {

    }

    @Override
    public void updateOnePerson() {

        mModel.updateOnePerson(mView.getUid(), mView.getUpdateUsername(), mView.getUpdateAddress(), new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    mView.onSucceed("修改数据成功");
                }else{
                    mView.onFail("修改数据失败：" + e.getMessage());
                }
            }
        });
    }

    @Override
    public void deleteOnePerson() {

    }

    @Override
    public void getOnePerson() {

    }

    @Override
    public void getBannerList() {
        mView.onLoadBannerList(mModel.getBannerList());
    }
}
