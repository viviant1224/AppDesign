package com.viviant.earngold.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.viviant.earngold.R;
import com.viviant.earngold.bean.Meizi;
import com.viviant.earngold.widget.recyclerView.rvhelper.adapter.CommonAdapter;
import com.viviant.earngold.widget.recyclerView.rvhelper.base.ViewHolder;

public  class MeiZiGridAdapter extends CommonAdapter<Meizi> /*implements View.OnClickListener, View.OnLongClickListener*/{

    private Context mContext;
    public MeiZiGridAdapter(Context context) {
        super(context, R.layout.grid_meizi_item);
        this.mContext = context;
    }


    @Override
    protected void convert(ViewHolder holder, final Meizi item, int position) {
        Glide.with(mContext).load(item.getUrl()).fitCenter().crossFade().into((ImageView) holder.getView(R.id.iv));
    }

//    public static interface OnRecyclerViewItemClickListener {
//        void onItemClick(View view);
//        void onItemLongClick(View view);
//    }
////
//    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
////
//    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
//        mOnItemClickListener = listener;
//    }
//
//
////    public MeiZiGridAdapter(Context context, List<Meizi> datas) {
////        mContext=context;
////        this.datas=datas;
////    }
////
////    @Override
////    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
////    {
////        if(viewType==0){
////            View view = LayoutInflater.from(mContext
////                    ).inflate(R.layout.grid_meizi_item, parent,
////                    false);
////            MyViewHolder holder = new MyViewHolder(view);
////
////            view.setOnClickListener(this);
////            view.setOnLongClickListener(this);
////
////            return holder;
////        }else{
////            MyViewHolder2 holder2=new MyViewHolder2(LayoutInflater.from(
////                    mContext).inflate(R.layout.page_item, parent,
////                    false));
////            return holder2;
////        }
////
////    }
////
////    @Override
////    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
////        if(holder instanceof MyViewHolder){
////            Glide.with(mContext).load(datas.get(position).getUrl())/*.centerCrop()*/.fitCenter()/*.placeholder(R.drawable.error_pic)*/.crossFade().into(((MyViewHolder) holder).iv);
////        }else if(holder instanceof MyViewHolder2){
////            ((MyViewHolder2) holder).tv.setText(datas.get(position).getPage()+"页");
////        }
////
////    }
//
//    @Override
//    public int getItemCount()
//    {
//        return datas.size();
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//
//        //判断item是图还是显示页数（图片有URL）
//        if (!TextUtils.isEmpty(datas.get(position).getUrl())) {
//            return 0;
//        } else {
//            return 1;
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//        if (mOnItemClickListener != null) {
//            mOnItemClickListener.onItemClick(v);
//        }
//
//    }
//
//
//    @Override
//    public boolean onLongClick(View v) {
//        if (mOnItemClickListener != null) {
//            mOnItemClickListener.onItemLongClick(v);
//        }
//        return false;
//    }
//
//    class MyViewHolder extends RecyclerView.ViewHolder
//    {
//        private ImageButton iv;
//
//        public MyViewHolder(View view)
//        {
//            super(view);
//            iv = (ImageButton) view.findViewById(R.id.iv);
//        }
//    }
//    class MyViewHolder2 extends RecyclerView.ViewHolder
//    {
//        private TextView tv;
//
//        public MyViewHolder2(View view)
//        {
//            super(view);
//            tv = (TextView) view.findViewById(R.id.tv);
//        }
//    }

}
