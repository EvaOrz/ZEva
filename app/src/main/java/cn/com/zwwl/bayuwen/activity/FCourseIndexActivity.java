package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.TestListAdapter;
import cn.com.zwwl.bayuwen.adapter.UnitTableAdapter;
import cn.com.zwwl.bayuwen.api.StudyingCourseApi;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.LessonModel;
import cn.com.zwwl.bayuwen.model.StudyingModel;

/**
 * 课程跟踪列表点击后进入该处
 * Created by zhumangmang at 2018/5/29 15:55
 */
public class FCourseIndexActivity extends BasicActivityWithTitle {
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.percent)
    AppCompatTextView percent;
    @BindView(R.id.test)
    RecyclerView test;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    UnitTableAdapter unitTableAdapter;
    TestListAdapter testListAdapter;
    StudyingModel model;
    private String kid;

    @Override
    protected int setContentView() {
        return R.layout.activity_finish_course_index;
    }

    @Override
    protected void initView() {
        setCustomTitle("大语文");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        test.setLayoutManager(new LinearLayoutManager(this));
        test.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void initData() {
        kid = getIntent().getStringExtra("kid");
        setCustomTitle(getIntent().getStringExtra("title"));
        getData();
        List<LessonModel> courseModels = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            LessonModel model = new LessonModel();
            model.setTitle("XXX");
            courseModels.add(model);
        }
        unitTableAdapter = new UnitTableAdapter(null);
        testListAdapter = new TestListAdapter(courseModels);
        test.setNestedScrollingEnabled(false);
        recyclerView.setNestedScrollingEnabled(false);
        test.setAdapter(testListAdapter);
        recyclerView.setAdapter(unitTableAdapter);
    }

    private void getData() {
        new StudyingCourseApi(this, kid, new ResponseCallBack<StudyingModel>() {
            @Override
            public void result(StudyingModel studyingModel, ErrorMsg errorMsg) {
                if (studyingModel != null) {
                    model = studyingModel;
                    unitTableAdapter.setNewData(model.getCompleteClass());
                }
            }
        }
        );
    }

    @Override
    protected void setListener() {
        testListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent=new Intent(mActivity,ExamDetailsActivity.class);
                intent.putExtra("kid","77");
                intent.putExtra("title","XXX");
                startActivity(intent);
            }
        });
        unitTableAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mActivity, UnitIndexActivity.class);
                intent.putExtra("kId", model.getCompleteClass().get(position).getKid());
                intent.putExtra("cId", model.getCompleteClass().get(position).getId());
                intent.putExtra("title", model.getCompleteClass().get(position).getTitle());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public boolean setParentScrollable() {
        return true;
    }
}
