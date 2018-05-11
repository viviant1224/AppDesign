package com.viviant.earngold.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.viviant.earngold.R;
import com.viviant.earngold.bean.TestBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 赵晨璞 on 2016/6/19.
 *
 */

public  class GridTestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    private Context mContext;
    private List<TestBean> datas;

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view);
        void onItemLongClick(View view);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }


    public GridTestAdapter(Context context, List<TestBean> datas) {
        mContext =context;
        this.datas=datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
//        if(viewType==0){
            View view = LayoutInflater.from(mContext
                    ).inflate(R.layout.gridview_test, parent,
                    false);
            MyViewHolder holder = new MyViewHolder(view);

            view.setOnClickListener(this);
            view.setOnLongClickListener(this);

            return holder;
//        }else{
//            MyViewHolder2 holder2=new MyViewHolder2(LayoutInflater.from(
//                    mContext).inflate(R.layout.page_item, parent,
//                    false));
//            return holder2;
//        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Glide.with(mContext).load(datas.get(position).getImg())/*.centerCrop()*/.fitCenter()/*.placeholder(R.drawable.error_pic)*/.crossFade().into(((MyViewHolder) holder).iv);


    }

    @Override
    public int getItemCount()
    {
        return datas.size();
    }

    @Override
    public int getItemViewType(int position) {

        //判断item是图还是显示页数（图片有URL）
//        if (!TextUtils.isEmpty(datas.get(position).getUrl())) {
            return 0;
//        } else {
//            return 1;
//        }
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {

            mOnItemClickListener.onItemClick(v);
        }

    }


    @Override
    public boolean onLongClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemLongClick(v);
        }
        return false;
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView iv;

        public MyViewHolder(View view)
        {
            super(view);
            iv = (ImageView) view.findViewById(R.id.iv);
        }
    }

}
