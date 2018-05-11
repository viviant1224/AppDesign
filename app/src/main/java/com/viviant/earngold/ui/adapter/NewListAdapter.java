package com.viviant.earngold.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.viviant.earngold.MyApplication;
import com.viviant.earngold.R;
import com.viviant.earngold.bean.NewsSummary;
import com.viviant.earngold.util.DisplayUtil;
import com.viviant.earngold.widget.recyclerView.rvhelper.adapter.MultiItemTypeAdapter;
import com.viviant.earngold.widget.recyclerView.rvhelper.base.ViewHolder;

import java.util.List;

/**
 * des:新闻列表适配器
 * Created by xsf
 * on 2016.09.10:49
 */
public class NewListAdapter extends MultiItemTypeAdapter<NewsSummary>
{
    private Context mContext;
    protected LayoutInflater mInflater;

    public static final int TYPE_ITEM = 0;
    public static final int TYPE_PHOTO_ITEM =1;
    private List<NewsSummary> mNewsSummaries;

    @Override
    public int getItemViewType(int position) {
        if (!TextUtils.isEmpty(mNewsSummaries.get(position).getDigest())) {
            return TYPE_ITEM;
        } else {
            return TYPE_PHOTO_ITEM;
        }
    }

    public NewListAdapter(Context context, List<NewsSummary> newsSummaries) {
        super(context, newsSummaries);
        this.mContext = context;
        this.mNewsSummaries = newsSummaries;

        mInflater = LayoutInflater.from(context);

//        addItemViewDelegate(new ItemViewDelegate<NewsSummary>() {
//            @Override
//            public int getItemViewLayoutId() {
////                switch (viewType) {
////                    case TYPE_ITEM:
////                        return R.layout.item_news;
////                    case TYPE_PHOTO_ITEM:
////                        return R.layout.item_news_photo;
////                    default:
////                        return R.layout.item_news;
////                }
//            }
//
//            @Override
//            public boolean isForViewType(NewsSummary item, int position) {
//                return true;
//            }
//
//            @Override
//            public void convert(ViewHolder holder, NewsSummary t, int position) {
//                NewListAdapter.this.convert(holder, t);
//            }
//        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        int layoutId = R.layout.item_news;

        switch (viewType) {
            case TYPE_ITEM:
                layoutId = R.layout.item_news;
                break;
            case TYPE_PHOTO_ITEM:
                layoutId = R.layout.item_news_photo;
                break;
            default:
                layoutId = R.layout.item_news;
                break;
        }
        ViewHolder holder = ViewHolder.createViewHolder(mContext, parent, layoutId);
        setListener(parent, holder, viewType);
        return holder;
    }


    @Override
    public void convert(ViewHolder holder, final NewsSummary newsSummary) {
        switch (holder.getItemViewType()) {
            case TYPE_ITEM:
                setItemValues(holder, newsSummary);
                break;
            case TYPE_PHOTO_ITEM:
                setPhotoItemValues(holder,newsSummary);
                break;
        }
    }


