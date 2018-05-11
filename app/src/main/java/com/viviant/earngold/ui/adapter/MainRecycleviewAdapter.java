package com.viviant.earngold.ui.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.viviant.earngold.R;
import com.viviant.earngold.bean.Banner;
import com.viviant.earngold.bean.TestBean;
import com.viviant.earngold.widget.banner.CycleViewPager;
import com.viviant.earngold.widget.recyclerView.rvhelper.divider.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public  class MainRecycleviewAdapter extends RecyclerView.Adapter implements CycleViewPager.ImageCycleViewListener {

    private Context mContext;
    private int count = 3;

    private int TYPE_BANNER = 1;//头部布局
    private int TYPE_CATEGORY = 2;//类别
    private int TYPE_CONTENT = 3;//内容

    private LayoutInflater inflater;

    private List<Banner> mBannerList;
    private List<TestBean> mCategoryList;
    private List<TestBean> mContentList;

    private TestItemAdapter mTestItemAdapter;


    public MainRecycleviewAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        //初始化各我数据源
        mBannerList = new ArrayList<>();
        mCategoryList = new ArrayList<>();
        mContentList = new ArrayList<>();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_BANNER) {
//            //头部轮播图
            View viewtop = inflater.inflate(R.layout.main_banner, parent, false);
//            StaggeredGridLayoutManager.LayoutParams params =
//                    (StaggeredGridLayoutManager.LayoutParams) viewtop.getLayoutParams();
//            params.setFullSpan(true);//最为重要的一个方法，占满全屏,以下同理
//            viewtop.setLayoutParams(params);
            return new TypeBannerHolder(viewtop);
        } else if (viewType == TYPE_CATEGORY) {
//
            View view2 = inflater.inflate(R.layout.main_category_rv, parent, false);

//            StaggeredGridLayoutManager.LayoutParams params =
//                    (StaggeredGridLayoutManager.LayoutParams) view2.getLayoutParams();
//            params.setFullSpan(true);
//            view2.setLayoutParams(params);
            return new TypeCategoryHolder(view2);
        }  else {
            //四个快速入口的holder
            //这里的TypetypeHolder和上面的TypetypeHolder2 其实可以写成一个holder，这里为了简单，避免引起复用带来的问题，分开了
            View view = inflater.inflate(R.layout.main_content_rv, parent, false);
//            StaggeredGridLayoutManager.LayoutParams params2 =
//                    (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
//            params2.setFullSpan(true);
//            view.setLayoutParams(params2);
            return new TypeContentHolder(view);

        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TypeBannerHolder && mBannerList.size() != 0) {
            //如果是TypeTopsliderHolder， 并且header有数据，并且TypeTopsliderHolder的linearLayout没有子view（因为这个布局只出现一次，如果他没有子view，
            // 也就是他是第一次加载，才加载数据）
            initBanner(((TypeBannerHolder) holder), mBannerList);//加载头部数据源
        } else if (holder instanceof TypeContentHolder && mContentList.size() != 0) {
            initContent(((TypeContentHolder) holder));
        } else if (holder instanceof TypeCategoryHolder) {
            initCategory(((TypeCategoryHolder) holder));
        }
    }

    @Override
    public int getItemCount() {
        return count;
    }


    @Override
    public int getItemViewType(int position) {
        //此处是根据数据源有无数据来判定分类条的位置；可自行拓展，自由发挥
        if(position == 0) {
            return TYPE_BANNER;
        } else if (position == 1) {
            return TYPE_CATEGORY;
        } else {
            return TYPE_CONTENT;
        }
    }


    private void initCategory(TypeCategoryHolder holder) {
        GridLayoutManager manager = new GridLayoutManager(mContext, 4);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

            @Override

            public int getSpanSize(int position) {
                if (position == 2) {
                    return 2;
                } else {
                    return 1;
                }
            }
        });
        holder.mCategoryRV.setLayoutManager(manager);
        holder.mCategoryRV.addItemDecoration(new SpaceItemDecoration(8));
        holder.mCategoryRV.setAdapter(new GridTestAdapter(mContext, mCategoryList));
    }

    private void initContent(TypeContentHolder holder) {
        mTestItemAdapter = new TestItemAdapter(mContext);
        holder.mContentRV.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL));
        holder.mContentRV.setLayoutManager(new LinearLayoutManager(mContext));
        holder.mContentRV.addItemDecoration(new RecycleViewDivider(mContext, LinearLayout.VERTICAL, 20, R.color.colorBG));
        holder.mContentRV.setAdapter(mTestItemAdapter);
        mTestItemAdapter.addDataAll(mContentList);
    }

    private void initBanner(TypeBannerHolder holder, List<Banner> data) {
        holder.mCycleViewPager.setIndicators(R.mipmap.ad_select, R.mipmap.ad_unselect);
        //设置轮播间隔时间
//        mCycleViewPager.setDelay(2000);
        holder.mCycleViewPager.setData(data, this);
    }


    //头部Viewpager viewholder
    public class TypeBannerHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.cycle_view)
        CycleViewPager mCycleViewPager;

        public TypeBannerHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public class TypeContentHolder extends RecyclerView.ViewHolder {


        @Bind(R.id.content_rv)
        RecyclerView mContentRV;

        public TypeContentHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class TypeCategoryHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.category_rv)
        RecyclerView mCategoryRV;

        public TypeCategoryHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public void setCategoryList(List<TestBean> datas) {
        mCategoryList = datas;
        notifyDataSetChanged();
    }

    public void setContentList(List<TestBean> datas) {
        mContentList = datas;
        notifyDataSetChanged();
    }

    public void setBannerList(List<Banner> datas) {

//        //        mList = bannerList;
//        //设置选中和未选中时的图片
//        mCycleViewPager.setIndicators(R.mipmap.ad_select, R.mipmap.ad_unselect);
//        //设置轮播间隔时间
////        mCycleViewPager.setDelay(2000);
//        mCycleViewPager.setData(bannerList, this);

        mBannerList = datas;
        notifyDataSetChanged();
    }


    @Override
    public void onImageClick(Banner banner, int position, View imageView) {

    }
}
