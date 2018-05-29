package cn.com.zwwl.bayuwen.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.view.PlayController;

import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Eva. on 17/3/17.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback {
    /**
     * 网页跳转uri
     */
    protected String fromHtmlUri = "";
    public Context mContext;
    public PlayController playController;// 音乐播放器
    private Dialog dialog;

    private RelativeLayout process_layout;//
    private LinearLayout errorLayout;// 加载错误页面
    private ProgressBar loadingLayout;// loading页面
    private ImageView errorImg;// 加载错误图片
    private TextView errorTxt;// 加载错误提示

    private MusicStatusReceiver musicStatusReceiver;

    /**
     * 播放／暂停
     */
    public static final int MSG_RESUME_PAUSE = 1001;
    public static final String ACTION_RESUME_PAUSE = "action_resume_pause";
    /**
     * 开始播放
     */
    public static final int MSG_START_PLAY = 1002;
    public static final String ACTION_START_PLAY = "action_start_play";
    /**
     * 播放完毕
     */
    public static final int MSG_COMPLETE = 1003;
    public static final String ACTION_MSG_COMPLETE = "action_msg_complete";
    /**
     * 改变时间
     */
    public static final int MSG_CHANGE_TIME = 1004;
    public static final String ACTION_CHANGE_TIME = "action_change_time";
    /**
     * 刷新列表选中状态
     */
    public static final int MSG_REFRESH_LIST = 1005;
    public static final String ACTION_REFRESH_LIST = "action_refresh_list";
    /**
     * 上一首
     */
    public static final int MSG_ALBUM_PRE = 1006;
    public static final String ACTION_ALBUM_PRE = "action_album_pre";
    /**
     * 下一首
     */
    public static final int MSG_ALBUM_NEXT = 1007;
    public static final String ACTION_ALBUM_NEXT = "action_album_next";

    /**
     * 拖动seekbar
     */
    public static final String ACTION_SEEK_SEEKBAR = "action_seek_seekbar";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }


    @Override
    protected void onResume() {
        super.onResume();
        analycisUri();

    }

    protected abstract void initData();

    public void initErrorLayout() {
        process_layout = findViewById(R.id.process_layout_activity);
        errorLayout = findViewById(R.id.error_layout);
        loadingLayout = findViewById(R.id.processBar);
        errorImg = findViewById(R.id.error_img);
        errorTxt = findViewById(R.id.error_text);
        errorLayout.setOnClickListener(this);
    }

    /**
     * 分析网页跳转
     */
    private void analycisUri() {
        Intent i_getvalue = getIntent();
        String action = i_getvalue.getAction();
        if (Intent.ACTION_VIEW.equals(action)) {
            Uri uri = i_getvalue.getData();
            fromHtmlUri = uri.toString();

        }
    }

    /**
     * 显示loading图标
     */
    public void showLoading() {
        if (loadingLayout == null) {
            System.out.println("未初始化process!!");
            return;
        }
        process_layout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.VISIBLE);
        errorLayout.setVisibility(View.GONE);
    }

    /**
     * 显示错误提示
     */
    public void showError(final int imgres, final int stringres) {
        if (process_layout == null) {
            System.out.println("未初始化process!!");
            return;
        }
        process_layout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
        errorImg.setImageResource(imgres);
        errorTxt.setText(stringres);

    }

    /**
     * 隐藏process_layout
     */
    public void disProcess() {
        if (process_layout == null) {
            System.out.println("未初始化process!!");
            return;
        }
        process_layout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }

    /**
     * 悬浮的dialog
     *
     * @param flag
     */
    public void showLoadingDialog(boolean flag) {
        if (flag) {
            if (dialog == null) {
                dialog = new Dialog(this, R.style.NobackDialog);
                dialog.setContentView(R.layout.processbar);
                dialog.setCancelable(true);
            }
            try {
                dialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                if (dialog != null && dialog.isShowing()) {
                    dialog.cancel();
                    dialog = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void showToast(final String txt) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, txt, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showToast(final int id) {

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, id, Toast.LENGTH_SHORT).show();
            }
        });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

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
                getMusicMsg((Message) intent.getParcelableExtra("music_service_message"));

            }

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (musicStatusReceiver != null)
            unregisterReceiver(musicStatusReceiver);
    }

    // 播放完成
    protected void getMusicMsg(Message ms) {

    }


    /**
     * 用于给子类继承点击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.error_layout:
                initData();
                break;
        }

    }

    /**
     * 隐藏软键盘
     */
    public void hideJianpan() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context
                .INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    public void askPermission(String[] permissions, int requestCode) {
        List<String> ll = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            int permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);

            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ll.add(permissions[i]);
            }
        }
        if (ll.size() > 0)
            ActivityCompat.requestPermissions(this, (String[]) ll.toArray(new String[ll.size()]),
                    requestCode);
    }

    /**
     * 获取控件高度
     *
     * @param view
     * @return
     */
    public int getViewHeight(View view) {
        int width = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
        view.getMeasuredWidth(); // 获取宽度
        view.getMeasuredHeight(); // 获取高度
        return view.getMeasuredHeight();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[]
            grantResults) {

    }


}

