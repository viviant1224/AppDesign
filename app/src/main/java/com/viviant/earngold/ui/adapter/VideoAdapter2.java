//package com.viviant.earngold.ui.adapter;
//
//import android.content.Context;
//import android.widget.TextView;
//
//import com.viviant.earngold.R;
//import com.viviant.earngold.bean.NewsSummary;
//import com.viviant.earngold.widget.recyclerView.rvhelper.adapter.CommonAdapter;
//import com.viviant.earngold.widget.recyclerView.rvhelper.base.ViewHolder;
//
//public  class VideoAdapter extends CommonAdapter<NewsSummary> /*implements View.OnClickListener, View.OnLongClickListener*/{
//
//    private Context mContext;
//    public VideoAdapter(Context context) {
//        super(context, R.layout.item_video);
//        this.mContext = context;
//    }
//
//
//    @Override
//    protected void convert(ViewHolder holder, final NewsSummary item, int position) {
//
//        ((TextView)holder.getView(R.id.tvVideoTitle)).setText(item.getTitle());
////        Glide.with(mContext).load(item.).fitCenter().crossFade().into((ImageView) holder.getView(R.id.iv));
//    }
//
//
//}
