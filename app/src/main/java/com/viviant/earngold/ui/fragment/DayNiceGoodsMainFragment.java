package com.viviant.earngold.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

import com.viviant.earngold.R;
import com.viviant.earngold.base.BaseFragment;
import com.viviant.earngold.bean.ArticleBean;
import com.viviant.earngold.mvp.daynicegoods.contract.DayNiceGoodsMainContract;
import com.viviant.earngold.mvp.daynicegoods.presenter.DayNiceGoodsMainPresenter;
import com.viviant.earngold.util.CommonUtils;
import com.viviant.earngold.util.TextFontUtil;
import com.viviant.earngold.widget.customtoast.CustomToast;


import butterknife.Bind;

/**
 * Created by weiwei.huang on 2017/8/9.
 */

public class DayNiceGoodsMainFragment extends BaseFragment<DayNiceGoodsMainPresenter> implements DayNiceGoodsMainContract.View, SwipeRefreshLayout.OnRefreshListener{

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.article_content)
    TextView mArticleContent;

    @Bind(R.id.article_title)
    TextView mArticleTitle;

    @Bind(R.id.article_author)
    TextView mArticleAuthor;




    @Override
    protected int getLayoutResource() {
        return R.layout.app_bar_daynicegoods;
    }

    @Override
    protected void initView() {
        initAdapter();
        mToolbar.setTitle("BmobTest");
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);


        mToolbar.inflateMenu(R.menu.main);
        setHasOptionsMenu(true);
        CommonUtils.setViewFixStatusHeight(mToolbar, getContext());
    }

    private void initAdapter() {
    }


    @Override
    protected void fetchData() {
//        mPresenter.getMeiziList(page);
        mPresenter.getDayArticle();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    protected DayNiceGoodsMainPresenter onCreatePresenter() {
        return new DayNiceGoodsMainPresenter(this);
    }


    @Override
    public void onSucceed(ArticleBean articleBean) {
        mArticleContent.setText(Html.fromHtml(articleBean.getContent()));
        mArticleContent.setTypeface(TextFontUtil.getTypeface(getContext()));

        mArticleTitle.setText(articleBean.getTitle());
        mArticleAuthor.setText(articleBean.getAuthor());
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
    public String getAddress() {
        return "address";
    }
}