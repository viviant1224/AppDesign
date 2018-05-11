package com.viviant.earngold.ui.fragment;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.viviant.earngold.AppConstant;
import com.viviant.earngold.R;
import com.viviant.earngold.base.BaseFragment;
import com.viviant.earngold.base.BaseFragmentAdapter;
import com.viviant.earngold.mvp.news.presenter.BSMainPresenter;
import com.viviant.earngold.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by weiwei.huang on 2017/8/9.
 */

public class BSMainFragment extends BaseFragment/*<WallpaperPresenter>*/ {
//
    @Bind(R.id.appbar_cpa)
    AppBarLayout mAppBarLayout;
//    @Bind(R.id.toolbar)
//    Toolbar mToolbar;
    @Bind(R.id.tabs)
    TabLayout mTabs;
    @Bind(R.id.viewpager)
    ViewPager mViewPager;
    @Bind(R.id.fab)
    FloatingActionButton mFab;
    List<Fragment> mFragments;
    String[]  mTitles=new String[]{
            "资讯快报","精彩视频","战术分析","技巧训练","高清壁纸"
    };

    @Override
    protected int getLayoutResource() {
        return R.layout.app_bar_main;
    }

    @Override
    protected void initView() {

//        mToolbar.setTitle("CPA");
//        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);

//        DrawerLayout drawer = (DrawerLayout)getActivity().findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                getActivity(), drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
        setupViewPager();
        initAppBarSetting();
//        CommonUtils.setViewFixStatusHeight(mToolbar, getContext());
//        marginNavigationBar(mFab);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRxManager.post(AppConstant.LIST_TO_TOP, "");
            }
        });

    }

    public void initAppBarSetting() {
//        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
//                if (i == 0 && mFab.isShown()) {
//                    //展开状态
//                    mFab.hide();
//                } else if (i != 0 && !mFab.isShown()) {
//                    //闭合状态
//                    mFab.show();
//                }
//            }
//        });
    }

    public void marginNavigationBar(View view){
        if(!CommonUtils.checkDeviceHasNavigationBar(getContext())){
            return;
        }
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams)view.getLayoutParams();
        layoutParams.setMargins(CommonUtils.dip2px(getContext(), 16),CommonUtils.dip2px(getContext(), 16),CommonUtils.dip2px(getContext(), 16),CommonUtils.getNavigationBarHeight(getContext()) + CommonUtils.dip2px(getContext(), 16));
        view.setLayoutParams(layoutParams);
    }

    @Override
    protected void fetchData() {

    }

    private void setupViewPager() {
        setupViewPager(mViewPager);
        mTabs.setupWithViewPager(mViewPager);
    }


    private void setupViewPager(ViewPager viewPager) {
        mFragments=new ArrayList<>();
        for(int i=0;i<mTitles.length;i++){
//            ListFragment listFragment = ListFragment.newInstance(mTitles[i]);
//            mFragments.add(listFragment);
            if (mTitles[i].trim().equals(mTitles[0])) {
                mFragments.add(createNewsFragment());
            } else if (mTitles[i].trim().equals(mTitles[1])) {
                mFragments.add(createVideoFragment());
            } else if (mTitles[i].trim().equals(mTitles[2])){
                mFragments.add(createTacticalFragment());
            } else if (mTitles[i].trim().equals(mTitles[3])) {
                mFragments.add(createSkillFragment());
            }  else {
                mFragments.add(createWallpaperFragment());
            }
        }
        BaseFragmentAdapter adapter =
                new BaseFragmentAdapter(getActivity().getSupportFragmentManager(),mFragments,mTitles);

        viewPager.setAdapter(adapter);
    }

    private NewsFragment createNewsFragment() {
        NewsFragment fragment = new NewsFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    private VideoFragment createVideoFragment() {
        VideoFragment fragment = new VideoFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    private TacticalFragment createTacticalFragment() {
        TacticalFragment fragment = new TacticalFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    private CPAFragment createSkillFragment() {
        CPAFragment fragment = new CPAFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    private WallpaperFragment createWallpaperFragment() {
        WallpaperFragment fragment = new WallpaperFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

//    private CPAFragment createCPAFragments() {
//        CPAFragment fragment = new CPAFragment();
//        Bundle bundle = new Bundle();
//        fragment.setArguments(bundle);
//        return fragment;
//    }
//
//    private CPAHotFragment createCPAHotFragments() {
//        CPAHotFragment fragment = new CPAHotFragment();
//        Bundle bundle = new Bundle();
//        fragment.setArguments(bundle);
//        return fragment;
//    }

    @Override
    protected BSMainPresenter onCreatePresenter() {
        return null;
    }

}
