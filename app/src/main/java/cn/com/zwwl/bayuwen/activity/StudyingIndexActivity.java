package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;
import cn.com.zwwl.bayuwen.util.ToastUtil;

/**
 * 课程跟踪正在进行课程点击箭头进入该界面
 * Created by zhumangmang at 2018/6/27 11:11
 */
public class StudyingIndexActivity extends BasicActivityWithTitle {
    @BindView(R.id.logo)
    AppCompatImageView logo;
    @BindView(R.id.course_name)
    AppCompatTextView courseName;
    @BindView(R.id.course_code)
    AppCompatTextView courseCode;
    @BindView(R.id.teacher_name)
    AppCompatTextView teacherName;
    @BindView(R.id.school_name)
    AppCompatTextView schoolName;
    @BindView(R.id.date)
    AppCompatTextView date;
    @BindView(R.id.time)
    AppCompatTextView time;
    @BindView(R.id.sign_per)
    AppCompatTextView signPer;
    @BindView(R.id.no_sign)
    AppCompatTextView noSign;
    private String kid;
    private int online;

    @Override
    protected int setContentView() {
        return R.layout.activity_studying_index;
    }

    @Override
    protected void initView() {
    }

    @Override
    public boolean setParentScrollable() {
        return true;
    }

    @Override
    protected void initData() {
        kid = getIntent().getStringExtra("kid");
        setCustomTitle(getIntent().getStringExtra("title"));
        online = getIntent().getIntExtra("online", -1);
    }

    @Override
    protected void setListener() {

    }

    @OnClick({R.id.course_change, R.id.class_covert, R.id.class_seat})
    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.course_change:
                if (online == 1) {
                    ToastUtil.showShortToast("线上班级暂不支持调课");
                    return;
                }
                mApplication.operate_type = 0;
                intent.putExtra("kid", kid);
                intent.putExtra("course_type", 0);
                intent.setClass(this, UnitTableActivity.class);
                startActivity(intent);
                break;
            case R.id.class_covert:
                if (online == 1) {
                    ToastUtil.showShortToast("线上班级暂不支持换班");
                    return;
                }
                mApplication.operate_type = 1;
                intent.setClass(this, ConvertClassActivity.class);
                startActivity(intent);
                break;
            default:
                ToastUtil.showShortToast("敬请期待");
                break;
        }
    }

    @Override
    public void close() {
        finish();
    }
}
