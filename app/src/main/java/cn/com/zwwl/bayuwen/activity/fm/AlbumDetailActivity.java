package cn.com.zwwl.bayuwen.activity.fm;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.activity.BaseActivity;
import cn.com.zwwl.bayuwen.activity.PayActivity;
import cn.com.zwwl.bayuwen.adapter.FmAdapter;
import cn.com.zwwl.bayuwen.adapter.PinglunAdapter;
import cn.com.zwwl.bayuwen.api.ActionApi;
import cn.com.zwwl.bayuwen.api.CourseApi;
import cn.com.zwwl.bayuwen.api.fm.AlbumApi;
import cn.com.zwwl.bayuwen.api.fm.CollectionApi;
import cn.com.zwwl.bayuwen.api.fm.PinglunApi;
import cn.com.zwwl.bayuwen.base.Constance;
import cn.com.zwwl.bayuwen.dialog.AskDialog;
import cn.com.zwwl.bayuwen.listener.FetchEntryListListener;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.fm.AlbumModel;
import cn.com.zwwl.bayuwen.model.fm.AlbumModel.*;
import cn.com.zwwl.bayuwen.model.fm.FmModel;
import cn.com.zwwl.bayuwen.model.fm.PinglunModel;
import cn.com.zwwl.bayuwen.service.NewMusicService;
import cn.com.zwwl.bayuwen.util.ShareTools;
import cn.com.zwwl.bayuwen.util.Tools;
import cn.com.zwwl.bayuwen.util.UmengLogUtil;
import cn.com.zwwl.bayuwen.view.music.MusicWindow;
import cn.com.zwwl.bayuwen.widget.CommonWebView;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.widget.CallScrollView;
import cn.com.zwwl.bayuwen.widget.NoScrollListView;

