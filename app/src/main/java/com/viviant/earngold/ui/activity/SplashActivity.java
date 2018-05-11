package com.viviant.earngold.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.viviant.earngold.R;
import com.viviant.earngold.base.BaseActivity;
import com.viviant.earngold.bean.SplashAct;
import com.viviant.earngold.mvp.splash.contract.SplashContract;
import com.viviant.earngold.mvp.splash.presenter.SplashPresenter;

import butterknife.Bind;


public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContract.View{

    @Bind(R.id.splash_iv)
    ImageView mSplashIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void fetchData() {
//        mPresenter.addSplashImage();
        mPresenter.getSplashImage();
    }

    @Override
    protected SplashPresenter onCreatePresenter() {
        return new SplashPresenter(this);
    }

    @Override
    public void onFail(String err) {

    }

    @Override
    public void onSucceed(SplashAct splashAct) {
        Glide.with(this).load(splashAct.getImageUrl()).fitCenter().crossFade().into(mSplashIV);
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(5000);
                    startActivity(MainActivity.class);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public String getClickUrl() {
        return "";
    }

    @Override
    public String getImageUrl() {
        return "https://ss0.baidu.com/7Po3dSag_xI4khGko9WTAnF6hhy/image/h%3D220/sign=9effa2ad88025aafcc3279c9cbecab8d/562c11dfa9ec8a132ccbb38dfd03918fa1ecc08e.jpg";
    }
}
