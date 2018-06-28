package cn.com.zwwl.bayuwen.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.TeacherAnswerAdapter;
import cn.com.zwwl.bayuwen.adapter.TopicUserAnswerAdapter;
import cn.com.zwwl.bayuwen.api.AddCommentApi;
import cn.com.zwwl.bayuwen.api.CancelVoteApi;
import cn.com.zwwl.bayuwen.api.TopicDetailApi;
import cn.com.zwwl.bayuwen.api.UrlUtil;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.TopicDetailModel;
import cn.com.zwwl.bayuwen.util.ToastUtil;
import cn.com.zwwl.bayuwen.widget.CircleImageView;
import cn.com.zwwl.bayuwen.widget.NoScrollListView;

public class TopicDetailActivity extends BasicActivityWithTitle implements View.OnClickListener {

    @BindView(R.id.topic_title_name_id)
    TextView topicTitleNameId;
    @BindView(R.id.topic_circleimageview_id)
    CircleImageView topicCircleimageviewId;
    @BindView(R.id.name_id)
    TextView nameId;
    @BindView(R.id.cource_name_id)
    TextView courceNameId;
    @BindView(R.id.date_id)
    TextView dateId;
    @BindView(R.id.topic_content_id)
    TextView topicContentId;
    @BindView(R.id.teacher_asked_id)
    NoScrollListView teacherAskedId;
    @BindView(R.id.jiaoshihuifu)
    TextView jiaoshihuifu;
    @BindView(R.id.jiazhanghuifu)
    TextView jiazhanghuifu;
    @BindView(R.id.user_asked_id)
    NoScrollListView userAskedId;
    @BindView(R.id.collection_icon_id)
    ImageView collectionIconId;
    @BindView(R.id.collect_number)
    TextView collectNumber;
    @BindView(R.id.comment_tv)
    TextView commentTv;
    @BindView(R.id.feed_ev)
    EditText feedEv;
    private boolean flag = false;
    private HashMap<String, String> params;
    private HashMap<String, String> comments;


    private String topic_id;    // 话题ID
    private String topicUrl = UrlUtil.getTopicMessage();
    private TopicDetailModel topicDetailModel;
    private List<TopicDetailModel.TeacherCommentBean> teacherCommentBeans;
    private List<TopicDetailModel.UserCommentBean> userCommentBeans;
    private TeacherAnswerAdapter teacherAnswerAdapter; //教师回复的adapter
    private TopicUserAnswerAdapter topicUserAnswerAdapter;//家长回复


    @Override
    protected int setContentView() {
        return R.layout.activity_topic_detail;
    }

    @Override
    protected void initView() {
        setCustomTitle("话题详情");

        params = new HashMap<>();
        comments = new HashMap<>();
        Intent intent = getIntent();
        topic_id = intent.getStringExtra("topicId");
        collectionIconId.setOnClickListener(this);
        commentTv.setOnClickListener(this);
        HttpData(topic_id);
    }

