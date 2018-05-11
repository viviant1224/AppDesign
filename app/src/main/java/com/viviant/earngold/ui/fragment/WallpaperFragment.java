package com.viviant.earngold.ui.fragment;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.viviant.earngold.R;
import com.viviant.earngold.base.BaseFragment;
import com.viviant.earngold.bean.Meizi;
import com.viviant.earngold.mvp.wallpaper.contract.WallpaperContract;
import com.viviant.earngold.mvp.wallpaper.presenter.WallpaperPresenter;
import com.viviant.earngold.ui.adapter.MeiZiGridAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by weiwei.huang on 2017/8/14.
 */

public class WallpaperFragment extends BaseFragment<WallpaperPresenter> implements WallpaperContract.View, SwipeRefreshLayout.OnRefreshListener{

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.srl)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private MeiZiGridAdapter mAdapter;
    private List<Meizi> meizis;
    private StaggeredGridLayoutManager mLayoutManager;
    private int page = 1;
    private int loadStatus = 0;

    private int lastVisibleItem;

    @Override
    protected int getLayoutResource() {
        return R.layout.frament_wallpaper;
    }

    @Override
    protected WallpaperPresenter onCreatePresenter() {
        return new WallpaperPresenter(this);
    }

    @Override
    protected void initView() {
        initSwipeRefreshLayout();
        initAdapter();
        setLoadMoreListener();
    }

    private void initAdapter() {

        mLayoutManager=new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        meizis = new ArrayList<>();
        if(mAdapter == null){
            mAdapter = new MeiZiGridAdapter(getContext());
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private void initSwipeRefreshLayout() {
        // 刷新监听事件，必须有
        mSwipeRefreshLayout.setOnRefreshListener(this);
        // 设置进度圈的背景色
        // swipeRefreshLayout.setProgressBackgroundColor(R.color.colorAccent);
        // 设置进度动画的颜色,可以使用多种颜色
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
    }

    private void setLoadMoreListener(){
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                0：当前屏幕停止滚动；1时：屏幕在滚动 且 用户仍在触碰或手指还在屏幕上；2时：随用户的操作，屏幕上产生的惯性滑动；
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem +2>=mLayoutManager.getItemCount()) {
                    page++;
                    mPresenter.getMeiziList(page);
                    loadStatus = 1;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int[] positions= mLayoutManager.findLastVisibleItemPositions(null);
                lastVisibleItem = Math.max(positions[0],positions[1]);
            }
        });
    }

    @Override
    protected void fetchData() {
        mPresenter.getMeiziList(page);
    }

    @Override
    public void onSucceed(List<Meizi> data) {
        if (loadStatus == 0) {
            meizis.clear();
        }
//        meizis.addAll(data);
//        mAdapter.notifyDataSetChanged();
        mAdapter.addDataAll(data);
        mAdapter.notifyDataSetChanged();

        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onFail(String err) {

    }

    @Override
    public void onLoad() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onLoaded() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        page = 1;
        mPresenter.getMeiziList(page);
        mSwipeRefreshLayout.setRefreshing(true);
        loadStatus = 0;
    }

    @Override
    public void scrolltoTop() {
        mRecyclerView.smoothScrollToPosition(0);
    }


    @Override
    public void onResume() {
        super.onResume();
    }
}
