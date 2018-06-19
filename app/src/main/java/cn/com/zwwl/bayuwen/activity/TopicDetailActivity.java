package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.TeacherAnswerAdapter;
import cn.com.zwwl.bayuwen.adapter.TopicUserAnswerAdapter;
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

public class TopicDetailActivity extends BasicActivityWithTitle implements View.OnClickListener{

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
    private  boolean flag=false;
    private HashMap<String ,String> params;

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
        setCustomTitle("话题名称");

        params=new HashMap<>();
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


                    nameId.setText(topicDetailModel.getUser_relName());
                    courceNameId.setText(topicDetailModel.getCourse_name());
                    dateId.setText(topicDetailModel.getTopic_create_at());
                    topicContentId.setText(topicDetailModel.getTopic_content());
                    collectNumber.setText(topicDetailModel.getVote_num()); //点赞数

                    if (topicDetailModel.getIs_vote()==1){
                        collectionIconId.setBackgroundResource(R.mipmap.collection_icon);
                        flag=true;
                    }else {
                        collectionIconId.setBackgroundResource(R.mipmap.no_collection_icon);
                        flag=false;
                    }
                    teacherCommentBeans = topicDetailModel.getTeacher_comment();
                    userCommentBeans = topicDetailModel.getUser_comment();

                    if (teacherCommentBeans.size() != 0&&teacherCommentBeans!=null) {   //教师回复绑定
                        jiaoshihuifu.setVisibility(View.VISIBLE);
                        teacherAskedId.setVisibility(View.VISIBLE);
                        teacherAnswerAdapter = new TeacherAnswerAdapter(TopicDetailActivity.this, teacherCommentBeans, topicDetailModel.getTopic_name());
                        teacherAskedId.setAdapter(teacherAnswerAdapter);
                    } else {
                        jiaoshihuifu.setVisibility(View.GONE);
                        teacherAskedId.setVisibility(View.GONE);
                    }

                    if (userCommentBeans.size() != 0&&userCommentBeans!=null) {
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

    }

    @Override
    protected void setListener() {

    }
    @Override
    public void onClick(View view) {
       switch (view.getId()){
           case R.id.collection_icon_id:
                params.put("topic_id",topic_id);
               if (flag==true){
                  cancelData(flag);

               }else {
                   cancelData(flag);

               }
               break;
           case R.id.comment_tv:
               initPopWindow();
               break;
               

       }
    }

    private void initPopWindow() {
    }

    private void cancelData(final boolean flag) {   //取消收藏的方法
        new CancelVoteApi(this,params,new ResponseCallBack<String>() {
            @Override
            public void result(String message, ErrorMsg errorMsg) {

                if (errorMsg==null){
                  if (flag==true) {
                      ToastUtil.showShortToast("取消收藏");
                      HttpData(topic_id); //取消收藏后
                  }else {
                      ToastUtil.showShortToast("收藏成功");
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
