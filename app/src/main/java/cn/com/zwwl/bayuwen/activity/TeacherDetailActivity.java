package cn.com.zwwl.bayuwen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ksoichiro.android.observablescrollview.ScrollUtils;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;
import cn.com.zwwl.bayuwen.adapter.EleCourseGridAdapter;
import cn.com.zwwl.bayuwen.adapter.TCourseListAdapter;
import cn.com.zwwl.bayuwen.api.PraiseListApi;
import cn.com.zwwl.bayuwen.api.TDeatailApi;
import cn.com.zwwl.bayuwen.glide.BorderCircleTransform;
import cn.com.zwwl.bayuwen.glide.CircleTransform;
import cn.com.zwwl.bayuwen.glide.GlideApp;
import cn.com.zwwl.bayuwen.listener.FetchEntryListener;
import cn.com.zwwl.bayuwen.listener.OnItemClickListener;
import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;
import cn.com.zwwl.bayuwen.model.PraiseModel;
import cn.com.zwwl.bayuwen.model.TeacherDetailModel;
import cn.com.zwwl.bayuwen.model.TeacherModel;
import cn.com.zwwl.bayuwen.widget.StopLinearLayoutManager;
import cn.com.zwwl.bayuwen.widget.decoration.DividerItemDecoration;

/**
 * Created by lousx on 2018/5/15.
 */

public class TeacherDetailActivity extends BaseActivity implements OnItemClickListener {
    private TextView fcNameTv;
    private TextView tv_name;
    private TextView lecture_tv;
    private TextView label_tv;
    private TextView educal_background_tv;
    private TextView teaching_idea_tv;
    private TextView titleTv;
    private ImageView iv_avatar;
    private List<TeacherDetailModel.CoursesEntity> list = new ArrayList<>();

    private RecyclerView recyclerView;
    private TCourseListAdapter tCourseListAdapter;
    private TeacherDetailModel teacherDetailModel;

    private String tid;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    setData();
                    break;
            }
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_detail);
        tid = getIntent().getStringExtra("tid");
        initView();
        getTeacherDetailList();
    }

    @Override
    protected void initData() {
    }

    private void initView() {
        tv_name = findViewById(R.id.tv_name);
        lecture_tv = findViewById(R.id.lecture_tv);
        label_tv = findViewById(R.id.label_tv);
        educal_background_tv = findViewById(R.id.educal_background_tv);
        teaching_idea_tv = findViewById(R.id.teaching_idea_tv);
        titleTv = findViewById(R.id.title_tv);
        iv_avatar = findViewById(R.id.iv_avatar);

        recyclerView = findViewById(R.id.td_reView);
        recyclerView.setHasFixedSize(true);
        StopLinearLayoutManager linearLayoutManager = new StopLinearLayoutManager(mContext);
        linearLayoutManager.setScrollEnabled(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getResources(), R.color.line, R.dimen.dp_1, OrientationHelper.VERTICAL));

        tCourseListAdapter = new TCourseListAdapter(mContext, list);
        recyclerView.setAdapter(tCourseListAdapter);
        tCourseListAdapter.setOnItemClickListener(this);

        titleTv.setText("教师详情");
        findViewById(R.id.back_btn).setOnClickListener(this);
    }

    private void setData() {
        tv_name.setText(teacherDetailModel.getTeacher().getName());
        lecture_tv.setText(teacherDetailModel.getTeacher().getKe_main());
        label_tv.setText(teacherDetailModel.getTeacher().getT_style());
        educal_background_tv.setText(teacherDetailModel.getTeacher().getT_desc());
        teaching_idea_tv.setText(teacherDetailModel.getTeacher().getT_idea());
        GlideApp.with(mContext).load(teacherDetailModel.getTeacher().getPic())
                .placeholder(R.drawable.avatar_placeholder)
                .error(R.drawable.avatar_placeholder)
                .transform(new BorderCircleTransform(mContext, 3))
                .into(iv_avatar);
        tCourseListAdapter.appendData(teacherDetailModel.getCourses());
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.back_btn:
                finish();
                break;
        }
    }

    private void getTeacherDetailList() {
        new TDeatailApi(mContext, tid, new FetchEntryListener() {

            @Override
            public void setData(Entry entry) {
                if (entry != null && entry instanceof TeacherDetailModel) {
                    teacherDetailModel = (TeacherDetailModel) entry;
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void setError(ErrorMsg error) {
                Toast.makeText(mContext, error.getDesc(), Toast.LENGTH_LONG);
            }
        });
    }

    @Override
    public void setOnItemClickListener(View view, int position) {
        Intent intent = new Intent();
        intent.putExtra("cid", teacherDetailModel.getCourses().get(position).getKid());
        intent.setClass(mContext, CourseDetailActivity.class);
        startActivity(intent);
    }

    @Override
    public void setOnLongItemClickListener(View view, int position) {

    }
}
