package cn.com.zwwl.bayuwen.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.zwwl.bayuwen.MyApplication;
import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.UnitTableAdapter;
import cn.com.zwwl.bayuwen.api.TransferLectureApi;
import cn.com.zwwl.bayuwen.base.BasicActivityWithTitle;
import cn.com.zwwl.bayuwen.listener.ResponseCallBack;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.LessonModel;
import cn.com.zwwl.bayuwen.widget.decoration.HSpacesItemDecoration;

/**
 * 课程单元列表
 * Create by zhumangmang at 2018/5/28 10:50
 */
public class UnitTableActivity extends BaseActivity {
    @BindView(R.id.type)
    AppCompatTextView type;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    UnitTableAdapter adapter;
    List<LessonModel> lessonModelList;
    @BindView(R.id.id_back)
    ImageView idBack;
    @BindView(R.id.title_name)
    TextView titleName;
    private int operate_type;
    private String projectType;
    private MyApplication mApplication;
    protected Resources res;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_table);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        mApplication = (MyApplication) getApplication();
        res = getResources();
        mActivity = this;
        initView1();
        initData1();
        setListener1();
    }


    protected void initView1() {
        titleName.setText(res.getString(R.string.no_study_unit));
        type.setText(res.getString(R.string.chose_change_course_unit_by_need));
        idBack.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new HSpacesItemDecoration(res, R.dimen.dp_5));
        adapter = new UnitTableAdapter(null);
        recyclerView.setAdapter(adapter);
    }


    protected void initData1() {
        int course_type = getIntent().getIntExtra("course_type", -1);
        projectType = getIntent().getStringExtra("project_type");
        adapter.setType(course_type);
        operate_type = getIntent().getIntExtra("type", 0);
        String kid = getIntent().getStringExtra("kid");
        new TransferLectureApi(this, kid, operate_type, new ResponseCallBack<List<LessonModel>>() {
            @Override
            public void result(List<LessonModel> lessonModels, ErrorMsg errorMsg) {
                if (lessonModels != null) {
                    lessonModelList = lessonModels;
                    adapter.setNewData(lessonModelList);
                }
            }
        });

    }

    protected void setListener1() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (operate_type == 0) {
                    mApplication.oldLesson = lessonModelList.get(position);
                    Intent intent = new Intent(mActivity, ConvertClassActivity.class);
                    intent.putExtra("project_type", projectType);
                    startActivity(intent);
                } else {
                    mApplication.newLesson = lessonModelList.get(position);
                    startActivity(new Intent(mActivity, CourseApplyActivity.class));
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_back:
                finish();
                break;
        }
    }

    @Override
    protected void initData() {

    }
}
