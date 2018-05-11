package com.viviant.earngold.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.viviant.earngold.R;
import com.viviant.earngold.api.RequestCallback;
import com.viviant.earngold.bean.NewsItem;
import com.viviant.earngold.bean.VideoInfo;
import com.viviant.earngold.mvp.video.model.VideoModel;
import com.viviant.earngold.util.DimenUtils;

import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by weiwei.huang on 2017/8/14.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> implements View.OnClickListener{

    private Context mContext;
    private List<NewsItem.NewsItemBean> nbaVideos;

    public VideoAdapter(Context context, List<NewsItem.NewsItemBean> datas) {
        this.mContext = context;
        this.nbaVideos = datas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(
                mContext).inflate(R.layout.item_video, parent,
                false);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(this);
        return holder;

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder,final int position) {
        // 近期腾讯视频真实地址解析后播放 提示“您未获授权，无法查看此网页。 HTTP403” 故同时支持跳转到网页播放
//        holder.videoPlayer.setUp("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4"
//                , JZVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "嫂子闭眼睛");
////        holder.videoPlayer.thumbImageView.("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");
//        Glide.with(mContext).load("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640").centerCrop().placeholder(R.drawable.error_pic).crossFade().into( holder.videoPlayer.thumbImageView);

        holder.videoPlayer.setUp("", JZVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, nbaVideos.get(position).title);
        if (TextUtils.isEmpty(nbaVideos.get(position).realUrl)) {

            VideoModel.getVideoRealUrls(nbaVideos.get(position).vid, new RequestCallback<VideoInfo>() {
                @Override
                public void onSuccess(VideoInfo real) {
                    if (real.vl.vi != null && real.vl.vi.size() > 0) {
                        String vid = real.vl.vi.get(0).vid;
                        String vkey = real.vl.vi.get(0).fvkey;
                        String url = real.vl.vi.get(0).ul.ui.get(0).url + vid + ".mp4?vkey=" + vkey;
                        nbaVideos.get(position).realUrl = url;
//                        LogUtils.i("title：" + item.title);
//                        LogUtils.i("real-url：" + url);
                        holder.videoPlayer.setUp(url, JZVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, nbaVideos.get(position).title);
                    }




//                    if (real.vl.vi != null && real.vl.vi.size() > 0) {
//                        String vid = real.vl.vi.get(0).vid;
//                        String vkey = real.vl.vi.get(0).fvkey;
//                        String url = real.vl.vi.get(0).ul.ui.get(0).url + vid + ".mp4?vkey=" + vkey;
//                        item.realUrl = url;
//                        LogUtils.i("title：" + item.title);
//                        LogUtils.i("real-url：" + url);
//                        videoPlayer.setUp(url, item.title);
//                    }
                }

                @Override
                public void onFailure(String message) {
                }
            });
        } else {
            holder.videoPlayer.setUp(nbaVideos.get(position).realUrl, JZVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, nbaVideos.get(position).title);
        }

//        videoPlayer.thumbImageView.setController(FrescoUtils.getController(nbaVideos.get(position).imgurl, videoPlayer.thumbImageView));
        Glide.with(mContext).load(nbaVideos.get(position).imgurl).centerCrop().placeholder(R.drawable.error_pic).crossFade().into( holder.videoPlayer.thumbImageView);
//        holder.tv.setText(nbaVideos.get(position).title);
        ViewGroup.LayoutParams params = holder.videoPlayer.getLayoutParams();
        params.height = DimenUtils.getScreenWidth() / 2;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        holder.videoPlayer.setLayoutParams(params);










//        holder.tv.setText(nbaVideos.get(position).getId());
//        Glide.with(mContext).load(meiziList.get(position).getUrl()).centerCrop().placeholder(R.drawable.error_pic).crossFade().into(holder.iv);
    }

    @Override
    public int getItemCount()
    {
        return nbaVideos.size();
    }


    @Override
    public void onClick(View v) {

//        int position=recyclerview.getChildAdapterPosition(v);
//        SnackbarUtil.ShortSnackbar(coordinatorLayout,"点击第"+position+"个",SnackbarUtil.Info).show();
    }


    class MyViewHolder extends RecyclerView.ViewHolder
    {
//        private ImageView iv;
        private JZVideoPlayerStandard videoPlayer;
        private TextView tv;
        public MyViewHolder(View view)
        {
            super(view);

            videoPlayer = (JZVideoPlayerStandard)view.findViewById(R.id.videoplayer);
//            iv = (ImageView) view.findViewById(R.id.line_item_iv);
//            tv=(TextView) view.findViewById(R.id.tvVideoTitle);
        }
    }
}
