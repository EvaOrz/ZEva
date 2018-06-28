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
import cn.com.zwwl.bayuwen.model.LessonModel;
import cn.com.zwwl.bayuwen.util.CalendarTools;
import cn.com.zwwl.bayuwen.util.ToastUtil;

/**
 * 调课申请提交
 * Created by zhumangmang at 2018/5/28 17:30
 */
public class CourseApplyActivity extends BasicActivityWithTitle {
    @BindView(R.id.unit_name)
    AppCompatTextView unitName;
    @BindView(R.id.description)
    AppCompatTextView description;
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
    LessonModel lessonModel;
    KeModel keModel;
    @BindView(R.id.stock)
    AppCompatTextView stock;
    @BindView(R.id.price)
    AppCompatTextView price;

    @Override
    protected int setContentView() {
        return R.layout.activity_course_apply;
    }

    @Override
    protected void initView() {
        setDisplayShowTitleEnabled(false);
    }

    @Override
    protected void initData() {
        lessonModel = mApplication.oldLesson;
        keModel = mApplication.newKe;
        unitName.setText(lessonModel.getTitle());
        description.setText(lessonModel.getTname());
        ImageLoader.display(this, pic, keModel.getPic());
        courseName.setText(keModel.getTitle());
        teacherName.setText(keModel.getTname());
        schoolName.setText(keModel.getSchool());
        date.setText(String.format("%s至%s", CalendarTools.format(keModel.getStartPtime(),
                "yyyy-MM-dd"), CalendarTools.format(keModel.getEndPtime(),
                "yyyy-MM-dd")));
        time.setText(String.format("%s-%s", keModel.getClass_start_at(), keModel.getClass_end_at()));
        stock.setText(String.format("剩余名额: %s", mApplication.newKe.getNum()));
        price.setText(String.format("￥ %s", mApplication.newKe.getBuyPrice()));
    }

    @Override
    protected void setListener() {

    }

    @OnClick(R.id.submit)
    @Override
    public void onClick(View view) {
        map.put("origin_kid", lessonModel.getKid());
        map.put("origin_lecture", lessonModel.getId());
        map.put("target_kid", keModel.getKid());
        map.put("target_lecture", mApplication.newLesson.getId());
        map.put("type", "2");
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
