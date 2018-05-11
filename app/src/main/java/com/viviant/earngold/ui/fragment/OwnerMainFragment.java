package com.viviant.earngold.ui.fragment;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.viviant.earngold.R;
import com.viviant.earngold.base.BaseFragment;
import com.viviant.earngold.base.BasePresenter;
import com.viviant.earngold.util.CommonUtils;


import butterknife.Bind;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by weiwei.huang on 2017/8/9.
 */

public class OwnerMainFragment extends BaseFragment{

    @Bind(R.id.coodinator_layout)
    CoordinatorLayout mCoordinatorLayout;

    @Bind(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;


    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.user_photo)
    ImageView mUserPhoto;
    @Bind(R.id.user_header_bg)
    ImageView mUserHeaderBG;
//    @Bind(R.id.fab)
//    FloatingActionButton mFab;

    @Override
    protected int getLayoutResource() {
        return R.layout.app_bar_self_media;
    }

    @Override
    protected void initView() {



        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);

        mToolbar.setTitle("");
        mToolbar.setNavigationIcon(null);
//        mToolbar.inflateMenu(R.menu.menu_search_view);


        CommonUtils.setViewFixStatusHeight(mToolbar, getContext());
        setHasOptionsMenu(true);


        Glide.with(
                getContext())
                .load("http://img0.imgtn.bdimg.com/it/u=1837350930,1729704606&fm=26&gp=0.jpg")
                .bitmapTransform(new CropCircleTransformation(getContext())).crossFade(1000)
                .into(mUserPhoto);

        Glide.with(
                getContext())
                .load("http://img0.imgtn.bdimg.com/it/u=1837350930,1729704606&fm=26&gp=0.jpg")
                .bitmapTransform(new BlurTransformation(getContext(), 15))
                .crossFade(1000).into(mUserHeaderBG);


//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("weiwei", "onclick");
////                finish();
//            }
//        });
    }


    @Override
    protected void fetchData() {

    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        menu.clear();
//        inflater.inflate(R.menu.menu_search_view, menu);
//    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }
}