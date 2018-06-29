package cn.com.zwwl.bayuwen.view.music;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.WindowManager.LayoutParams;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.activity.fm.MusicPlayActivity;
import cn.com.zwwl.bayuwen.http.HttpUtil;
import cn.com.zwwl.bayuwen.model.fm.AlbumModel;
import cn.com.zwwl.bayuwen.model.fm.FmModel;
import cn.com.zwwl.bayuwen.service.NewMusicService;
import cn.com.zwwl.bayuwen.util.AnimationTools;
import cn.com.zwwl.bayuwen.util.CalendarTools;

import static android.view.WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;
import static cn.com.zwwl.bayuwen.activity.BaseActivity.ACTION_RESUME_PAUSE;


public class MusicWindow {
    private static final byte[] LOCKER = new byte[0];
    private static View mView = null;
    private static WindowManager mWindowManager = null;
    private static Context mContext = null;
    public static Boolean isShown = false;
    private static TextView titleTv, timeTv;
    private static ImageView playBt, closeBt;

    public static AlbumModel currentAlModel;// 当前显示的播放数据
    public static FmModel currentFmModel;// 当前显示的播放数据
    public static boolean isPlaying = false;// 记录播放状态
    //    private static boolean isClosedByUser = false;// 是否被用户手动关闭悬浮窗
    private static MusicWindow mInstance;

    private static int windowHeight = 120;
    private static int windowWidPadding = 20;

    public static MusicWindow getInstance(Context context) {
        if (mInstance == null) {
            synchronized (LOCKER) {
                if (mInstance == null) {
                    mInstance = new MusicWindow(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }

    public MusicWindow(Context context) {
        this.mContext = context;
        // 获取WindowManager
        mWindowManager = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        mView = setUpView();
    }

    /**
     * 每次进入专辑列表时，重置用户手动关闭悬浮窗的状态
     */
    public static void resetCloseStatus() {
//        isClosedByUser = false;
    }

    /**
     * 显示弹出框
     */
    public static void showPopupWindow() {
        if (isShown) {
            return;
        }
        isShown = true;
        mWindowManager.addView(mView, getPamas(0));
    }


    /**
     * 隐藏弹出框
     */
    public static void hidePopupWindow() {
        if (isShown && null != mView) {
            mWindowManager.removeView(mView);
            isShown = false;
        }
    }

    /**
     * ??todo 动画
     * TranslateAnimation ani = new TranslateAnimation(0.0f, 0.0f, MyApplication.height - 160,
     * MyApplication
     * .height - 160 - y);
     * ani.setDuration(3000);
     *
     * @param y
     */
    public static void movetoController(int y) {
        if (isShown) {
            mWindowManager.updateViewLayout(mView, getPamas(y));
        } else {
            if (isPlaying) {
                isShown = true;
                mWindowManager.addView(mView, getPamas(y));
            }
        }
    }

    private static WindowManager.LayoutParams getPamas(int y) {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        // 类型
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            params.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        // 设置flag
        int flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // 如果设置了WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE，弹出的View收不到Back键的事件
        params.flags = flags;
        // 不设置这个弹出框的透明遮罩显示为黑色
        params.format = PixelFormat.TRANSLUCENT;
        // FLAG_NOT_TOUCH_MODAL不阻塞事件传递到后面的窗口
        // 设置 FLAG_NOT_FOCUSABLE 悬浮窗口较小时，后面的应用图标由不可长按变为可长按
        // 不设置这个flag的话，home页的划屏会有问题
        params.width = MyApplication.width - windowWidPadding * 2;
        params.height = windowHeight;
        params.gravity = Gravity.LEFT | Gravity.BOTTOM;
        params.x = windowWidPadding;
        params.y = y;
        return params;
    }

    public static View setUpView() {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.view_play_control,
                null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hidePopupWindow();
                Intent i = new Intent(mContext, MusicPlayActivity.class);
                i.putExtra("MusicPlayActivity_al", currentAlModel);
                i.putExtra("MusicPlayActivity_fm", currentFmModel);
                mContext.startActivity(i);

            }
        });
        playBt = view.findViewById(R.id.control_img);
        playBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    playBt.setImageResource(R.drawable.music_control_pause);
                    closeBt.setVisibility(View.VISIBLE);
                } else {
                    playBt.setImageResource(R.drawable.music_control_play);
                    closeBt.setVisibility(View.GONE);
                }
                sendintent(ACTION_RESUME_PAUSE);
            }
        });
        closeBt = view.findViewById(R.id.control_play_close);
        closeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                isClosedByUser = true;
                MusicWindow.hidePopupWindow();
            }
        });

        titleTv = view.findViewById(R.id.control_title);
        timeTv = view.findViewById(R.id.control_time);

//        // 点击窗口外部区域可消除
//        // 这点的实现主要将悬浮窗设置为全屏大小，外层有个透明背景，中间一部分视为内容区域
//        // 所以点击内容区域外部视为点击悬浮窗外部
//        final View popupWindowView = view.findViewById(R.id.popup_window);// 非透明的内容区域
//        view.setOnTouchListener(new OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int x = (int) event.getX();
//                int y = (int) event.getY();
//                Rect rect = new Rect();
//                popupWindowView.getGlobalVisibleRect(rect);
//                if (!rect.contains(x, y)) {
//                    MusicWindow.hidePopupWindow();
//                }
//                return false;
//            }
//        });

        return view;
    }

    public void setFmData(AlbumModel albumModel, FmModel fmModel) {
        currentAlModel = albumModel;
        currentFmModel = fmModel;
        titleTv.setText(fmModel.getTitle());
        timeTv.setText("00:00/" + CalendarTools.getTime(Long.valueOf(fmModel.getAudioDuration())));
    }

    public void setCurrentTime(int progress, String totalP) {
        String pp = CalendarTools.getTime(progress);
        timeTv.setText(pp + "/" + totalP);
    }

    public void setStart() {
        playBt.setImageResource(R.drawable.music_control_play);
    }

    public void setPause() {
        playBt.setImageResource(R.drawable.music_control_pause);
    }

    /**
     * intent
     */
    private static void sendintent(String action) {
        Intent intent = new Intent(mContext, NewMusicService.class);
        intent.setAction(action);
        mContext.startService(intent);
    }

}
