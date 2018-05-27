package cn.com.zwwl.bayuwen.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.activity.BaseActivity;
import cn.com.zwwl.bayuwen.api.ActionApi;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.fm.AlbumModel;
import cn.com.zwwl.bayuwen.model.fm.FmModel;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.util.Tools;

public class NewMusicService extends Service {
    // 全局音乐播放器
    public static MediaPlayer mediaPlayer;

    private boolean isReleased = false;// 未释放
    private int playStatus = 0;//0：停止，1：正在播放，2：暂停，3：当前播放完毕

    public AlbumModel albumModel;
    public FmModel currentFmModel;

    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication.newMusicService = this;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 页面传入service的动作： 开始、暂停/恢复、拖动seekbar
     * service传入页面的动作：开始、结束、seekbar，
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction().equals(BaseActivity.ACTION_START_PLAY)) {
            init(intent);
        } else if (intent.getAction().equals(BaseActivity.ACTION_RESUME_PAUSE)) {
            if (isPlaying()) {
                pause();
            } else {
                resume();
            }
        } else if (intent.getAction().equals(BaseActivity.ACTION_SEEK_SEEKBAR)) {
            int pp = intent.getIntExtra("change_to", 0);
            changeProgress(pp);
        }
        return super.onStartCommand(intent, flags, startId);
    }


    private void init(Intent intent) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            playStatus = 0;
            mediaPlayer = null;
            isReleased = true;
        }
        albumModel = (AlbumModel) intent.getSerializableExtra("play_model");
        int posi = intent.getIntExtra("play_model_position", 0);

        if (albumModel != null && Tools.listNotNull(albumModel.getFmModels()) && albumModel
                .getFmModels().size() > posi) {
            List<FmModel> datas = new ArrayList<>();
            datas.addAll(albumModel.getFmModels());
            currentFmModel = datas.get(posi);
            prepare(currentFmModel.getAudioUrl());
        }
    }


    /**
     * 重新启动页面，向页面传递当前播放数据
     */
    public AlbumModel getCurrentAl() {
        return albumModel;
    }

    public void prepare(String path) {
        if (mediaPlayer == null && !isPlaying()) { // 新播放音频
            mediaPlayer = new MediaPlayer();
            isReleased = false;
        }

        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    start();
                }
            });
            mediaPlayer.prepareAsync();

            // mediaPlayer控视频播放完成出发的事件
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    playStatus = 3;
                    Message msg = new Message();
                    msg.what = BaseActivity.MSG_COMPLETE;
                    notifyActivity(BaseActivity.ACTION_MSG_COMPLETE, msg);
                }
            });
            mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {

                @Override
                public boolean onInfo(MediaPlayer arg0, int arg1, int arg2) {
                    return false;
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    Log.e("MediaPlayer.OnError", what + "___" + extra);
                    return false;
                }
            });
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected boolean isCompleted() {
        return playStatus == 3 ? true : false;
    }

    /**
     * 开始播放,申请音频焦点
     */
    public void start() {
        if (mediaPlayer != null && !isPlaying()) {
            mediaPlayer.start();
            playStatus = 1;
            // 开始播放向页面发送播放message
            Message m = new Message();
            m.what = BaseActivity.MSG_START_PLAY;
            notifyActivity(BaseActivity.ACTION_START_PLAY, m);
            handler.post(runnable);
            addPLay();
        }
    }

    /**
     * 暂停
     */
    public void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            playStatus = 2;
        }
    }

    /**
     * 恢复
     */
    public void resume() {
        if (mediaPlayer != null && !isPlaying()) {
            mediaPlayer.start();
            handler.post(runnable);
            playStatus = 1;
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            isReleased = true;
            mediaPlayer = null;
            playStatus = 0;
        }
    }

    public boolean isPlaying() {
        return playStatus == 1 ? true : false;
    }

    /**
     * 有人拖动Seekbar了，要告诉service去改变播放的位置
     *
     * @param progress
     */
    public void changeProgress(int progress) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(progress);
        }
    }

    // 循环
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        public void run() {
            toUpdateProgress();
        }
    };

    private void notifyActivity(String action, Message message) {
        Intent i = new Intent(action);
        i.putExtra("music_service_message", message);
        sendBroadcast(i);
    }

    private void toUpdateProgress() {
        if (mediaPlayer != null && !isReleased && isPlaying()) {
            int progress = mediaPlayer.getCurrentPosition() / 1000;
            Message message = new Message();
            message.what = BaseActivity.MSG_CHANGE_TIME;
            message.arg1 = progress;
            notifyActivity(BaseActivity.ACTION_CHANGE_TIME, message);
            handler.postDelayed(runnable, 100);
        }
    }

    public void setIsChanging() {
        isReleased = true;
    }

    public FmModel getCurrentModel() {
        return currentFmModel;
    }


    /**
     * 增加播放量
     */
    private void addPLay() {
        new ActionApi(this, albumModel.getKid(), new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {
                // 播放量结果不需要监听
            }

            @Override
            public void setError(ErrorMsg error) {

            }
        });
    }


}

