package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.api.AddCourseApi;
import cn.com.zwwl.bayuwen.api.ClassDetailApi;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ClassModel;
import cn.com.zwwl.bayuwen.model.CommonModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.util.TimeUtil;
import cn.com.zwwl.bayuwen.util.ToastUtil;

/**
 * 转班申请提交
 * Created by zhumangmang at 2018/5/28 17:30
 */
public class ClassApplyActivity extends BasicActivityWithTitle {
    @BindView(R.id.o_pic)
    AppCompatImageView oPic;
    @BindView(R.id.o_course_name)
    AppCompatTextView oCourseName;
    @BindView(R.id.o_teacher_name)
    AppCompatTextView oTeacherName;
    @BindView(R.id.o_school_name)
    AppCompatTextView oSchoolName;
    @BindView(R.id.o_date)
    AppCompatTextView oDate;
    @BindView(R.id.o_time)
    AppCompatTextView oTime;
    @BindView(R.id.pic)
    AppCompatImageView pic;
    @BindView(R.id.course_name)
    AppCompatTextView courseName;
    @BindView(R.id.teacher_name)
    AppCompatTextView teacherName;
    @BindView(R.id.school_name)
    AppCompatTextView schoolName;
    @BindView(R.id.date)
    AppCompatTextView date;
    @BindView(R.id.time)
    AppCompatTextView time;
    @BindView(R.id.stock)
    AppCompatTextView stock;
    @BindView(R.id.price)
    AppCompatTextView price;
    KeModel oldKe, newKe;

    @Override
    protected int setContentView() {
        return R.layout.activity_class_apply;
    }

    @Override
    protected void initView() {
        setDisplayShowTitleEnabled(false);
    }

    @Override
    protected void initData() {
        setCustomTitle("提交申请");
        newKe = mApplication.newKe;
        getDetail();
        bind(newKe, pic, courseName, teacherName, schoolName, date, time);
        stock.setText(String.format("剩余名额: %s", newKe.getNum()));
        price.setText(String.format("￥ %s", newKe.getBuyPrice()));
    }

    private void getDetail() {
        new ClassDetailApi(this, mApplication.oldKe.getKid(), new ResponseCallBack<ClassModel>() {
            @Override
            public void result(ClassModel classModel, ErrorMsg errorMsg) {
                if (classModel != null) {
                    oldKe = classModel.getCourse();
                    bind(oldKe, oPic, oCourseName, oTeacherName, oSchoolName, oDate, oTime);
                } else {
                    ToastUtil.showShortToast(errorMsg.getDesc());
                }
            }
        });
    }

    public void bind(KeModel keModel, AppCompatImageView pic, AppCompatTextView courseName,
                     AppCompatTextView teacherName, AppCompatTextView schoolName, AppCompatTextView date, AppCompatTextView time) {
        ImageLoader.display(this, pic, keModel.getPic());
        courseName.setText(keModel.getTitle());
        teacherName.setText(keModel.getTname());
        schoolName.setText(keModel.getSchool());
        date.setText(String.format("%s至%s", CalendarTools.format(keModel.getStartPtime(),
                "yyyy-MM-dd"), CalendarTools.format(keModel.getEndPtime(),
                "yyyy-MM-dd")));
        time.setText(String.format("%s-%s", TimeUtil.parseToHm(keModel.getClass_start_at()), TimeUtil.parseToHm(keModel.getClass_end_at())));
    }

    @Override
    protected void setListener() {

    }

    @OnClick({R.id.submit, R.id.o_pic_layout, R.id.pic_layout})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                map.put("origin_kid", mApplication.oldKe.getKid());
                map.put("target_kid", mApplication.newKe.getKid());
                map.put("type", "1");
                new AddCourseApi(this, map, new ResponseCallBack<CommonModel>() {
                    @Override
                    public void result(CommonModel commonModel, ErrorMsg errorMsg) {
                        if (errorMsg == null) {
                            startActivity(new Intent(mActivity, ChangeResultActivity.class));
                        } else {
                            ToastUtil.showShortToast(errorMsg.getDesc());
                        }
                    }
                });
                break;
            case R.id.o_pic_layout:
                Intent i = new Intent(mContext, VideoPlayActivity.class);
                i.putExtra("VideoPlayActivity_url", oldKe.getVideo());
                i.putExtra("VideoPlayActivity_pic", oldKe.getPic());
                startActivity(i);
                break;
            case R.id.pic_layout:
                Intent intent = new Intent(mContext, VideoPlayActivity.class);
                intent.putExtra("VideoPlayActivity_url", newKe.getVideo());
                intent.putExtra("VideoPlayActivity_pic", newKe.getPic());
                startActivity(intent);
                break;

        }

    }

    @Override
    public void close() {
        finish();
    }
}
