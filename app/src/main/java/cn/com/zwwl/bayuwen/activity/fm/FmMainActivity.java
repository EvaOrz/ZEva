package cn.com.zwwl.bayuwen.activity.fm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.activity.BaseActivity;
import cn.com.zwwl.bayuwen.db.UserDataHelper;
import cn.com.zwwl.bayuwen.model.fm.AlbumModel;
import cn.com.zwwl.bayuwen.model.fm.FmModel;
import cn.com.zwwl.bayuwen.service.NewMusicService;
import cn.com.zwwl.bayuwen.util.AnimationTools;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.view.FmView;
import cn.com.zwwl.bayuwen.view.PlayController;
import cn.com.zwwl.bayuwen.view.UserCenterView;

public class FmMainActivity extends BaseActivity {
    private FmView fmView;
    private UserCenterView userCenterView;
    private LinearLayout container;
    private RadioButton fmButton, userButton;
    private long lastClickTime = 0;

    private AlbumModel albumModel;
    private List<FmModel> fmModels = new ArrayList<>();// 音频列表
    private FmModel currentFmModel;// 当前正在播放的音频
    private int currentPosition = -1;// 当前正在播放的音频在列表中的位置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();
        // 首页有无网状态处理
        initErrorLayout();
        initData();

    }

    @Override
    protected void initData() {
        if (!Tools.checkNetWork(this)) {
            showError(R.mipmap.blank_no_wifi, R.string.no_wifi);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 登录状态变化
        if (MyApplication.loginStatusChange) {
            MyApplication.loginStatusChange = false;
            userCenterView.getUserinfo();
        }
        registerReceiver();//先恢复数据 再注册receiver
        /**
         *  获取播放音乐状态
         */
        if (MyApplication.newMusicService == null) {
            playController.setVisibility(View.GONE);
        } else {
            playController.setVisibility(View.VISIBLE);
            albumModel = MyApplication.newMusicService.getCurrentAl();
            currentFmModel = MyApplication.newMusicService.getCurrentModel();
            if (Tools.listNotNull(albumModel.getFmModels())) {
                fmModels.clear();
                fmModels.addAll(albumModel.getFmModels());
                for (int i = 0; i < fmModels.size(); i++) {
                    if (fmModels.get(i).getId().equals(currentFmModel.getId())) {
                        currentPosition = i;
                    }
                }
                handler.sendEmptyMessage(0);
            }
            showPlayController();
            playController.setData(currentFmModel);
            if (MyApplication.newMusicService.isPlaying()) {
                playController.setPlay();
            } else {
                playController.setPause();
            }
        }
    }

    /**
     * 绑定intent
     */
    private void sendintent(String action, int curtime) {
        if (currentFmModel != null) {
            Intent intent = new Intent(this, NewMusicService.class);
            intent.setAction(action);
            startService(intent);
        } else showToast("音频数据缺失");

    }

    private void initView() {
        container = findViewById(R.id.main_container);
        playController = findViewById(R.id.main_playcontroller);
        playController.setListener(new PlayController.PlayControlClickListener() {
            @Override
            public void onPlayOrPauseClick() {
                sendintent(ACTION_RESUME_PAUSE, 0);
                handler.sendEmptyMessage(MSG_RESUME_PAUSE);
            }

            @Override
            public void onCloseClick() {
                playController.setVisibility(View.GONE);
            }
        });
        fmButton = findViewById(R.id.bottom_nav_fm);
        userButton = findViewById(R.id.bottom_nav_user);
        fmButton.setOnClickListener(this);
        userButton.setOnClickListener(this);
        fmView = new FmView(this);
        userCenterView = new UserCenterView(this);
        changTab(0);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }


    public void showPlayController() {
        if (MyApplication.isPlaying)
            AnimationTools.with().bottomMoveToViewLocation(playController, 300);
    }

    public void unshowPlayController() {
        AnimationTools.with().moveToViewBottom(playController, 300);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.bottom_nav_fm:
                changTab(0);
                break;
            case R.id.bottom_nav_user:
                changTab(1);
                break;
        }
    }

    private void changTab(int i) {
        container.removeAllViews();
        if (i == 0) {
            container.addView(fmView.getView(), new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        } else if (i == 1) {
            container.addView(userCenterView.getView(), new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));


        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    break;
                case MSG_START_PLAY: // ---------------－开始播放
                    playController.setVisibility(View.VISIBLE);
                    showPlayController();
                    playController.setPlay();
                    playController.setData(currentFmModel);

                    break;
                case MSG_CHANGE_TIME:
                    playController.setCurrentTime(msg.arg1);
                    break;
                case MSG_RESUME_PAUSE:// 播放/暂停
                    if (MyApplication.newMusicService.isPlaying()) {
                        playController.setPause();
                    } else {
                        playController.setPlay();
                    }
                    break;
                case MSG_COMPLETE:// -------------------播放完成
                    // 最后一曲
//                    if (currentPosition + 1 == fmModels.size()) {
//                        playController.setPause();
//                        handler.sendEmptyMessage(MSG_REFRESH_LIST);
//                    } else {// 开始下一曲
//                        currentPosition++;
//                        changeMusic(currentPosition);
//                    }
                    break;
            }
        }
    };


    @Override
    protected void getMusicMsg(Message ms) {
        handler.sendMessage(ms);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            long clickTime = System.currentTimeMillis() / 1000;
            if (clickTime - lastClickTime >= 3) {
                lastClickTime = clickTime;
                showToast(R.string.exit_app);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 102) {
            if (resultCode == FmLoginActivity.LOGIN_SUCCESS) return;
            else if (resultCode == FmLoginActivity.LOGIN_CANCLE) finish();

        }
    }
}