import static cn.com.zwwl.bayuwen.base.Constance.OVERLAY_PERMISSION_REQ_CODE;

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
    private ImageView likeImg, collectImg;
    private TextView likeTv;
    private TextView title, name, time;
    private View viewPlay, viewPinglun, viewTeacher;
    private CommonWebView viewDetail;
    private LinearLayout contain, teacherContain;
    private TextView part1, part2, part3, part4;
    private View line1, line2, line3, line4;
    private MusicStatusReceiver musicStatusReceiver;
    private LinearLayout inputLayout;// 输入框
    private TextView buyBt;
    private TextView tagTv;

    private FmModel currentFmModel;// 当前正在播放的音频
    private int currentPosition = -1;// 当前正在播放的音频在列表中的位置
    /**
     * 拦截一种情况：从主页面来回主页面去
     */
    private boolean isFromMain = false, isGoMain = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_detail);
        requestDrawOverLays();
        if (getIntent().getSerializableExtra("AlbumDetailActivity_data") != null && getIntent()
                .getSerializableExtra("AlbumDetailActivity_data") instanceof String)
            aId = getIntent().getStringExtra("AlbumDetailActivity_data");
        isFromMain = getIntent().getBooleanExtra("is_from_main", false);
        initView();
        initErrorLayout();
        initData();
    }

    @Override
    public void initData() {
        showLoadingDialog(true);
        new AlbumApi(this, aId, new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {
                showLoadingDialog(false);
                if (entry != null && entry instanceof AlbumModel) {
                    albumModel = (AlbumModel) entry;
                    handler.sendEmptyMessage(1);
                    fmModels.clear();
                    fmModels.addAll(albumModel.getFmModels());
                    checkCurrent();
                    teachers.clear();
                    teachers.addAll(albumModel.getTeachers());
                    handler.sendEmptyMessage(2);
                    getPinglunData(albumModel.getKid(), "");
                } else {


                }
            }

            @Override
            public void setError(ErrorMsg error) {
                showLoadingDialog(false);
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


    private void checkCurrent() {
        for (int i = 0; i < fmModels.size(); i++) {
            if (MusicWindow.getInstance(this).currentFmModel != null && fmModels.get(i).getId()
                    .equals(MusicWindow.getInstance(this).currentFmModel
                            .getId())) {
                currentPosition = i;
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


    private void initView() {
        findViewById(R.id.album_detail_back).setOnClickListener(this);
        contain = findViewById(R.id.ablum_detail_contain);
        imageView = findViewById(R.id.album_detail_img);
        title = findViewById(R.id.album_detail_t);
        name = findViewById(R.id.album_detail_name);
        time = findViewById(R.id.album_detail_time);
        likeImg = findViewById(R.id.album_detail_like_img);
        likeTv = findViewById(R.id.album_detail_like_tv);
        collectImg = findViewById(R.id.album_detail_shoucang_img);
        tagTv = findViewById(R.id.album_detail_tag);

        inputLayout = findViewById(R.id.album_detail_input);
        inputEdit = findViewById(R.id.album_detail_inputedit);
        buyBt = findViewById(R.id.album_buy);
        buyBt.setOnClickListener(this);

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
        findViewById(R.id.album_detail_like).setOnClickListener(this);
        findViewById(R.id.album_detail_shoucang).setOnClickListener(this);
        findViewById(R.id.album_detail_share).setOnClickListener(this);
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
                if (fmModels.get(position).getStatus() == 1) {
                    getKeModel();
                } else {
                    currentPosition = position;
                    currentFmModel = fmModels.get(position);
                    checkLoading(position);
                    sendintent(ACTION_START_PLAY);
                }

            }
        });

        fmAdapter = new FmAdapter(this);
        fmListView.setAdapter(fmAdapter);
        pinglunAdapter = new PinglunAdapter(this);
        pingListView.setAdapter(pinglunAdapter);

    }

    private void changeTab(int type) {
        if (type == 1) {
            part1.setTextColor(getResources().getColor(R.color.lisichen));
            line1.setVisibility(View.VISIBLE);
        } else {
            part1.setTextColor(getResources().getColor(R.color.gray_dark));
            line1.setVisibility(View.INVISIBLE);
        }
        if (type == 2) {
            part2.setTextColor(getResources().getColor(R.color.lisichen));
            line2.setVisibility(View.VISIBLE);
        } else {
            part2.setTextColor(getResources().getColor(R.color.gray_dark));
            line2.setVisibility(View.INVISIBLE);
        }
        if (type == 3) {
            part3.setTextColor(getResources().getColor(R.color.lisichen));
            line3.setVisibility(View.VISIBLE);
        } else {
            part3.setTextColor(getResources().getColor(R.color.gray_dark));
            line3.setVisibility(View.INVISIBLE);
        }
        if (type == 4) {
            part4.setTextColor(getResources().getColor(R.color.lisichen));
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
                case 1:// 初始化专辑信息
                    disProcess();
                    if (!TextUtils.isEmpty(albumModel.getPic()))
                        Glide.with(mContext)
                                .load(albumModel.getPic())
                                .into(imageView);
                    title.setText(albumModel.getTitle());
                    name.setText(albumModel.getTname());
                    time.setText("更新时间：" + albumModel.getUpdate_time());
                    tagTv.setText(albumModel.getType());
                    likeTv.setText("喜欢(" + albumModel.getLikeNum() + ")");
                    handler.sendEmptyMessage(7);

                    if (albumModel.getBuyPrice() > 0 && !albumModel.isIs_buy()) {
                        buyBt.setVisibility(View.VISIBLE);
                        buyBt.setText("￥ " + albumModel.getBuyPrice() + "  立即购买");
                    }
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
                    break;
                case 6:
                    break;

                case 7:// 更新收藏、点赞状态
                    checkLikeCollect();
                    break;
                case 8:// 更新输入状态
                    inputEdit.setText("");
                    break;
                case MSG_START_PLAY: // ---------------－开始播放
                    checkCurrent();
                    break;
                case MSG_REFRESH_LIST:
                    fmAdapter.setData(fmModels);// 更新gif播放
                    break;
                case MSG_COMPLETE:// -------------------播放完成
                    checkLoading(-1);
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
        sendintent(ACTION_START_PLAY);// 主动切歌
    }

    /**
     * 绑定intent
     */
    private void sendintent(String action) {
        if (currentFmModel != null) {
            Intent intent = new Intent(this, NewMusicService.class);
            intent.setAction(action);
            if (action.equals(ACTION_START_PLAY)) {
                intent.putExtra("play_model", albumModel);
                intent.putExtra("play_model_position", currentPosition);
            }
            startService(intent);
        } else showToast("音频数据缺失");

    }

    // 刷新点赞、收藏状态
    private void checkLikeCollect() {
        likeTv.setText("喜欢(" + albumModel.getLikeNum() + ")");
        if (albumModel.isLikeState()) {
            likeImg.setImageResource(R.mipmap.like_b);
        } else {
            likeImg.setImageResource(R.mipmap.like_a);
        }

        if (albumModel.getConllectId() == 0) {
            collectImg.setImageResource(R.mipmap.shoucang_b);
        } else {
            collectImg.setImageResource(R.mipmap.shoucang_a);
        }
    }

    /**

     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isFromMain) isGoMain = true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.album_detail_back:
                if (isFromMain) isGoMain = true;
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
                UmengLogUtil.logFmLikeClick(mContext);
                doLike();
                break;
            case R.id.album_detail_shoucang:// 收藏
                UmengLogUtil.logFmCollectClick(mContext);
                doCollect();
                break;
            case R.id.album_detail_share:// 分享
                if (albumModel != null)
                    ShareTools.doShareWebWithPic(this, albumModel.getPic(),
                            albumModel.getTitle(), albumModel.getShareUrl(),
                            albumModel.getTname() + "_更新时间：" + albumModel.getUpdate_time());
                break;

            case R.id.album_detail_inputsend:// 发送评论
                String content = inputEdit.getText().toString();
                if (!TextUtils.isEmpty(content))
                    sendComment(albumModel.getKid(), content);
                break;
            case R.id.album_buy:// 购买
                getKeModel();
                break;
        }
    }

    private void getKeModel() {
        showLoadingDialog(true);
        new CourseApi(mContext, albumModel.getKid(), new FetchEntryListener() {
            @Override
            public void setData(Entry entry) {
                showLoadingDialog(false);
                if (entry != null && entry instanceof KeModel) {
                    KeModel keModel = (KeModel) entry;
                    Intent j = new Intent(mContext, PayActivity.class);
                    j.putExtra("TuanPayActivity_type", 5);
                    j.putExtra("TuanPayActivity_data", keModel);
                    startActivity(j);
                }

            }

            @Override
            public void setError(ErrorMsg error) {
                showLoadingDialog(false);
                if (error != null) showToast(error.getDesc());
            }
        });
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
                } else
                    showToast(error.getDesc());
            }
        });
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
        if (albumModel.getConllectId() == 0) {// 添加

            new CollectionApi(mContext, albumModel.getKid(), 2, new FetchEntryListener() {
                @Override
                public void setData(Entry entry) {
                    showLoadingDialog(false);
                    if (entry != null && entry instanceof AlbumModel) {
                        albumModel.setConllectId(((AlbumModel) entry).getConllectId());
                        handler.sendEmptyMessage(7);
                        showToast("收藏成功");
                    }
                }

                @Override
                public void setError(ErrorMsg error) {
                    showLoadingDialog(false);
                    if (error != null)
                        showToast(error.getDesc());
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
                        albumModel.setConllectId(0);
                        handler.sendEmptyMessage(7);
                        showToast("取消收藏成功");
                    }

                }
            });

        }

    }

    /**
     * 监听音乐播放Service的receiver
     */
    class MusicStatusReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // 开始播放、播放完成、更新时间
            if (intent.getAction().equals(ACTION_START_PLAY) || intent.getAction().equals
                    (ACTION_MSG_COMPLETE)) {
                handler.sendMessage((Message) intent.getParcelableExtra("music_service_message"));

            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (musicStatusReceiver != null)
            unregisterReceiver(musicStatusReceiver);
        if (isFromMain && isGoMain)
            return;
        MusicWindow.getInstance(this).hidePopupWindow();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, Constance.OVERLAY_PERMISSION_REQ_CODE_RESUME);
            }
        } else {
            MusicWindow.getInstance(this).movetoController(0);
            registerReceiver();//先恢复数据 再注册receiver
            checkCurrent();
        }

    }


    @TargetApi(Build.VERSION_CODES.M)
    public void requestDrawOverLays() {
        if (!Settings.canDrawOverlays(this)) {
            new AskDialog(mContext, "去开启", "不需要", "音乐播放器", new AskDialog.OnSurePickListener() {
                @Override
                public void onSure() {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse
                            ("package:" + AlbumDetailActivity.this.getPackageName()));
                    startActivityForResult(intent, Constance.OVERLAY_PERMISSION_REQ_CODE);
                }

                @Override
                public void onCancle() {

                }
            });

        } else {
            // Already hold the SYSTEM_ALERT_WINDOW permission, do addview or something.
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constance.OVERLAY_PERMISSION_REQ_CODE:
//                if (!Settings.canDrawOverlays(this)) {
//                // SYSTEM_ALERT_WINDOW permission not granted...
//            } else {
//                // Already hold the SYSTEM_ALERT_WINDOW permission, do addview or something.
//            }
                break;
            case Constance.OVERLAY_PERMISSION_REQ_CODE_RESUME:
                MusicWindow.getInstance(this).movetoController(0);
                registerReceiver();//先恢复数据 再注册receiver
                checkCurrent();
                break;
        }
//        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
//            if (!Settings.canDrawOverlays(this)) {
//                // SYSTEM_ALERT_WINDOW permission not granted...
//            } else {
//                // Already hold the SYSTEM_ALERT_WINDOW permission, do addview or something.
//            }
//        }
    }
}
