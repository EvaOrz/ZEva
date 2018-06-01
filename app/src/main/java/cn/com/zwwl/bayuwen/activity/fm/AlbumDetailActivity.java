package cn.com.zwwl.bayuwen.activity.fm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.activity.BaseActivity;
import cn.com.zwwl.bayuwen.adapter.FmAdapter;
import cn.com.zwwl.bayuwen.adapter.PinglunAdapter;
import cn.com.zwwl.bayuwen.api.ActionApi;
import cn.com.zwwl.bayuwen.api.fm.AlbumApi;
import cn.com.zwwl.bayuwen.api.fm.CollectionApi;
import cn.com.zwwl.bayuwen.api.fm.PinglunApi;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.model.fm.AlbumModel;
import cn.com.zwwl.bayuwen.model.fm.AlbumModel.*;
import cn.com.zwwl.bayuwen.model.fm.FmModel;
import cn.com.zwwl.bayuwen.model.fm.PinglunModel;
import cn.com.zwwl.bayuwen.service.NewMusicService;
import cn.com.zwwl.bayuwen.util.AnimationTools;
import cn.com.zwwl.bayuwen.util.ShareTools;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.widget.CommonWebView;
import cn.com.zwwl.bayuwen.view.PlayController;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.widget.CallScrollView;
import cn.com.zwwl.bayuwen.widget.NoScrollListView;

/**
 * 专辑详情页面
 */
public class AlbumDetailActivity extends BaseActivity {

    private String aId;// 当前专辑的id
    private AlbumModel albumModel;
    private List<FmModel> fmModels = new ArrayList<>();// 音频列表
    private List<Teacher> teachers = new ArrayList<>();// 教师列表
    private List<PinglunModel> pinglunModels = new ArrayList<>();// 评论列表
    private EditText inputEdit;
    private NoScrollListView fmListView, pingListView;
    private FmAdapter fmAdapter;
    private PinglunAdapter pinglunAdapter;
    private ImageView imageView;
    private TextView title, name, time, likeBtn, shoucangBtn, shareBtn;
    private View viewPlay, viewPinglun, viewTeacher;
    private CommonWebView viewDetail;
    private LinearLayout contain, teacherContain;
    private TextView part1, part2, part3, part4;
    private View line1, line2, line3, line4;
    private PlayController playController;
    private LinearLayout inputLayout;// 输入框

