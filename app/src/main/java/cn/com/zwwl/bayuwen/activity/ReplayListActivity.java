package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.LessonReplayAdapter;
import cn.com.zwwl.bayuwen.api.StudyingCourseApi;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;
import cn.com.zwwl.bayuwen.glide.ImageLoader;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.KeModel;
import cn.com.zwwl.bayuwen.model.LessonModel;
import cn.com.zwwl.bayuwen.model.StudyingModel;
import cn.com.zwwl.bayuwen.util.TimeUtil;
import cn.com.zwwl.bayuwen.util.UmengLogUtil;
import cn.com.zwwl.bayuwen.view.OvalImageview;
import cn.com.zwwl.bayuwen.widget.CircleImageView;
import cn.com.zwwl.bayuwen.widget.roundview.RoundLinearLayout;

/**
 * 课程跟踪视频回访列表
 * Created by zhumangmang at 2018/7/4 15:26
 */
public class ReplayListActivity extends BaseActivity {
    @BindView(R.id.logo)
    CircleImageView logo;
    @BindView(R.id.course_name)
    AppCompatTextView courseName;
    @BindView(R.id.course_code)
    AppCompatTextView courseCode;
    @BindView(R.id.top_layout)
    LinearLayout topLayout;
    @BindView(R.id.teacher_name)
    AppCompatTextView teacherName;
    @BindView(R.id.school_name)
    AppCompatTextView schoolName;
    @BindView(R.id.date)
    AppCompatTextView date;
    //    @BindView(R.id.time)
//    AppCompatTextView time;
    @BindView(R.id.class_info)
    RoundLinearLayout classInfo;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.look_replay)
    AppCompatTextView lookReplay;
    LessonReplayAdapter adapter;
    List<LessonModel> reports;
    StudyingModel model;
    AppCompatTextView emptyTv;
    @BindView(R.id.id_back)
    ImageView idBack;
    @BindView(R.id.title_name)
    TextView titleName;
    private ReplayListActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replay_list);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        mActivity = this;
        initView1();
        SetData();
        setListener1();
    }

    @Override
    protected void initData() {

    }


    protected void initView1() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        reports = new ArrayList<>();
        adapter = new LessonReplayAdapter(reports);
        View view = getLayoutInflater().inflate(R.layout.empty_view, null);
        emptyTv = view.findViewById(R.id.empty_content);
        emptyTv.setText("正在加载视频列表");
        adapter.setEmptyView(view);
        recyclerView.setAdapter(adapter);
    }


    protected void SetData() {
        if (getIntent().getIntExtra("type", 0) == 1) {
            titleName.setText(R.string.look_replay);
//            classInfo.setVisibility(View.GONE);
//            lookReplay.setText(null);
//            lookReplay.setVisibility(View.GONE);
        } else
            titleName.setText(getIntent().getStringExtra("title"));
        String kid = getIntent().getStringExtra("kid");
        new StudyingCourseApi(this, kid, new ResponseCallBack<StudyingModel>() {
            @Override
            public void result(StudyingModel studyingModel, ErrorMsg errorMsg) {
                if (studyingModel != null) {
                    model = studyingModel;
                    bindData();
                }
            }
        });
    }


    protected void setListener1() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                UmengLogUtil.LivePlayBackClick(mActivity);
                Intent intent = new Intent(mActivity, WebActivity.class);
                intent.putExtra("WebActivity_data", reports.get(position).getPlay_url());
                startActivity(intent);
                startActivity(intent);
            }
        });
        idBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void bindData() {
        if (model.getCourse() != null) {
            KeModel keModel = model.getCourse();
            courseName.setText(keModel.getTitle());
            courseCode.setText(String.format("班级编码: %s", keModel.getModel()));
            teacherName.setText(String.format("%s", keModel.getTname()));
            schoolName.setText(String.format("%s", keModel.getSchool()));
            date.setText(String.format("%s-%s", TimeUtil.parseTime(keModel.getStartPtime() * 1000,
                    "yyyy年MM月dd日"), TimeUtil.parseTime(keModel.getEndPtime() * 1000,
                    "yyyy年MM月dd日")) +
                    String.format("%s%s-%s", keModel.getWeekday(),
                            TimeUtil.parseToHm(keModel.getClass_start_at()), TimeUtil.parseToHm
                                    (keModel.getClass_end_at())));
            ImageLoader.display(this, logo, keModel.getPic());
        }
        if (model.getCompleteClass() != null) {
            reports = model.getCompleteClass();
        } else {
            emptyTv.setText("暂无视频~");
        }
        adapter.setNewData(reports);
    }


}
