package com.viviant.earngold.ui.fragment;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.viviant.earngold.R;
import com.viviant.earngold.base.BaseFragment;
import com.viviant.earngold.bean.Banner;
import com.viviant.earngold.bean.TestBean;
import com.viviant.earngold.mvp.bmobtest.contract.BmobTestContract;
import com.viviant.earngold.mvp.bmobtest.presenter.BmobTestPresenter;
import com.viviant.earngold.ui.adapter.GridTestAdapter;
import com.viviant.earngold.ui.adapter.MainRecycleviewAdapter;
import com.viviant.earngold.ui.adapter.SpaceItemDecoration;
import com.viviant.earngold.ui.adapter.TestItemAdapter;
import com.viviant.earngold.ui.adapter.TestRecyclerAdapter;
import com.viviant.earngold.util.CommonUtils;
import com.viviant.earngold.widget.banner.CycleViewPager;
import com.viviant.earngold.widget.customtoast.CustomToast;
import com.viviant.earngold.widget.recyclerView.rvhelper.base.ViewHolder;
import com.viviant.earngold.widget.recyclerView.rvhelper.divider.RecycleViewDivider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;

/**
 * Created by weiwei.huang on 2017/8/9.
 */

public class ShopsMainFragment extends BaseFragment<BmobTestPresenter> implements BmobTestContract.View, SwipeRefreshLayout.OnRefreshListener{

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
//    @Bind(R.id.fab)
//    FloatingActionButton mFab;
//    @Bind(R.id.get_bt)
//    Button mButtonGet;
//    @Bind(R.id.add_bt)
//    Button mButtonAdd;
//    @Bind(R.id.delete_bt)
//    Button mButtonDelete;
//    @Bind(R.id.update_bt)
//    Button mButtonUpdate;
//    @Bind(R.id.upload_bt)
//    Button mButtonUpload;

    @Bind(R.id.rv_main)
    RecyclerView mRecyclerViewMain;
    private MainRecycleviewAdapter mMainRecycleviewAdapter;



    @Override
    protected int getLayoutResource() {
        return R.layout.app_bar_bmobtest;
    }

    @Override
    protected void initView() {

        initAdapter();
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);

        mToolbar.setNavigationIcon(null);

        setHasOptionsMenu(true);
        CommonUtils.setViewFixStatusHeight(mToolbar, getContext());
        setActions();

    }

    private void setActions() {
//        mButtonGet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mPresenter.getOnePerson();
//            }
//        });
//
//        mButtonAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mPresenter.addOnePerson();
//            }
//        });
//
//        mButtonDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mPresenter.deleteOnePerson();
//            }
//        });
//
//        mButtonUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mPresenter.updateOnePerson();
//            }
//        });
//
//        mButtonUpload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mPresenter.uploadFile();
//            }
//        });
    }

    private void initAdapter() {

        mMainRecycleviewAdapter = new MainRecycleviewAdapter(getContext());
        mRecyclerViewMain.setAdapter(mMainRecycleviewAdapter);
        //这里不用自定义的流式布局也是可以的，这里这是根据特定需要简单自定义了一个
        mRecyclerViewMain.setLayoutManager(new LinearLayoutManager(getContext()));





    }


    @Override
    protected void fetchData() {

        //banner数据
        mPresenter.getBannerList();
        //获得分类数据源
        getCategoryData();
        //获取主内容的数据源
        getContentData();
    }
    //---
    private void getCategoryData() {
        List<TestBean> testBeanList = new ArrayList<>();
        for (int i = 0 ; i < 7 ; i++) {
            TestBean testBean = new TestBean();
            testBean.setImg(R.drawable.error_pic);
            testBean.setText("AAAA");
            testBeanList.add(testBean);
        }

        mMainRecycleviewAdapter.setCategoryList(testBeanList);
    }

    private void getContentData() {
        List<TestBean> testBeanList = new ArrayList<>();
        for (int i = 0 ; i < 8 ; i++) {
            TestBean testBean = new TestBean();
            testBean.setImg(R.drawable.error_pic);
            testBean.setText("BBBB");
            testBeanList.add(testBean);
        }
        mMainRecycleviewAdapter.setContentList(testBeanList);
    }
    //---

    @Override
    protected BmobTestPresenter onCreatePresenter() {
        return new BmobTestPresenter(this);
    }


    @Override
    public void onSucceed(String msg) {
        CustomToast.makeTextSuccessShort(getContext(), msg);
    }

    @Override
    public void onFail(String err) {
        CustomToast.makeTextErrorShort(getContext(), err);
    }

    @Override
    public void onLoad() {
    }

    @Override
    public void onLoaded() {
    }

    @Override
    public void onRefresh() {
    }

    @Override
    public void scrolltoTop() {
    }


    @Override
    public String getUsername() {
        return "username";
    }

    @Override
    public String getUid() {
        return "011b886c1a";
    }

    @Override
    public String getUpdateUsername() {
        return "UpdateUsername";
    }

    @Override
    public String getUpdateAddress() {
        return "Updateaddress";
    }



    @Override
    public String getAddress() {
        return "address";
    }

    @Override
    public void onLoadBannerList(List<Banner> bannerList) {
        mMainRecycleviewAdapter.setBannerList(bannerList);
    }
}