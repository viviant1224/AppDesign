package com.viviant.earngold.ui.fragment;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.viviant.earngold.R;
import com.viviant.earngold.api.RequestCallback;
import com.viviant.earngold.base.BaseFragment;
import com.viviant.earngold.bean.NbaVideoId;
import com.viviant.earngold.bean.NewsItem;
import com.viviant.earngold.mvp.video.contract.VideoContract;
import com.viviant.earngold.mvp.video.presenter.VideoPresenter;
import com.viviant.earngold.ui.adapter.VideoAdapter;
import com.viviant.earngold.widget.LoadMoreOnScrollListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by weiwei.huang on 2017/8/14.
 */

public class VideoFragment extends BaseFragment<VideoPresenter> implements VideoContract.View, SwipeRefreshLayout.OnRefreshListener{

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.srl)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private VideoAdapter mAdapter;
//    private List<NbaVideoId> nbaVideos;
    private LinearLayoutManager mLayoutManager;
//    private int page = 0;
    private int loadStatus = 0;
    private String column = "videos";

//    private static final String NEWS_NBA_ID = "T1348649145984";
//    private static final String NEWS_NBA_TYPE = "list";

    private List<String> indexs = new ArrayList<>();
    private int start = 0; // 查询数据起始位置
    private int num = 10;
    private List<NewsItem.NewsItemBean> list = new ArrayList<>();

    @Override
    protected int getLayoutResource() {
        return R.layout.frament_rv;
    }

    @Override
    protected VideoPresenter onCreatePresenter() {
        return new VideoPresenter(this);
    }

    @Override
    protected void initView() {
        initSwipeRefreshLayout();
        initAdapter();
        setLoadMoreListener();
    }

    private void initAdapter() {
        mLayoutManager=new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
//        nbaVideos = new ArrayList<>();

        if(mAdapter == null){
            mAdapter = new VideoAdapter(getContext(), list);
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

    @Override
    protected void fetchData() {
        mPresenter.getNbaVideoId(column, false);
    }

    private void setLoadMoreListener(){
        mRecyclerView.addOnScrollListener(new LoadMoreOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore() {
//                page++;
//                LogUtils.i("load more: start=" + start);
                String arcIds = parseIds();
                if (!TextUtils.isEmpty(arcIds)) {
                    requestNbaVideos(arcIds, false, true);
                } else {
//                    ToastUtils.showToast("已经到底啦");
                    complete();
                }


//                mPresenter.getNbaVideoId(column, false);
                loadStatus = 1;
            }
        });
    }

    @Override
    public void onSucceed(List<NbaVideoId> data, boolean isRefresh) {

        indexs.clear();
        start = 0;
        for (NbaVideoId bean : data) {
            indexs.add(bean.getId());
        }
        String arcIds = parseIds();
        requestNbaVideos(arcIds, isRefresh, false);



//        if (loadStatus == 0) {
//            nbaVideos.clear();
//        }
//        nbaVideos.addAll(data);
//        mAdapter.notifyDataSetChanged();
//        mSwipeRefreshLayout.setRefreshing(false);
    }


    private String parseIds() {
        int size = indexs.size();
        String articleIds = "";
        for (int i = start, j = 0; i < size && j < num; i++, j++, start++) {
            articleIds += indexs.get(i) + ",";
        }
        if (!TextUtils.isEmpty(articleIds))
            articleIds = articleIds.substring(0, articleIds.length() - 1);
//        LogUtils.i("articleIds = " + articleIds);
        return articleIds;
    }

    private void requestNbaVideos(String arcIds, final boolean isRefresh, final boolean isLoadMore) {

//        mPresenter.getNbaVideo();
        mPresenter.getNbaVideo("videos", arcIds, isRefresh);

    }

    @Override
    public void onGetVideoSuccess(NewsItem newsItem, boolean isRefresh) {
        Log.d("weiwei", "---" + newsItem.data.size());
        if (isRefresh) {
            list.clear();
        }
        list.addAll(newsItem.data);
        complete();
    }

    @Override
    public void onGetVideoFail(String error) {
        complete();
    }

    private void complete() {
//        recyclerView.setEmptyView(emptyView);
        mAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
//        materialRefreshLayout.finishRefresh();
//        materialRefreshLayout.finishRefreshLoadMore();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                hideLoadingDialog();
//            }
//        }, 1000);
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
//        page = 1;
        mPresenter.getNbaVideoId(column, true);
        mSwipeRefreshLayout.setRefreshing(true);
        loadStatus = 0;
    }

    @Override
    public void scrolltoTop() {
        mRecyclerView.smoothScrollToPosition(0);
    }
}
