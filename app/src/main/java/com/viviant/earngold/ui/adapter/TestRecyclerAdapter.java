package com.viviant.earngold.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.viviant.earngold.R;
import com.viviant.earngold.bean.Meizi;

import java.util.List;

/**
 * Created by weiwei.huang on 2017/8/14.
 */

public class TestRecyclerAdapter extends RecyclerView.Adapter<TestRecyclerAdapter.MyViewHolder> implements View.OnClickListener{

    private Context mContext;
    private List<Meizi> meiziList;

    public TestRecyclerAdapter(Context context, List<Meizi> meizis) {
        this.mContext = context;
        this.meiziList = meizis;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(
                mContext).inflate(R.layout.line_meizi_item, parent,
                false);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(this);
        return holder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv.setText("图"+position);
        Glide.with(mContext).load(meiziList.get(position).getUrl()).centerCrop().placeholder(R.drawable.error_pic).crossFade().into(holder.iv);
    }

    @Override
    public int getItemCount()
    {
        return meiziList.size();
    }


    @Override
    public void onClick(View v) {

//        int position=recyclerview.getChildAdapterPosition(v);
//        SnackbarUtil.ShortSnackbar(coordinatorLayout,"点击第"+position+"个",SnackbarUtil.Info).show();
    }


    class MyViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView iv;
        private TextView tv;
        public MyViewHolder(View view)
        {
            super(view);
            iv = (ImageView) view.findViewById(R.id.line_item_iv);
            tv=(TextView) view.findViewById(R.id.line_item_tv);
        }
    }
}
