package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.api.AddCommentApi;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.util.ToastUtil;

public class TopicCommitActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView feedBack;
    private TextView feedCommit;
    private EditText feedEv;
    private HashMap<String, String> comments;
    private String topic_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_commit);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        topic_id = intent.getStringExtra("topic_id");
        initView();
    }

    private void initView() {
        comments = new HashMap<>();
        feedBack = findViewById(R.id.feed_back);
        feedCommit = findViewById(R.id.feed_commit);
        feedEv = findViewById(R.id.feed_ev);
        feedBack.setOnClickListener(this);
        feedCommit.setOnClickListener(this);

    }


    private void Httpcomment() {
        comments.put("topic_id", topic_id);
        comments.put("content", feedEv.getText().toString().trim());
        new AddCommentApi(this, comments, new ResponseCallBack<String>() {
            @Override
            public void result(String message, ErrorMsg errorMsg) {

                if (errorMsg == null) {

                    ToastUtil.showShortToast("添加评论成功");
                    finish();


                } else {
                    ToastUtil.showShortToast(errorMsg.getDesc());
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.feed_back:
                finish();
                break;
            case R.id.feed_commit:
                Httpcomment();
                break;
        }
    }
}
