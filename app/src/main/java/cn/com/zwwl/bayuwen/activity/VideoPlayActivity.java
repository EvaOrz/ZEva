package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.service.NewMusicService;
import cn.com.zwwl.bayuwen.widget.MyVideoView;
import cn.jzvd.JZVideoPlayer;

/**
 * 视频播放页面
 */
public class VideoPlayActivity extends BaseActivity {
    private MyVideoView myVideoView;
    private String videoUrl, picUrl;

    public interface OnBackButtonClickListener {
        public void onBackClick();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        sendintent(BaseActivity.ACTION_RESET);
        videoUrl = getIntent().getStringExtra("VideoPlayActivity_url");
//        videoUrl = "https://v-cdn-bb.bbwc
// .cn/bloomberg/2017/02/07/20170207162511930/20170207162511930_index.m3u8";
        picUrl = getIntent().getStringExtra("VideoPlayActivity_pic");
        initView();
    }

    private void initView() {
        myVideoView = findViewById(R.id.video_view);
        myVideoView.setUp(videoUrl, JZVideoPlayer.SCREEN_WINDOW_FULLSCREEN, new
                OnBackButtonClickListener() {
                    @Override
                    public void onBackClick() {
                        finish();
                    }
                });
        ImageLoader.display(mContext, myVideoView.thumbImageView, picUrl);
        myVideoView.fullscreenButton.setVisibility(View.GONE);// 禁止横竖屏切换
        myVideoView.startVideo();

    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    protected void initData() {

    }

    /**
     * intent
     */
    private void sendintent(String action) {
        Intent intent = new Intent(mContext, NewMusicService.class);
        intent.setAction(action);
        startService(intent);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
    }
}
