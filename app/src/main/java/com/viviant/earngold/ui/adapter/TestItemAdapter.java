package com.viviant.earngold.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.viviant.earngold.R;
import com.viviant.earngold.bean.TestBean;
import com.viviant.earngold.widget.recyclerView.rvhelper.adapter.CommonAdapter;
import com.viviant.earngold.widget.recyclerView.rvhelper.base.ViewHolder;

/**
 * Created by weiwei.huang on 2017/9/8.
 */

public class TestItemAdapter extends CommonAdapter<TestBean> {

    private Context mContext;
    public TestItemAdapter(Context context) {
        super(context, R.layout.item_main);
        this.mContext = context;
    }


    @Override
    protected void convert(ViewHolder holder, final TestBean item, int position) {
        Glide.with(mContext).load(item.getImg()).crossFade().into((ImageView) holder.getView(R.id.iv));
//        holder.setText(R.id.tv_wechat_item_title, item.getTitle())
//                .setText(R.id.tv_wechat_item_from, item.getDescription())
//                .setText(R.id.tv_wechat_item_time, item.getCtime())
//                .setOnClickListener(R.id.ll_click, v -> {
////                    WebviewDetailsActivity.start(mContext, item.getTitle(), item.getUrl());
//                });

    }
}
