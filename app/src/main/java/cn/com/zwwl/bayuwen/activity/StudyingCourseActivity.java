package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.logging.Handler;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.StudyingCourseAdapter;
import cn.com.zwwl.bayuwen.api.CourseStateApi;
import cn.com.zwwl.bayuwen.api.StudyingCourseApi;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.StudyingModel;
import cn.com.zwwl.bayuwen.util.ToastUtil;
import cn.com.zwwl.bayuwen.widget.decoration.HSpacesItemDecoration;

/**
 * 在学课程
 * Create by zhumangmang at 2018/5/26 13:22
 */
public class StudyingCourseActivity extends BasicActivityWithTitle {
    @BindView(R.id.course_progress)
    ProgressBar courseProgress;
    @BindView(R.id.total_course)
    AppCompatTextView totalCourse;
    @BindView(R.id.current)
    AppCompatTextView current;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    StudyingCourseAdapter adapter;
    String kid;
    StudyingModel model;
    @BindView(R.id.progress_layout)
    LinearLayout progressLayout;
    @BindView(R.id.course_eval)
    AppCompatTextView courseEval;
    @BindView(R.id.course_change)
    AppCompatTextView courseChange;
    @BindView(R.id.class_covert)
    AppCompatTextView classCovert;
    @BindView(R.id.course_title)
    AppCompatTextView courseTitle;
    private int type;

    @Override
    protected int setContentView() {
        return R.layout.activity_studying_course;
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new HSpacesItemDecoration(res, R.dimen.dp_5));
        adapter = new StudyingCourseAdapter(null);
        recyclerView.setAdapter(adapter);
    }

    private void getData() {
        new StudyingCourseApi(this, kid, new ResponseCallBack<StudyingModel>() {
            @Override
            public void result(StudyingModel studyingModel, ErrorMsg errorMsg) {
                if (studyingModel != null) {
                    model = studyingModel;
                    current.setText(String.valueOf(model.getCurrent()));
                    totalCourse.setText(String.valueOf(model.getCount()));
                    courseProgress.setMax(model.getCount());
                    courseProgress.setProgress(model.getCurrent());
                    adapter.setNewData(model.getCompleteClass());
                }
            }
        });
    }


    @Override
    protected void initData() {
        type = getIntent().getIntExtra("course_type", -1);
        adapter.setType(type);
        kid = getIntent().getStringExtra("kid");
        setCustomTitle(getIntent().getStringExtra("title"));
        if (getIntent().getIntExtra("online", -1) == 1) {
            classCovert.setVisibility(View.GONE);
            courseChange.setVisibility(View.GONE);
        }
        getData();
    }

    @Override
    protected void setListener() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mActivity, UnitIndexActivity.class);
                intent.putExtra("online", model.getOnline());
                intent.putExtra("kId", model.getCompleteClass().get(position).getKid());
                intent.putExtra("cId", model.getCompleteClass().get(position).getId());
                intent.putExtra("title", model.getCompleteClass().get(position).getTitle());
                intent.putExtra("video", 1);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean setParentScrollable() {
        return false;
    }

    @OnClick({R.id.course_eval, R.id.course_change, R.id.class_covert})
    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.course_eval:
                ToastUtil.showShortToast("学完课程才能评价哦！");
                break;
            case R.id.course_change:
                mApplication.operate_type = 0;
                intent.putExtra("kid", kid);
                intent.putExtra("course_type", type);
                intent.setClass(this, UnitTableActivity.class);
                startActivity(intent);
                break;
            case R.id.class_covert:
                mApplication.operate_type = 1;
                intent.setClass(this, ConvertClassActivity.class);
                startActivity(intent);
                break;
        }

    }

    @Override
    public void close() {
        finish();
    }

}
