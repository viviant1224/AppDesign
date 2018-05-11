//package com.viviant.earngold.ui.fragment;
//
//
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//
//import com.viviant.earngold.R;
//import com.viviant.earngold.base.BaseFragment;
//import com.viviant.earngold.bean.Meizi;
//import com.viviant.earngold.mvp.news.contract.SkillContract;
//import com.viviant.earngold.mvp.news.presenter.SkillPresenter;
//import com.viviant.earngold.ui.adapter.TestRecyclerAdapter;
//import com.viviant.earngold.widget.LoadMoreOnScrollListener;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.Bind;
//
///**
// * Created by weiwei.huang on 2017/8/14.
// */
//
//public class CPAHotFragment extends BaseFragment<SkillPresenter> implements SkillContract.View, SwipeRefreshLayout.OnRefreshListener{
//
//    @Bind(R.id.recyclerView)
//    RecyclerView mRecyclerView;
//    @Bind(R.id.srl)
//    SwipeRefreshLayout mSwipeRefreshLayout;
//
//    private TestRecyclerAdapter mAdapter;
//    private List<Meizi> meizis;
//    private LinearLayoutManager mLayoutManager;
//    private int page = 1;
//    private int loadStatus = 0;
//
//    @Override
//    protected int getLayoutResource() {
//        return R.layout.frament_rv;
//    }
//
//    @Override
//    protected SkillPresenter onCreatePresenter() {
//        return new SkillPresenter(this);
//    }
//
//    @Override
//    protected void initView() {
//        initSwipeRefreshLayout();
//        initAdapter();
//        setLoadMoreListener();
//    }
//
//    private void initAdapter() {
//        mLayoutManager=new LinearLayoutManager(getContext());
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        meizis = new ArrayList<>();
//
//        if(mAdapter == null){
//            mAdapter = new TestRecyclerAdapter(getContext(), meizis);
//            mRecyclerView.setAdapter(mAdapter);
//        } else {
//            mAdapter.notifyDataSetChanged();
//        }
//    }
//
//    private void initSwipeRefreshLayout() {
//        // 刷新监听事件，必须有
//        mSwipeRefreshLayout.setOnRefreshListener(this);
//        // 设置进度圈的背景色
//        // swipeRefreshLayout.setProgressBackgroundColor(R.color.colorAccent);
//        // 设置进度动画的颜色,可以使用多种颜色
//        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
//    }
//
//    @Override
//    protected void fetchData() {
//        mPresenter.getMeiziList(page);
//    }
//
//    private void setLoadMoreListener(){
//        mRecyclerView.addOnScrollListener(new LoadMoreOnScrollListener(mLayoutManager) {
//            @Override
//            public void onLoadMore() {
//                page++;
//                mPresenter.getMeiziList(page);
//                loadStatus = 1;
//            }
//        });
//    }
//
//    @Override
//    public void onSucceed(List<Meizi> data) {
//        if (loadStatus == 0) {
//            meizis.clear();
//        }
//        meizis.addAll(data);
//        mAdapter.notifyDataSetChanged();
//        mSwipeRefreshLayout.setRefreshing(false);
//    }
//
//    @Override
//    public void onFail(String err) {
//
//    }
//
//    @Override
//    public void onLoad() {
//        mSwipeRefreshLayout.setRefreshing(true);
//    }
//
//    @Override
//    public void onLoaded() {
//        mSwipeRefreshLayout.setRefreshing(false);
//    }
//
//    @Override
//    public void onRefresh() {
//        page = 1;
//        mPresenter.getMeiziList(page);
//        mSwipeRefreshLayout.setRefreshing(true);
//        loadStatus = 0;
//    }
//
//    @Override
//    public void scrolltoTop() {
//        mRecyclerView.smoothScrollToPosition(0);
//    }
//}