    private FmModel currentFmModel;// 当前正在播放的音频
    private int currentPosition = -1;// 当前正在播放的音频在列表中的位置

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_detail);
        if (getIntent().getSerializableExtra("AlbumDetailActivity_data") != null && getIntent()
                .getSerializableExtra("AlbumDetailActivity_data") instanceof String)
            aId = getIntent().getStringExtra("AlbumDetailActivity_data");
        initView();
        initErrorLayout();
        initData();

    }

    @Override
    public void initData() {
        showLoading();
        new AlbumApi(this, aId, new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {
                if (entry != null && entry instanceof AlbumModel) {
                    albumModel = (AlbumModel) entry;
                    handler.sendEmptyMessage(1);
                    fmModels.clear();
                    fmModels.addAll(albumModel.getFmModels());
                    handler.sendEmptyMessage(0);
                    teachers.clear();
                    teachers.addAll(albumModel.getTeachers());
                    handler.sendEmptyMessage(2);
                    getPinglunData(albumModel.getKid(), "");
                } else {


                }
            }

            @Override
            public void setError(ErrorMsg error) {
                if (error != null)
                    handler.sendEmptyMessage(4);
            }
        });
    }

    /**
     * 获取评论数据
     */
    private void getPinglunData(String kid, String cid) {
        new PinglunApi(this, kid, cid, new FetchEntryListListener() {
            @Override
            public void setData(List list) {
                if (Tools.listNotNull(list)) {
                    pinglunModels.clear();
                    pinglunModels.addAll(list);
                    handler.sendEmptyMessage(3);
                }
            }

            @Override
            public void setError(ErrorMsg error) {
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();//先恢复数据 再注册receiver
        if (MyApplication.newMusicService == null || !MyApplication.newMusicService.isPlaying()) {
            playController.setVisibility(View.GONE);
        } else {
            playController.setVisibility(View.VISIBLE);
            currentFmModel = MyApplication.newMusicService.getCurrentModel();
            for (int i = 0; i < fmModels.size(); i++) {
                if (fmModels.get(i).getId().equals(currentFmModel.getId())) {
                    currentPosition = i;
                }
            }
            handler.sendEmptyMessage(0);
            showPlayController();
            playController.setData(currentFmModel);

        }
    }

    private void initView() {
        findViewById(R.id.album_detail_back).setOnClickListener(this);
        contain = findViewById(R.id.ablum_detail_contain);
        imageView = findViewById(R.id.album_detail_img);
        title = findViewById(R.id.album_detail_t);
        name = findViewById(R.id.album_detail_name);
        time = findViewById(R.id.album_detail_time);
        likeBtn = findViewById(R.id.album_detail_like);
        shoucangBtn = findViewById(R.id.album_detail_shoucang);
        shareBtn = findViewById(R.id.album_detail_share);
        playController = findViewById(R.id.ablum_detail_playcontroller);
        playController.setListener(new PlayController.PlayControlClickListener() {
            @Override
            public void onPlayOrPauseClick() {
                // 没有播放，则启动
                if (MyApplication.newMusicService == null) {
                    sendintent(ACTION_START_PLAY, 0);
                } else {
                    sendintent(ACTION_RESUME_PAUSE, 0);
                    handler.sendEmptyMessage(MSG_RESUME_PAUSE);
                }
            }

            @Override
            public void onCloseClick() {
                playController.setVisibility(View.GONE);
            }
        });
        inputLayout = findViewById(R.id.album_detail_input);
        inputEdit = findViewById(R.id.album_detail_inputedit);

        part1 = findViewById(R.id.ablum_detail_part1);
        part2 = findViewById(R.id.ablum_detail_part2);
        part3 = findViewById(R.id.ablum_detail_part3);
        part4 = findViewById(R.id.ablum_detail_part4);
        line1 = findViewById(R.id.ablum_detail_line1);
        line2 = findViewById(R.id.ablum_detail_line2);
        line3 = findViewById(R.id.ablum_detail_line3);
        line4 = findViewById(R.id.ablum_detail_line4);
        part1.setOnClickListener(this);
        part2.setOnClickListener(this);
        part3.setOnClickListener(this);
        part4.setOnClickListener(this);
        likeBtn.setOnClickListener(this);
        shoucangBtn.setOnClickListener(this);
        shareBtn.setOnClickListener(this);
        findViewById(R.id.album_detail_inputsend).setOnClickListener(this);

        viewPlay = LayoutInflater.from(this).inflate(R.layout.view_album_detail_1, null);
        viewTeacher = LayoutInflater.from(this).inflate(R.layout.view_album_detail_3, null);
        teacherContain = viewTeacher.findViewById(R.id.part3_contain);
        viewDetail = new CommonWebView(this);
        viewPinglun = LayoutInflater.from(this).inflate(R.layout.view_album_detail_2, null);
        changeTab(1);

        viewPlay.findViewById(R.id.ablum_detail_all).setOnClickListener(this);
        fmListView = viewPlay.findViewById(R.id.album_detail_listview);
        pingListView = viewPinglun.findViewById(R.id.pinglun_list);

        ((CallScrollView) findViewById(R.id.album_detail_scroll)).setOnScrollListener(new CallScrollView.OnScrollListener() {
            @Override
            public void onScroll(boolean isUp) {
                if (isUp) {
                    handler.sendEmptyMessage(6);
                } else {
                    handler.sendEmptyMessage(5);
                }
            }

        });

        fmListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentPosition = position;
                currentFmModel = fmModels.get(position);

                sendintent(ACTION_START_PLAY, currentPosition);
            }
        });

        fmAdapter = new FmAdapter(this);
        fmAdapter.setData(fmModels, currentPosition);
        fmListView.setAdapter(fmAdapter);
        pinglunAdapter = new PinglunAdapter(this);
        pingListView.setAdapter(pinglunAdapter);

    }

    private void changeTab(int type) {
        if (type == 1) {
            part1.setTextColor(getResources().getColor(R.color.oringe));
            line1.setVisibility(View.VISIBLE);
        } else {
            part1.setTextColor(getResources().getColor(R.color.gray_dark));
            line1.setVisibility(View.INVISIBLE);
        }
        if (type == 2) {
            part2.setTextColor(getResources().getColor(R.color.oringe));
            line2.setVisibility(View.VISIBLE);
        } else {
            part2.setTextColor(getResources().getColor(R.color.gray_dark));
            line2.setVisibility(View.INVISIBLE);
        }
        if (type == 3) {
            part3.setTextColor(getResources().getColor(R.color.oringe));
            line3.setVisibility(View.VISIBLE);
        } else {
            part3.setTextColor(getResources().getColor(R.color.gray_dark));
            line3.setVisibility(View.INVISIBLE);
        }
        if (type == 4) {
            part4.setTextColor(getResources().getColor(R.color.oringe));
            line4.setVisibility(View.VISIBLE);
        } else {
            part4.setTextColor(getResources().getColor(R.color.gray_dark));
            line4.setVisibility(View.INVISIBLE);
        }
        contain.removeAllViews();
        switch (type) {
            case 1:// 播放列表
                inputLayout.setVisibility(View.GONE);
                contain.addView(viewPlay);
                break;
            case 2:// 详情介绍
                inputLayout.setVisibility(View.GONE);
                contain.addView(viewDetail);
                break;
            case 3://授课老师
                inputLayout.setVisibility(View.GONE);
                contain.addView(viewTeacher);
                break;
            case 4://学员评论
                inputLayout.setVisibility(View.VISIBLE);
                contain.addView(viewPinglun);
                break;

        }


    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:// 初始化播放列表
                    fmAdapter.setData(fmModels, currentPosition);
                    break;

                case 1:// 初始化专辑信息
                    disProcess();
                    if (!TextUtils.isEmpty(albumModel.getPic()))
                        Glide.with(mContext)
                                .load(albumModel.getPic())
                                .into(imageView);
                    title.setText(albumModel.getTitle());
                    name.setText(albumModel.getTname());
                    time.setText(albumModel.getUpdate_time());
                    likeBtn.setText("喜欢(" + albumModel.getLikeNum() + ")");
                    handler.sendEmptyMessage(7);
                    break;
                case 2:// 初始化教师列表,专辑详情
                    viewDetail.loadData(albumModel.getContent(), "text/html", "UTF-8");
                    teacherContain.removeAllViews();
                    for (Teacher tt : teachers) {
                        View item = LayoutInflater.from(mContext).inflate(R.layout.item_teacher,
                                null);
                        ImageView avatar = item.findViewById(R.id.item_teacher_avatar);
                        TextView name = item.findViewById(R.id.item_teacher_title);
                        CommonWebView desc = item.findViewById(R.id.item_teacher_desc);
                        Glide.with(mContext).load(tt.getPic()).into(avatar);
                        name.setText(tt.getName());
                        desc.loadData(tt.getT_desc(), "text/html", "UTF-8");
                        teacherContain.addView(item);
                    }
                    break;

                case 3:// 初始化评论列表
                    pinglunAdapter.setData(pinglunModels);
                    break;

                case 4:
                    showError(R.mipmap.blank_no_fm, R.string.no_content);
                    break;
                case 5:
                    showPlayController();
                    break;
                case 6:
                    unshowPlayController();
                    break;

                case 7:// 更新收藏、点赞状态
                    checkLikeCollect();
                    break;
                case 8:// 更新输入状态
                    inputEdit.setText("");
                    break;
                case MSG_START_PLAY: // ---------------－开始播放
                    playController.setVisibility(View.VISIBLE);
                    showPlayController();
                    playController.setPlay();
                    playController.setData(currentFmModel);

                    handler.sendEmptyMessage(MSG_REFRESH_LIST);
                    Log.e("开始播放 currentPos", currentPosition + currentFmModel.getTitle());
                    break;
                case MSG_CHANGE_TIME:
                    playController.setCurrentTime(msg.arg1);
                    break;
                case MSG_REFRESH_LIST:
                    fmAdapter.setData(fmModels, currentPosition);// 更新gif播放
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
                    if (currentPosition + 1 == fmModels.size()) {
                        playController.setPause();
                        handler.sendEmptyMessage(MSG_REFRESH_LIST);
                    } else {// 开始下一曲
                        currentPosition++;
                        changeMusic(currentPosition);
                    }
                    break;
            }
        }
    };

    /**
     * 主动换歌
     */
    private void changeMusic(int p) {
        if (!Tools.listNotNull(fmModels)) return;
        currentPosition = p;
        currentFmModel = fmModels.get(p);
        sendintent(ACTION_START_PLAY, 0);// 主动切歌
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
                intent.putExtra("play_model_position", currentPosition);
            }
            startService(intent);
        } else showToast("音频数据缺失");

    }

    // 刷新点赞、收藏状态
    private void checkLikeCollect() {
        likeBtn.setText("喜欢（" + albumModel.getLikeNum() + ")");
        if (albumModel.isLikeState()) {
            Drawable aa = mContext.getResources().getDrawable(R.mipmap.like_a);
            aa.setBounds(0, 0, aa.getMinimumWidth(), aa.getMinimumHeight());
            likeBtn.setCompoundDrawables(null, aa, null, null);
        } else {
            Drawable bb = mContext.getResources().getDrawable(R.mipmap.like_b);
            bb.setBounds(0, 0, bb.getMinimumWidth(), bb.getMinimumHeight());
            likeBtn.setCompoundDrawables(null, bb, null, null);
        }

        if (TextUtils.isEmpty(albumModel.getConllectId())) {
            Drawable cc = mContext.getResources().getDrawable(R.mipmap.shoucang_b);
            cc.setBounds(0, 0, cc.getMinimumWidth(), cc.getMinimumHeight());
            shoucangBtn.setCompoundDrawables(null, cc, null, null);
        } else {
            Drawable dd = mContext.getResources().getDrawable(R.mipmap.shoucang_a);
            dd.setBounds(0, 0, dd.getMinimumWidth(), dd.getMinimumHeight());
            shoucangBtn.setCompoundDrawables(null, dd, null, null);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.album_detail_back:
                finish();
                break;
            case R.id.ablum_detail_all://播放全部
                changeMusic(0);
                break;

            case R.id.ablum_detail_part1:
                changeTab(1);
                break;
            case R.id.ablum_detail_part2:
                changeTab(2);

                break;
            case R.id.ablum_detail_part3:
                changeTab(3);
                break;
            case R.id.ablum_detail_part4:
                changeTab(4);
                break;

            case R.id.album_detail_like:// 喜欢
                doLike();
                break;
            case R.id.album_detail_shoucang:// 收藏
                doCollect();
                break;
            case R.id.album_detail_share:// 分享
                ShareTools.doShareWeb(this, albumModel.getTitle(), albumModel.getContent(),
                        albumModel.getPic(), "http://baidu.com");
                break;

            case R.id.album_detail_inputsend:// 发送评论
                String content = inputEdit.getText().toString();
                if (!TextUtils.isEmpty(content))
                    sendComment(albumModel.getKid(), content);
                break;
        }
    }

    /**
     * 添加评论
     *
     * @param kid
     * @param content
     */
    private void sendComment(final String kid, String content) {
        new ActionApi(this, kid, "", "", content, "1", new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {
            }

            @Override
            public void setError(ErrorMsg error) {
                // 发送成功，再次获取评论列表
                if (error == null) {
                    getPinglunData(kid, "");
                    handler.sendEmptyMessage(8);
                }
            }
        });
    }

    public void showPlayController() {
        AnimationTools.with().bottomMoveToViewLocation(playController, 300);
    }

    public void unshowPlayController() {
        AnimationTools.with().moveToViewBottom(playController, 300);
    }

    @Override
    protected void getMusicMsg(Message ms) {
        handler.sendMessage(ms);
    }


    /**
     * 点赞
     * 0 取消喜欢 1 喜欢
     */
    private void doLike() {
        final int type = albumModel.isLikeState() ? 0 : 1;
        new ActionApi(this, albumModel.getKid(), type, new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {
                if (entry != null && entry instanceof AlbumModel) {
                    AlbumModel e = (AlbumModel) entry;
                    if (type == 0) {
                        albumModel.setLikeState(false);
                    } else
                        albumModel.setLikeState(true);
                    albumModel.setLikeNum(e.getLikeNum());
                    handler.sendEmptyMessage(7);
                }
            }

            @Override
            public void setError(ErrorMsg error) {

            }
        });

    }

    /**
     * 收藏
     */
    private void doCollect() {
        if (TextUtils.isEmpty(albumModel.getConllectId())) {// 添加
            new CollectionApi(this, albumModel.getKid(), 1, new FetchEntryListener() {
                @Override
                public void setData(Entry entry) {
                    if (((ErrorMsg) entry).getNo() > 0) {// 返回了收藏id
                        albumModel.setConllectId(((ErrorMsg) entry).getNo() + "");
                        handler.sendEmptyMessage(7);
                    }
                }

                @Override
                public void setError(ErrorMsg error) {
                    if (error != null)
                        showToast(R.string.collect_faild);
                }
            });

        } else {
            new CollectionApi(this, albumModel.getConllectId(), new FetchEntryListener() {
                @Override
                public void setData(Entry entry) {
                }

                @Override
                public void setError(ErrorMsg error) {
                    if (error == null) {
                        albumModel.setConllectId(null);
                        handler.sendEmptyMessage(7);
                    }

                }
            });

        }

    }
}
