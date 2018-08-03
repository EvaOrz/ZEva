package cn.com.zwwl.bayuwen.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.api.CourseStateApi;
import cn.com.zwwl.bayuwen.api.StudyingClassInfoApi;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;
import cn.com.zwwl.bayuwen.dialog.FinalEvalDialog;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ClassModel;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.LessonModel;
import cn.com.zwwl.bayuwen.util.TimeUtil;
import cn.com.zwwl.bayuwen.util.ToastUtil;
import cn.com.zwwl.bayuwen.util.UmengLogUtil;
import cn.com.zwwl.bayuwen.view.OvalImageview;
import cn.com.zwwl.bayuwen.widget.CircleImageView;

/**
 * 课程跟踪正在进行课程点击箭头进入该界面
 * Created by zhumangmang at 2018/6/27 11:11
 */
public class StudyingIndexActivity extends BaseActivity {
    @BindView(R.id.logo)
    OvalImageview logo;
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
    @BindView(R.id.sign_per)
    AppCompatTextView signPer;
    @BindView(R.id.no_sign)
    AppCompatTextView noSign;
    @BindView(R.id.sign_logo)
    AppCompatImageView signLogo;
    @BindView(R.id.course_logo)
    AppCompatImageView courseLogo;
    @BindView(R.id.class_logo)
    AppCompatImageView classLogo;
    @BindView(R.id.seat_logo)
    AppCompatImageView seatLogo;
    @BindView(R.id.sign_title)
    AppCompatTextView signTitle;
    @BindView(R.id.course_title)
    AppCompatTextView courseTitle;
    @BindView(R.id.class_title)
    AppCompatTextView classTitle;
    @BindView(R.id.seat_title)
    AppCompatTextView seatTitle;
    @BindView(R.id.id_back)
    ImageView idBack;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.main_layout)
    LinearLayout main_layout;
    private String kid;
    private int online;
    private ClassModel classModel;
    private MyApplication mApplication;
    private Activity mActivity;
    private FinalEvalDialog evalDialog;
    private CourseStateApi.CourseStateModel courseStateModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studying_index);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        mApplication = (MyApplication) getApplication();
        mActivity = this;
        initView();
        initData();
    }

    @Override
    protected void initData() {
        new StudyingClassInfoApi(this, kid, new ResponseCallBack<ClassModel>() {
            @Override
            public void result(ClassModel model, ErrorMsg errorMsg) {
                if (model != null) {
                    classModel = model;
                    bindData();
                }
            }
        });
        new CourseStateApi(this, kid, new ResponseCallBack<CourseStateApi.CourseStateModel>() {
            @Override
            public void result(CourseStateApi.CourseStateModel c, ErrorMsg
                    errorMsg) {
                if (errorMsg != null) ToastUtil.showShortToast(errorMsg.getDesc());
                else {
                    courseStateModel = c;
                    handler.sendEmptyMessage(0);
                }
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:

                    courseLogo.setImageResource(courseStateModel.isTransfer_course() ? R.mipmap
                            .convert_course_yellow : R.mipmap
                            .convert_course_gray);
                    classLogo.setImageResource(courseStateModel.isTransfer_class() ? R.mipmap
                            .convert_class_yellow : R.mipmap
                            .convert_class_gray);
                    courseTitle.setTextColor(courseStateModel.isTransfer_course() ? ContextCompat
                            .getColor(mActivity, R.color.text_color_default) : Color.parseColor
                            ("#BABDC2"));
                    classTitle.setTextColor(courseStateModel.isTransfer_class() ? ContextCompat
                            .getColor(mActivity, R.color.text_color_default) : Color.parseColor
                            ("#BABDC2"));
                    break;
            }
        }
    };

    protected void initView() {
        kid = getIntent().getStringExtra("kid");
        titleName.setText(getIntent().getStringExtra("title"));
//        setCustomTitle(getIntent().getStringExtra("title"));
        online = getIntent().getIntExtra("online", -1);
        signLogo.setImageResource(online == 1 ? R.mipmap.sign_gray : R.mipmap.sign_yellow);
        signTitle.setTextColor(online == 1 ? Color.parseColor("#BABDC2") : ContextCompat.getColor
                (this, R.color.text_color_default));
        courseTitle.setTextColor(online == 1 ? Color.parseColor("#BABDC2") : ContextCompat
                .getColor(this, R.color.text_color_default));
        classTitle.setTextColor(online == 1 ? Color.parseColor("#BABDC2") : ContextCompat
                .getColor(this, R.color.text_color_default));
        seatTitle.setTextColor(online == 1 ? Color.parseColor("#BABDC2") : ContextCompat.getColor
                (this, R.color.text_color_default));
        courseLogo.setImageResource(online == 1 ? R.mipmap.convert_course_gray : R.mipmap
                .convert_course_yellow);
        classLogo.setImageResource(online == 1 ? R.mipmap.convert_class_gray : R.mipmap
                .convert_class_yellow);
        seatLogo.setImageResource(online == 1 ? R.mipmap.class_seat_gray : R.mipmap
                .class_seat_yellow);

    }

    private void bindData() {
        if (classModel.getCourse() != null) {
            KeModel keModel = classModel.getCourse();
            courseName.setText(keModel.getTitle());
            courseCode.setText(String.format("班级编码: %s", keModel.getModel()));
            teacherName.setText(String.format("%s", keModel.getTname()));
            schoolName.setText(String.format("%s", keModel.getSchool()));
            date.setText(String.format("%s-%s", TimeUtil.parseTime(keModel.getStartPtime() *
                            1000, "yyyy年MM月dd日"),
                    TimeUtil.parseTime(keModel.getEndPtime() * 1000, "yyyy年MM月dd日")) + " " +
                    String.format("%s%s-%s", keModel.getWeekday(), TimeUtil.parseToHm(keModel
                            .getClass_start_at()), TimeUtil.parseToHm(keModel.getClass_end_at())));
            ImageLoader.display(this, logo, keModel.getPic());
        }
        signPer.setText(String.format("签到率: %s%s", classModel.getSignInRate(), "%"));
        noSign.setText(String.format("缺勤次数: %s次", classModel.getAbsenteeism()));
    }


    @OnClick({R.id.id_back, R.id.course_change, R.id.class_covert, R.id.class_seat, R.id
            .middle_report, R.id.final_report, R.id.welcome})
    @Override
    public void onClick(View view) {
        if (classModel == null) return;
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.id_back:
                finish();
                break;
            case R.id.course_change:
                UmengLogUtil.ChangeCourceClick(mActivity);
                if (online == 1) {
                    ToastUtil.showShortToast("线上课不支持该功能");
                    return;
                }
                if (courseStateModel != null && !courseStateModel.isTransfer_course()) return;
                mApplication.operate_type = 0;
                intent.putExtra("project_type", classModel.getCourse().getType());
                intent.putExtra("kid", kid);
                intent.putExtra("course_type", 1);
                intent.setClass(this, UnitTableActivity.class);
                startActivity(intent);
                break;
            case R.id.class_covert:
                UmengLogUtil.ConverClassClick(mActivity);
                if (online == 1) {
                    ToastUtil.showShortToast("线上课不支持该功能");
                    return;
                }
                if (courseStateModel != null && !courseStateModel.isTransfer_class()) return;
                mApplication.operate_type = 1;
                intent.putExtra("project_type", classModel.getCourse().getType());
                intent.setClass(this, ConvertClassActivity.class);
                startActivity(intent);
                break;
            case R.id.class_seat:
                if (online == 1) {
                    ToastUtil.showShortToast("线上课不支持该功能");
                    return;
                }
                ToastUtil.showShortToast("敬请期待");
                break;
            case R.id.middle_report:
                UmengLogUtil.QiZhongReportClick(mActivity);
                if (TextUtils.isEmpty(classModel.getMidterm_report())) {
                    ToastUtil.showShortToast(R.string.warning_no_mid_report);
                } else {
                    if (courseStateModel != null && !courseStateModel.isMidterm_report()) {
                        showEvalDialog(1, null);
                    } else
                        goWeb(1, classModel.getMidterm_report());
                }
                break;
            case R.id.final_report:
                UmengLogUtil.QiMoReportClick(mActivity);
                if (TextUtils.isEmpty(classModel.getMidterm_report())) {
                    ToastUtil.showShortToast(R.string.warning_no_mid_report);
                } else {
                    if (courseStateModel != null && !courseStateModel.isEnd_term_report()) {
                        showEvalDialog(2, null);
                    } else
                        goWeb(2, classModel.getEnd_term_report());
                }
                break;
            case R.id.welcome:
                UmengLogUtil.WelReportClick(mActivity);
                if (TextUtils.isEmpty(classModel.getWelcome_speech())) {
                    ToastUtil.showShortToast(R.string.warning_no_mid_report);
                } else {
                    goWeb(3, classModel.getWelcome_speech());
                }
                break;
        }
    }

    private void showEvalDialog(int type, String lessonid) {
        evalDialog = new FinalEvalDialog(this);
        if (type == 0) {
            evalDialog.setData(1, kid, lessonid);
        } else if (type == 1) {
            evalDialog.setData(2, kid, null);
        } else if (type == 2) {
            evalDialog.setData(3, kid, null);
        }

        evalDialog.setSubmitListener(new FinalEvalDialog.SubmitListener() {
            @Override
            public void show() {
                evalDialog.showAtLocation(main_layout, Gravity.BOTTOM, 0, 0);
            }

            @Override
            public void ok() {
                showToast("提交成功，感谢您的评价！");
            }

            @Override
            public void error(int code) {
                evalDialog.dismiss();
            }
        });
    }

    /**
     * @param type 1期中2期末3欢迎
     */
    private void goWeb(int type, String url) {
        Intent intent = new Intent(mContext, WebReportActivity.class);
        intent.putExtra("WebActivity_url", url);
        intent.putExtra("WebActivity_kid", kid);
        intent.putExtra("WebActivity_type", type);
        startActivity(intent);
    }

}
