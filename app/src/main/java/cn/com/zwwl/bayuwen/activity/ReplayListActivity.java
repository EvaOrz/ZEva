package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
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
import cn.com.zwwl.bayuwen.widget.CircleImageView;
import cn.com.zwwl.bayuwen.widget.roundview.RoundLinearLayout;

/**
 * 课程跟踪视频回访列表
 * Created by zhumangmang at 2018/7/4 15:26
 */
public class ReplayListActivity extends BasicActivityWithTitle {
    @BindView(R.id.logo)
    CircleImageView logo;
    @BindView(R.id.course_name)
    AppCompatTextView courseName;
    @BindView(R.id.course_code)
    AppCompatTextView courseCode;
    @BindView(R.id.top_layout)
    ConstraintLayout topLayout;
    @BindView(R.id.teacher_name)
    AppCompatTextView teacherName;
    @BindView(R.id.school_name)
    AppCompatTextView schoolName;
    @BindView(R.id.date)
    AppCompatTextView date;
    @BindView(R.id.time)
    AppCompatTextView time;
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

    @Override
    protected int setContentView() {
        return R.layout.activity_replay_list;
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        reports = new ArrayList<>();
        adapter = new LessonReplayAdapter(reports);
        View view = getLayoutInflater().inflate(R.layout.empty_view, null);
        emptyTv = view.findViewById(R.id.empty_content);
        emptyTv.setText("正在加载视频列表");
        adapter.setEmptyView(view);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        if (getIntent().getIntExtra("type", 0) == 1) {
            setCustomTitle(R.string.look_replay);
            classInfo.setVisibility(View.GONE);
            lookReplay.setText(null);
            lookReplay.setVisibility(View.GONE);
        } else
            setCustomTitle(getIntent().getStringExtra("title"));
        new StudyingCourseApi(this, "8391", new ResponseCallBack<StudyingModel>() {
            @Override
            public void result(StudyingModel studyingModel, ErrorMsg errorMsg) {
                if (studyingModel != null) {
                    model = studyingModel;
                    bindData();
                }
            }
        });
    }

    @Override
    protected void setListener() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mActivity, VideoPlayActivity.class);
                intent.putExtra("VideoPlayActivity_url", reports.get(position).getPlay_url());
                startActivity(intent);
                startActivity(intent);
            }
        });
    }

    private void bindData() {
        if (model.getCourse() != null) {
            KeModel keModel = model.getCourse();
            courseName.setText(keModel.getTitle());
            courseCode.setText(String.format("班级编码: %s", keModel.getModel()));
            teacherName.setText(String.format("授课老师: %s", keModel.getTname()));
            schoolName.setText(String.format("上课地点: %s", keModel.getSchool()));
            date.setText(String.format("上课日期: %s-%s", TimeUtil.parseTime(keModel.getStartPtime() * 1000, "yyyy年MM月dd日"), TimeUtil.parseTime(keModel.getEndPtime() * 1000, "yyyy年MM月dd日")));
            time.setText(String.format("上课时间: %s%s-%s", keModel.getWeekday(),
                    TimeUtil.parseToHm(keModel.getClass_start_at()), TimeUtil.parseToHm(keModel.getClass_end_at())));
            ImageLoader.display(this, logo, keModel.getPic());
        }
        if (model.getCompleteClass() != null) {
            reports = model.getCompleteClass();
        } else {
            emptyTv.setText("暂无视频~");
        }
        adapter.setNewData(reports);
    }

    @Override
    public boolean setParentScrollable() {
        return true;
    }


    @Override
    public void onClick(View view) {
    }

    @Override
    public void close() {
        finish();
    }

}
