package cn.com.zwwl.bayuwen.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.activity.fm.MusicPlayActivity;
import cn.com.zwwl.bayuwen.model.FmModel;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.util.Tools;

public class PlayController extends LinearLayout {
    private Context mContext;
    private View conentView;
    private FmModel fmModel;// 当前音频
    private ImageView playOrPause;// 全局控制器上的播放、暂停按钮
    private ImageView close;
    private TextView title, time;
    private PlayControlClickListener listener;
    private boolean isPlaying = false;

    private void init() {
        conentView = LayoutInflater.from(mContext).inflate(R.layout.view_play_control, null);
        addView(conentView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        close = conentView.findViewById(R.id.control_play_close);
        close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCloseClick();
            }
        });
        playOrPause = conentView.findViewById(R.id.control_img);
        playOrPause.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    playOrPause.setImageResource(R.mipmap.play_cpntrol_play);
                } else playOrPause.setImageResource(R.mipmap.play_cpntrol_pause);

                listener.onPlayOrPauseClick();
            }
        });
        title = conentView.findViewById(R.id.control_title);
        time = conentView.findViewById(R.id.control_time);
        conentView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, MusicPlayActivity.class);
                mContext.startActivity(i);
                ((Activity) mContext).overridePendingTransition(R.anim.popup_enter, R.anim.popup_exit);
            }
        });


    }

    public void setPlay() {
        close.setVisibility(View.GONE);
        playOrPause.setImageResource(R.mipmap.play_cpntrol_pause);
    }

    public void setPause() {
        close.setVisibility(View.VISIBLE);
        playOrPause.setImageResource(R.mipmap.play_cpntrol_play);
    }


    /**
     * @param context
     */
    public PlayController(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public PlayController(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public void setData(FmModel fmModel) {
        this.fmModel = fmModel;

        title.setText(fmModel.getTitle());
        time.setText("00:00/" + CalendarTools.getTime(Long.valueOf(fmModel.getAudioDuration())));

    }

    public void setListener(PlayControlClickListener listener) {
        this.listener = listener;
    }

    public void setCurrentTime(int progress) {
        if (fmModel == null) return;
        String pp = CalendarTools.getTime(progress);
        time.setText(pp + "/" + CalendarTools.getTime(Long.valueOf(fmModel.getAudioDuration())));
    }

    public interface PlayControlClickListener {
        public void onPlayOrPauseClick();

        public void onCloseClick();

    }

}