package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.widget.CircleImageView;

public class MessageDetailActivity extends BaseActivity {

    @BindView(R.id.image_circleview)
    CircleImageView circleImageView;
    @BindView(R.id.item_title)
    TextView itemtile;
    @BindView(R.id.time_tv)
    TextView time_tv;
    @BindView(R.id.tv_content_id)
    TextView tv_content;
    @BindView(R.id.id_back)
    ImageView idBack;
    @BindView(R.id.title_name)
    TextView titleName;
    private String content;
    private String createTime;
    private String title;
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

        initView1();

    }

    @Override
    protected void initData() {

    }


    protected void initView1() {
        titleName.setText("活动通知");
        Intent intent = getIntent();
        content = intent.getStringExtra("content");
        createTime = intent.getStringExtra("createTime");
        title = intent.getStringExtra("title");
        imageUrl = intent.getStringExtra("imageUrl");

        tv_content.setText(content);
        itemtile.setText(title);
        time_tv.setText(createTime);
        ImageLoader.display(mContext, (CircleImageView) circleImageView, imageUrl);

    }

    @OnClick({R.id.id_back})
    @Override
    public void onClick(View view) {
        finish();
    }


}
