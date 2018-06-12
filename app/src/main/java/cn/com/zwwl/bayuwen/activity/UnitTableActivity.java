package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;
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
public class UnitTableActivity extends BasicActivityWithTitle {
    @BindView(R.id.type)
    AppCompatTextView type;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    UnitTableAdapter adapter;
    List<LessonModel> lessonModelList;
    private int operate_type;
    private String kid = "10644";

    @Override
    protected int setContentView() {
        return R.layout.activity_course_table;
    }

    @Override
    protected void initView() {
        setCustomTitle(res.getString(R.string.no_study_unit));
        type.setText(res.getString(R.string.chose_change_course_unit_by_need));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new HSpacesItemDecoration(res,R.dimen.dp_5));
        adapter = new UnitTableAdapter(null);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        int course_type = getIntent().getIntExtra("course_type", -1);
        adapter.setType(course_type);
        operate_type = getIntent().getIntExtra("type", 0);
            kid = getIntent().getStringExtra("kid");
        new TransferLectureApi(this, kid,operate_type, new ResponseCallBack<List<LessonModel>>() {
            @Override
            public void result(List<LessonModel> lessonModels, ErrorMsg errorMsg) {
                if (lessonModels != null) {
                    lessonModelList = lessonModels;
                    adapter.setNewData(lessonModelList);
                }
            }
        });

    }

    @Override
    protected void setListener() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (operate_type == 0) {
                    mApplication.oldLesson = lessonModelList.get(position);
                    startActivity(new Intent(mActivity, ConvertClassActivity.class));
                } else {
                    mApplication.newLesson = lessonModelList.get(position);
                    startActivity(new Intent(mActivity, CourseApplyActivity.class));
                }
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
}
