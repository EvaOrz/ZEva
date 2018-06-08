package cn.com.zwwl.bayuwen.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;

/**
 * 教学总结
 * Created by zhumangmang at 2018/6/8 19:40
 */
public class TeacherSummaryActivity extends BasicActivityWithTitle {
    @BindView(R.id.content)
    AppCompatTextView content;

    @Override
    protected int setContentView() {
        return R.layout.activity_teacher_teacher;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        switch (getIntent().getIntExtra("type", 0)) {
            case 0:
                setCustomTitle("教师评语");
                break;
            case 1:
                setCustomTitle("助教评语");
                break;
            default:
                setCustomTitle("学术顾问评语");
                break;
        }
        content.setText(getIntent().getStringExtra("content"));
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