    private void HttpData(final String topic_id) {
        new TopicDetailApi(this, topicUrl, topic_id, new ResponseCallBack<TopicDetailModel>() {
            @Override
            public void result(TopicDetailModel messageModel, ErrorMsg errorMsg) {
                if (messageModel != null) {
                    topicDetailModel = messageModel;
                    if (topicDetailModel.getTopic_name() != null) {
                        topicTitleNameId.setText(topicDetailModel.getTopic_name());
                    }
                    if (topicDetailModel.getUser_pic() != null)
                        ImageLoader.display(TopicDetailActivity.this, topicCircleimageviewId, topicDetailModel.getUser_pic());
                    else
                        ImageLoader.display(TopicDetailActivity.this, topicCircleimageviewId, R.mipmap.ic_launcher);


                    nameId.setText(topicDetailModel.getUser_name());
                    courceNameId.setText(topicDetailModel.getCourse_name());
                    dateId.setText(topicDetailModel.getTopic_create_at());
                    topicContentId.setText(topicDetailModel.getTopic_content());
                    collectNumber.setText(topicDetailModel.getVote_num()); //点赞数

                    if (topicDetailModel.getIs_vote() == 1) {
                        collectionIconId.setBackgroundResource(R.mipmap.collection_icon);
                        flag = true;
                    } else {
                        collectionIconId.setBackgroundResource(R.mipmap.no_collection_icon);
                        flag = false;
                    }
                    teacherCommentBeans = topicDetailModel.getTeacher_comment();
                    userCommentBeans = topicDetailModel.getUser_comment();

                    if (teacherCommentBeans.size() != 0 && teacherCommentBeans != null) {   //教师回复绑定
                        jiaoshihuifu.setVisibility(View.VISIBLE);
                        teacherAskedId.setVisibility(View.VISIBLE);
                        teacherAnswerAdapter = new TeacherAnswerAdapter(TopicDetailActivity.this, teacherCommentBeans, topicDetailModel.getTopic_name());
                        teacherAskedId.setAdapter(teacherAnswerAdapter);
                    } else {
                        jiaoshihuifu.setVisibility(View.GONE);
                        teacherAskedId.setVisibility(View.GONE);
                    }

                    if (userCommentBeans.size() != 0 && userCommentBeans != null) {
                        jiazhanghuifu.setVisibility(View.VISIBLE);
                        userAskedId.setVisibility(View.VISIBLE);
                        topicUserAnswerAdapter = new TopicUserAnswerAdapter(TopicDetailActivity.this, userCommentBeans);
                        userAskedId.setAdapter(topicUserAnswerAdapter);
                    } else {
                        jiazhanghuifu.setVisibility(View.GONE);
                        userAskedId.setVisibility(View.GONE);
                    }

                } else {
                    ToastUtil.showShortToast(errorMsg.getDesc());
                }
            }


        });

    }

    @Override
    protected void initData() {
        feedEv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    Httpcomment(v.getText().toString().trim());
                    feedEv.setVisibility(View.GONE);
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.collection_icon_id:
                params.put("topic_id", topic_id);
                if (flag == true) {
                    cancelData(flag);

                } else {
                    cancelData(flag);

                }
                break;
            case R.id.comment_tv:
                initPopWindow();   //弹出popwindow
                break;


        }
    }


    private void initPopWindow() {   //评论的popwindow
        View popView = View.inflate(this, R.layout.pop_comment_topic_layout, null);

        EditText comment = popView.findViewById(R.id.feed_ev);

        final PopupWindow popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        popupWindow.setFocusable(true);
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.setOutsideTouchable(true);// 设置同意在外点击消失
        popupWindow.setAnimationStyle(R.style.fetch_image_popup_anim);
        comment.setFocusable(true);
        comment.setFocusableInTouchMode(true);
        comment.requestFocus();
        InputMethodManager imm = (InputMethodManager) TopicDetailActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);

        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAtLocation(this.getWindow().getDecorView(), Gravity.CENTER_HORIZONTAL, 30, 30);


        comment.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {

                    Httpcomment(v.getText().toString().trim());
                    popupWindow.dismiss();
                    return true;
                }
                return false;
            }
        });


    }
    /**
     * EditText获取焦点并显示软键盘
     */
    /**
     * 显示软键盘
     *
     * @param context
     * @param view
     */
    public static void showInputMethodForQuery(final Context context, final View view) {

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm != null) {
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        }
    }

    private void Httpcomment(String trim) {
        comments.put("topic_id", topic_id);
        comments.put("content", trim);
        new AddCommentApi(this, comments, new ResponseCallBack<String>() {
            @Override
            public void result(String message, ErrorMsg errorMsg) {

                if (errorMsg == null) {

                    ToastUtil.showShortToast("添加评论成功");

                    HttpData(topic_id); //添加评论

                } else {
                    ToastUtil.showShortToast(errorMsg.getDesc());
                }
            }
        });


    }

    private void cancelData(final boolean flag) {   //取消收藏的方法
        new CancelVoteApi(this, params, new ResponseCallBack<String>() {
            @Override
            public void result(String message, ErrorMsg errorMsg) {

                if (errorMsg == null) {
                    if (flag == true) {
                        ToastUtil.showShortToast("取消点赞");
                        HttpData(topic_id); //取消收藏后
                    } else {
                        ToastUtil.showShortToast("点赞成功");
                        HttpData(topic_id);
                    }
                } else {
                    ToastUtil.showShortToast(errorMsg.getDesc());
                }
            }


        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void close() {
        super.close();
        finish();
    }
}
