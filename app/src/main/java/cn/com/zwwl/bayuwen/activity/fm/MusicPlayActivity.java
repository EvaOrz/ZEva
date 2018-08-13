package cn.com.zwwl.bayuwen.activity.fm;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.activity.BaseActivity;
import cn.com.zwwl.bayuwen.model.fm.AlbumModel;
import cn.com.zwwl.bayuwen.model.fm.FmModel;
import cn.com.zwwl.bayuwen.service.NewMusicService;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.view.PlayListPopWindow;
import cn.com.zwwl.bayuwen.view.music.MusicWindow;

/**
 * 播放器页面
 */
public class MusicPlayActivity extends BaseActivity {

    private TextView alTitle, fmTitle, playTimebox, wholeTimebox;
    private ImageView image, playOrPause, nextBtn, preBtn;
    private SeekBar seekBar;
    private PlayListPopWindow playListPopWindow;
    private MusicStatusReceiver musicStatusReceiver;

    private AlbumModel albumModel;
    private ArrayList<FmModel> fmModels = new ArrayList<>();
    private FmModel currentFmModel;// 正在播放的音频model
    private int currentPos = -1;// 正在播放的音频在列表中位置
    public int progress;// 播放进度/ 播放长度
    private boolean ifFromUser = false;// 进度条拖动 是否来自用户

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        initView();
        initData();
        registerReceiver();//先恢复数据 再注册receiver
    }

    /**
     * 注册音乐播放器的监听receiver，用于接收音乐播放情况，更新UI
     */
    public void registerReceiver() {
        //实例化过滤器；
        IntentFilter intentFilter = new IntentFilter();
        //添加过滤的Action值；
        intentFilter.addAction(ACTION_RESUME_PAUSE);
        intentFilter.addAction(ACTION_START_PLAY);
        intentFilter.addAction(ACTION_SEEK_SEEKBAR);
        intentFilter.addAction(ACTION_MSG_COMPLETE);
        intentFilter.addAction(ACTION_CHANGE_TIME);
        intentFilter.addAction(ACTION_REFRESH_LIST);
        intentFilter.addAction(ACTION_ALBUM_PRE);
        intentFilter.addAction(ACTION_ALBUM_NEXT);
        //实例化广播监听器；
        musicStatusReceiver = new MusicStatusReceiver();
        //将广播监听器和过滤器注册在一起；
        registerReceiver(musicStatusReceiver, intentFilter);
    }


    /**
     * 播放器页面初始化只有恢复播放数据的情况
     */
    @Override
    protected void initData() {

        albumModel = (AlbumModel) getIntent().getSerializableExtra("MusicPlayActivity_al");
        currentFmModel = (FmModel) getIntent().getSerializableExtra("MusicPlayActivity_fm");

        if (Tools.listNotNull(albumModel.getFmModels())) {
            fmModels.clear();
            fmModels.addAll(albumModel.getFmModels());
            String tname = "";
            if (Tools.listNotNull(albumModel.getTeachers())) {
                tname = albumModel.getTeachers().get(0).getName();
            }
            playListPopWindow = new PlayListPopWindow(this, tname, new PlayListPopWindow
                    .OnItemClickListener() {
                @Override
                public void choose(int position) {
                    checkLoading(position);
                    changeMusic(position);
                }
            });
            for (int i = 0; i < fmModels.size(); i++) {
                if (fmModels.get(i).getId().equals(currentFmModel.getId())) {
                    currentPos = i;
                }
            }
            handler.sendEmptyMessage(0);
            checkPreNext();
            checkCurrent();
        }

    }

    private void initView() {
        alTitle = findViewById(R.id.album_title);
        fmTitle = findViewById(R.id.fm_title);
        image = findViewById(R.id.fm_img);
        seekBar = findViewById(R.id.fm_seekbar);
        playTimebox = findViewById(R.id.fm_time1);
        wholeTimebox = findViewById(R.id.fm_time2);
        nextBtn = findViewById(R.id.fm_next);
        preBtn = findViewById(R.id.fm_previous);
        playOrPause = findViewById(R.id.fm_play);

        findViewById(R.id.fm_list).setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar s) {
                if (ifFromUser) {
                    int curTime = s.getProgress();
                    sendintent(ACTION_SEEK_SEEKBAR, curTime);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onProgressChanged(SeekBar arg0, int progress, boolean fromUser) {
                ifFromUser = fromUser;
            }
        });

        findViewById(R.id.fm_back).setOnClickListener(this);
        findViewById(R.id.fm_share).setOnClickListener(this);
        playOrPause.setOnClickListener(this);
        preBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
    }

    /**
     * 绑定intent
     */
    private void sendintent(String action, int curtime) {
        if (currentFmModel != null) {
            Intent intent = new Intent(this, NewMusicService.class);
            intent.setAction(action);
            if (action.equals(ACTION_SEEK_SEEKBAR)) {
                intent.putExtra("change_to", curtime);
            } else if (action.equals(ACTION_START_PLAY)) {
                intent.putExtra("play_model", albumModel);
                intent.putExtra("play_model_position", currentPos);
            }
            startService(intent);
        } else showToast("音频数据缺失");

    }

    private void checkPreNext() {
        if (currentPos == 0) {
            preBtn.setImageResource(R.mipmap.player_previous_dis);
        } else if (currentPos + 1 == fmModels.size()) {
            nextBtn.setImageResource(R.mipmap.player_next_dis);
        } else {
            preBtn.setImageResource(R.mipmap.player_previous);
            nextBtn.setImageResource(R.mipmap.player_next);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MusicWindow.getInstance(mContext).showPopupWindow();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fm_back:
                finish();
                break;
            case R.id.fm_share:

                break;
            case R.id.fm_play:
                sendintent(ACTION_RESUME_PAUSE, 0);
                if (MusicWindow.isPlaying) {
                    playOrPause.setImageResource(R.mipmap.player_pause);
                } else {
                    playOrPause.setImageResource(R.mipmap.player_play);
                }

                break;
            case R.id.fm_previous:
                if (currentPos > 0) {
                    currentPos--;
                    checkPreNext();
                    changeMusic(currentPos);
                    handler.sendEmptyMessage(0);
                } else Toast.makeText(mContext, "已是列表第一首", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fm_next:
                if (currentPos + 1 < fmModels.size()) {
                    currentPos++;
                    checkPreNext();
                    changeMusic(currentPos);
                    handler.sendEmptyMessage(0);
                } else Toast.makeText(mContext, "已是列表最后一首", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fm_list:
                playListPopWindow.show();
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    // 初始化页面
                    Glide.with(mContext).load(albumModel.getPic()).into(image);
                    alTitle.setText(albumModel.getTitle());
                    fmTitle.setText(currentFmModel.getTitle());
                    wholeTimebox.setText(CalendarTools.getTime(Long.valueOf(currentFmModel
                            .getAudioDuration())));
                    seekBar.setMax(Integer.valueOf(currentFmModel.getAudioDuration()));
                    progress = 0;
                    seekBar.setProgress(0);
                    playTimebox.setText("00:00");
                    if (MusicWindow.getInstance(mContext).isPlaying) {
                        playOrPause.setImageResource(R.mipmap.player_play);
                    } else {
                        playOrPause.setImageResource(R.mipmap.player_pause);
                    }
                    break;

                case MSG_START_PLAY: // ---------------－开始播放
                    playOrPause.setImageResource(R.mipmap.player_play);
                    checkCurrent();
                    break;
                case MSG_CHANGE_TIME:// ----------------seekbar拖动
                    if (msg.arg1 > progress) {
                        progress = msg.arg1;// 更新当前进度
                    }
                    seekBar.setProgress(progress);

                    playTimebox.setText(CalendarTools.getTime(seekBar.getProgress()));
                    break;
                case MSG_REFRESH_LIST:
                    playListPopWindow.setData(fmModels);
                    break;

                case MSG_COMPLETE:// -------------------播放完成
                    playOrPause.setImageResource(R.mipmap.player_pause);
                    break;
            }
        }
    };


    private void checkCurrent() {
        for (int i = 0; i < fmModels.size(); i++) {
            if (MusicWindow.getInstance(this).currentFmModel != null && fmModels.get(i).getId()
                    .equals(MusicWindow.getInstance(this).currentFmModel
                            .getId())) {
                currentPos = i;
                fmModels.get(i).setGifSta(2);
            } else
                fmModels.get(i).setGifSta(0);
        }
        handler.sendEmptyMessage(MSG_REFRESH_LIST);
    }

    private void checkLoading(int pos) {
        for (int i = 0; i < fmModels.size(); i++) {
            if (i == pos) {
                fmModels.get(i).setGifSta(1);
            } else
                fmModels.get(i).setGifSta(0);
        }
        handler.sendEmptyMessage(MSG_REFRESH_LIST);
    }

    /**
     * 主动换歌
     */
    private void changeMusic(int p) {
        if (!Tools.listNotNull(fmModels)) return;
        currentPos = p;
        currentFmModel = fmModels.get(p);
        handler.sendEmptyMessage(0);
        // 更新播放状态
        sendintent(ACTION_START_PLAY, 0);// 主动切歌
    }

    @Override
    protected void onStop() {
        super.onStop();
//        if (musicStatusReceiver != null)
//            unregisterReceiver(musicStatusReceiver);
    }

    /**
     * 监听音乐播放Service的receiver
     */
    class MusicStatusReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // 开始播放、播放完成、更新时间
            if (intent.getAction().equals(ACTION_START_PLAY) || intent.getAction().equals
                    (ACTION_MSG_COMPLETE) || intent.getAction().equals(ACTION_CHANGE_TIME)) {
                handler.sendMessage((Message) intent.getParcelableExtra("music_service_message"));

            }
        }
    }

}