    /**
     * 普通样式
     * @param holder
     * @param newsSummary
     */
    private void setItemValues(final ViewHolder holder, final NewsSummary newsSummary) {
        String title = newsSummary.getLtitle();
        if (title == null) {
            title = newsSummary.getTitle();
        }
        String ptime = newsSummary.getPtime();
        String digest = newsSummary.getDigest();
        String imgSrc = newsSummary.getImgsrc();

        holder.setText(R.id.news_summary_title_tv,title);
        holder.setText(R.id.news_summary_ptime_tv,ptime);
        holder.setText(R.id.news_summary_digest_tv,digest);
        holder.setImageUrl(R.id.news_summary_photo_iv,imgSrc);
        holder.setOnClickListener(R.id.rl_root, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                NewsDetailActivity.startAction(mContext,holder.getView(R.id.news_summary_photo_iv),newsSummary.getPostid(),newsSummary.getImgsrc());
            }
        });
    }


    /**
     * 图文样式
     * @param holder
     */
    private void setPhotoItemValues(ViewHolder holder, final NewsSummary newsSummary) {
        String title = newsSummary.getTitle();
        String ptime = newsSummary.getPtime();
        holder.setText(R.id.news_summary_title_tv,title);
        holder.setText(R.id.news_summary_ptime_tv,ptime);
        setImageView(holder, newsSummary);
        holder.setOnClickListener(R.id.ll_root, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                NewsPhotoDetailActivity.startAction(mContext,getPhotoDetail(newsSummary));
            }
        });
    }

    private void setImageView(ViewHolder holder, NewsSummary newsSummary) {
        int PhotoThreeHeight = (int) DisplayUtil.dip2px(90);
        int PhotoTwoHeight = (int) DisplayUtil.dip2px(120);
        int PhotoOneHeight = (int)DisplayUtil.dip2px(150);

        String imgSrcLeft = null;
        String imgSrcMiddle = null;
        String imgSrcRight = null;
        LinearLayout news_summary_photo_iv_group=holder.getView(R.id.news_summary_photo_iv_group);
        ViewGroup.LayoutParams layoutParams = news_summary_photo_iv_group.getLayoutParams();

        if (newsSummary.getAds() != null) {
            List<NewsSummary.AdsBean> adsBeanList = newsSummary.getAds();
            int size = adsBeanList.size();
            if (size >= 3) {
                imgSrcLeft = adsBeanList.get(0).getImgsrc();
                imgSrcMiddle = adsBeanList.get(1).getImgsrc();
                imgSrcRight = adsBeanList.get(2).getImgsrc();
                layoutParams.height = PhotoThreeHeight;
                holder.setText(R.id.news_summary_title_tv, MyApplication.getContext()
                        .getString(R.string.photo_collections, adsBeanList.get(0).getTitle()));
            } else if (size >= 2) {
                imgSrcLeft = adsBeanList.get(0).getImgsrc();
                imgSrcMiddle = adsBeanList.get(1).getImgsrc();

                layoutParams.height = PhotoTwoHeight;
            } else if (size >= 1) {
                imgSrcLeft = adsBeanList.get(0).getImgsrc();

                layoutParams.height = PhotoOneHeight;
            }
        } else if (newsSummary.getImgextra() != null) {
            int size = newsSummary.getImgextra().size();
            if (size >= 3) {
                imgSrcLeft = newsSummary.getImgextra().get(0).getImgsrc();
                imgSrcMiddle = newsSummary.getImgextra().get(1).getImgsrc();
                imgSrcRight = newsSummary.getImgextra().get(2).getImgsrc();

                layoutParams.height = PhotoThreeHeight;
            } else if (size >= 2) {
                imgSrcLeft = newsSummary.getImgextra().get(0).getImgsrc();
                imgSrcMiddle = newsSummary.getImgextra().get(1).getImgsrc();

                layoutParams.height = PhotoTwoHeight;
            } else if (size >= 1) {
                imgSrcLeft = newsSummary.getImgextra().get(0).getImgsrc();

                layoutParams.height = PhotoOneHeight;
            }
        } else {
            imgSrcLeft = newsSummary.getImgsrc();

            layoutParams.height = PhotoOneHeight;
        }

        setPhotoImageView(holder, imgSrcLeft, imgSrcMiddle, imgSrcRight);
        news_summary_photo_iv_group.setLayoutParams(layoutParams);
    }

    private void setPhotoImageView(ViewHolder holder, String imgSrcLeft, String imgSrcMiddle, String imgSrcRight) {
        if (imgSrcLeft != null) {
            holder.setVisible(R.id.news_summary_photo_iv_left,true);
            holder.setImageUrl(R.id.news_summary_photo_iv_left,imgSrcLeft);
        } else {
            holder.setVisible(R.id.news_summary_photo_iv_left,false);
        }
        if (imgSrcMiddle != null) {
            holder.setVisible(R.id.news_summary_photo_iv_middle,true);
            holder.setImageUrl(R.id.news_summary_photo_iv_middle,imgSrcMiddle);
        } else {
            holder.setVisible(R.id.news_summary_photo_iv_middle,false);
        }
        if (imgSrcRight != null) {
            holder.setVisible(R.id.news_summary_photo_iv_right,true);
            holder.setImageUrl(R.id.news_summary_photo_iv_right,imgSrcRight);
        } else {
            holder.setVisible(R.id.news_summary_photo_iv_right,false);
        }
    }


}
