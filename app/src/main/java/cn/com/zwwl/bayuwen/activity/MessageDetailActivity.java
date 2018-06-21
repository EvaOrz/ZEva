package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.widget.CircleImageView;

public class MessageDetailActivity extends BasicActivityWithTitle {

    @BindView(R.id.image_circleview)
    CircleImageView circleImageView;
    @BindView(R.id.item_title)
    TextView itemtile;
    @BindView(R.id.time_tv)
    TextView time_tv;
    @BindView(R.id.tv_content_id)
    TextView tv_content;
    private String content;
    private String createTime;
    private String title;
    private String imageUrl;

    @Override
    protected int setContentView() {
        return R.layout.activity_message_detail;
    }

    @Override
    protected void initView() {
        setCustomTitle("活动通知");
        setDisplayShowTitleEnabled(false);
    }

    @Override
    protected void initData() {
        Intent intent=getIntent();
        content=intent.getStringExtra("content");
        createTime =intent.getStringExtra("createTime");
        title =intent.getStringExtra("title");
        imageUrl=intent.getStringExtra("imageUrl");

        tv_content.setText(content);
        itemtile.setText(title);
        time_tv.setText(createTime);
        ImageLoader.display(mContext,(CircleImageView) circleImageView,imageUrl);

    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void close() {
        super.close();
        finish();
    }
}
