package com.viviant.earngold.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viviant.earngold.baserx.RxManager;

import butterknife.ButterKnife;

/**
 * Created by weiwei.huang on 2017/8/9.
 */

public abstract class BaseFragment<P extends BasePresenter> extends Fragment {

    protected P mPresenter;
    protected View mRootView;
    public RxManager mRxManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        if (mRootView == null)
            mRootView = inflater.inflate(getLayoutResource(), container, false);
        mRxManager=new RxManager();
        ButterKnife.bind(this, mRootView);
        initView();
        return mRootView;
    }

    //获取布局文件
    protected abstract int getLayoutResource();

    //获取view
    public View getRootView() {
        return mRootView;
    }

    //初始化view
    protected abstract void initView();

    //初始化数据
    protected abstract void fetchData();

    @Override
    public void onResume() {
        super.onResume();
        if (onCreatePresenter() != null) {
            mPresenter = onCreatePresenter();
            fetchData();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPresenter != null) {
            mPresenter.unSubscribe();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        if (mPresenter != null)
            mPresenter.onDestroy();
        mRxManager.clear();
        ButterKnife.unbind(this);
    }

    protected abstract P onCreatePresenter();
}