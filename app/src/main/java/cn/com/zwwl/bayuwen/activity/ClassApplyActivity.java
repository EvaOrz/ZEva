package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.api.AddCourseApi;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.CommonModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.util.ToastUtil;

/**
 * 转班申请提交
 * Created by zhumangmang at 2018/5/28 17:30
 */
public class ClassApplyActivity extends BasicActivityWithTitle {


    @BindView(R.id.old)
    AppCompatTextView old;
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
    @BindView(R.id.o_arrow)
    AppCompatImageView oArrow;
    @BindView(R.id.now)
    AppCompatTextView now;
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
        bind(mApplication.oldKe, oPic, oCourseName, oTeacherName, oSchoolName, oDate, oTime);
        bind(mApplication.newKe, pic, courseName, teacherName, schoolName, date, time);
    }

    public void bind(KeModel keModel, AppCompatImageView pic, AppCompatTextView courseName,
                     AppCompatTextView teacherName, AppCompatTextView schoolName, AppCompatTextView date, AppCompatTextView time) {
        ImageLoader.display(this, pic, keModel.getPic());
        courseName.setText(keModel.getTitle());
        teacherName.setText(keModel.getTname());
        schoolName.setText(keModel.getSchool());
        date.setText(CalendarTools.format(Long.valueOf(keModel.getStartPtime()),
                "yyyy-MM-dd") + " 至 " + CalendarTools.format(Long.valueOf(keModel.getEndPtime()),
                "yyyy-MM-dd"));
        time.setText(keModel.getClass_start_at() + "-" + keModel.getClass_end_at());
    }

    @Override
    protected void setListener() {

    }

    @OnClick(R.id.submit)
    @Override
    public void onClick(View view) {
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
    }

    @Override
    public void close() {
        finish();
    }

}
